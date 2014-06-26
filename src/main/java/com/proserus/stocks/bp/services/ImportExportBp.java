package com.proserus.stocks.bp.services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import com.google.inject.Inject;
import com.proserus.stocks.bo.common.BoBuilder;
import com.proserus.stocks.bo.symbols.CurrencyEnum;
import com.proserus.stocks.bo.symbols.SectorEnum;
import com.proserus.stocks.bo.symbols.Symbol;
import com.proserus.stocks.bo.transactions.Label;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bo.transactions.TransactionType;
import com.proserus.stocks.bo.utils.BigDecimalUtils;
import com.proserus.stocks.bp.dao.PersistenceManager;
import com.proserus.stocks.bp.model.CsvModel;
import com.proserus.stocks.bp.utils.DateUtils;

public class ImportExportBp {
	private final static Logger LOGGER = LoggerFactory.getLogger(ImportExportBp.class.getName());
	@Inject
	private SymbolsBp symbolsBp;

	@Inject
	private TransactionsBp transactionsBp;

	@Inject
	private BoBuilder boBuilder;

	@Inject
	private LabelsBp labelsBp;

	@Inject
	private PersistenceManager persistenceManager;

	public String exportTransactions(Collection<Transaction> transactions) throws IOException {
		Validate.notNull(transactions);
		Validate.notEmpty(transactions);

		ByteArrayOutputStream b = new ByteArrayOutputStream();
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(b), ',');
		writer.writeNext(CsvModel.getHeaders());
		for (Transaction t : transactions) {
			writer.writeNext(new CsvModel(t).getFields());
		}
		writer.close();
		b.close();
		return b.toString("UTF-8");
	}

	public void importTransactions(File file, CurrencyEnum defaultCurrency) throws FileNotFoundException {
		Validate.notNull(file);

		// HeaderColumnNameMappingStrategy<CsvModel> strat = new
		// HeaderColumnNameMappingStrategy<CsvModel>();
		HeaderColumnNameTranslateMappingStrategy<CsvModel> strat2 = new HeaderColumnNameTranslateMappingStrategy<CsvModel>();
		strat2.setColumnMapping(CsvModel.getColumnMapping());
		strat2.setType(CsvModel.class);

		CsvToBean<CsvModel> csv = new CsvToBean<CsvModel>();
		List<CsvModel> list = csv.parse(strat2, new CSVReader(new FileReader(file)));

		persistenceManager.getTransaction().begin();
		for (CsvModel model : list) {
			Symbol s = boBuilder.getSymbol();
			Transaction transaction = boBuilder.getTransaction();

			if (setSymbol(model, s) && setDate(model, transaction) && setQuantity(model, transaction) && setType(model, transaction)
					&& setPrice(model, transaction)) {

				setName(model, s);
				setCurrency(model, s, defaultCurrency);
				setSector(model, s);
				setCustomPrice(model, s);
				s = symbolsBp.add(s);
				transaction.setSymbol(s);
				setCommission(model, transaction);
				setLabels(model, transaction);
				transactionsBp.add(transaction);
			}
		}
		persistenceManager.getTransaction().commit();
	}

	private boolean setLabels(CsvModel model, Transaction transaction) {
		Validate.notNull(model);
		Validate.notNull(transaction);

		if (model.getLabels() != null && !model.getLabels().isEmpty()) {
			for (String str : model.getLabels().replaceFirst("\\[", "").replaceAll("\\]", "").split(",")) {
				if (!str.isEmpty()) {
					Label label = boBuilder.getLabel();
					label.setName(str);
					label = labelsBp.add(label);
					transaction.addLabel(label);
				}
			}
		}
		return true;
	}

	private boolean setCommission(CsvModel model, Transaction transaction) {
		Validate.notNull(model);
		Validate.notNull(transaction);

		if (model.getCommission() != null && !model.getCommission().isEmpty()) {
			transaction.setCommission(BigDecimalUtils.stringToBigDecimal(model.getCommission()));
		} else {
			transaction.setCommission(BigDecimal.ZERO);
			LOGGER.debug("No commission found during import: Continuing");
		}
		return true;
	}

	private boolean setPrice(CsvModel model, Transaction transaction) {
		Validate.notNull(model);
		Validate.notNull(transaction);

		if (model.getPrice() != null && !model.getPrice().isEmpty()) {
			transaction.setPrice(BigDecimalUtils.stringToBigDecimal(model.getPrice()));
			return true;
		} else {
			LOGGER.debug("Unable to import transaction: No price found");
		}
		return false;
	}

	private boolean setType(CsvModel model, Transaction transaction) {
		Validate.notNull(model);
		Validate.notNull(transaction);

		if (model.getType() != null && !model.getType().isEmpty()) {
			transaction.setQuantity(BigDecimalUtils.stringToBigDecimal(model.getQuantity()));
			try {
				TransactionType type = TransactionType.valueOfById(model.getType().toUpperCase());
				transaction.setType(type);
				return true;
			} catch (IllegalArgumentException e) {
				LOGGER.debug("Unable to import transaction: No valid type found");

			}
		} else {
			LOGGER.debug("Unable to import transaction: No type found");
		}

		return false;
	}

	private boolean setQuantity(CsvModel model, Transaction transaction) {
		Validate.notNull(model);
		Validate.notNull(transaction);

		if (model.getQuantity() != null && !model.getQuantity().isEmpty()) {
			transaction.setQuantity(BigDecimalUtils.stringToBigDecimal(model.getQuantity()));
			return true;
		} else {
			LOGGER.debug("Unable to import transaction: No quantity found");
		}
		return false;
	}

	private boolean setDate(CsvModel model, Transaction transaction) {
		Validate.notNull(model);
		Validate.notNull(transaction);

		if (model.getDate() != null && !model.getDate().isEmpty()) {
			int i = 0;
			Calendar calendar = null;
			while (calendar == null && i < CsvModel.DATE_FORMATS.length) {
				calendar = DateUtils.stringToToCalendar(CsvModel.DATE_FORMATS[i], model.getDate());
				i++;
			}

			if (calendar != null) {
				transaction.setCalendar(calendar);
				return true;
			} else {
				transaction.setCalendar(Calendar.getInstance());
				LOGGER.debug("No valid date found during import: continuing with today's date");
			}
		}
		return false;
	}

	private boolean setName(CsvModel model, Symbol s) {
		Validate.notNull(model);
		Validate.notNull(s);

		if (model.getName() != null && !model.getName().isEmpty()) {
			s.setName(model.getName());
		} else {
			LOGGER.debug("No name found during import: continuing");
		}
		return true;
	}

	private boolean setCurrency(CsvModel model, Symbol s, CurrencyEnum defaultCurrency) {
		Validate.notNull(model);
		Validate.notNull(s);

		if (model.getCurrency() != null && !model.getCurrency().isEmpty()) {
			try {
				s.setCurrency(CurrencyEnum.valueOfById(model.getCurrency()));
			} catch (IllegalArgumentException e) {

			}
		}
		if (s.getCurrency() == null) {
			s.setCurrency(defaultCurrency);
			LOGGER.debug("Using default currency: continuing");
		}
		return true;
	}

	private boolean setSector(CsvModel model, Symbol s) {
		Validate.notNull(model);
		Validate.notNull(s);

		if (model.getSector() != null && !model.getSector().isEmpty()) {
			try {
				s.setSector(SectorEnum.valueOfById(model.getSector()));
				return true;
			} catch (IllegalArgumentException e) {

			}
		}
		s.setSector(SectorEnum.UNKNOWN);
		return true;
	}

	private boolean setCustomPrice(CsvModel model, Symbol s) {
		Validate.notNull(model);
		Validate.notNull(s);

		s.setCustomPriceFirst(Boolean.parseBoolean(model.getCustomPrices()));

		return true;
	}

	private boolean setSymbol(CsvModel model, Symbol s) {
		Validate.notNull(model);
		Validate.notNull(s);

		if (model.getSymbol() != null && !model.getSymbol().isEmpty()) {
			s.setTicker(model.getSymbol());
			return true;
		} else {
			LOGGER.debug("Unable to import transaction: No symbol found");
		}
		return false;
	}
}
