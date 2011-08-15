package com.proserus.stocks.bp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;

import com.google.inject.Inject;
import com.proserus.stocks.controllers.iface.PortfolioController;
import com.proserus.stocks.model.symbols.Symbol;
import com.proserus.stocks.model.transactions.Label;
import com.proserus.stocks.model.transactions.Transaction;
import com.proserus.stocks.model.transactions.TransactionType;
import com.proserus.stocks.utils.BigDecimalUtils;

public class ImportExportBp {
	private static Logger logger = Logger.getLogger(ImportExportBp.class.getName());

	private PortfolioController controller;

	@Inject
	public void setPortfolioController(PortfolioController controller) {
		this.controller = controller;
	}

	public ByteArrayOutputStream exportTransactions(Collection<Transaction> transactions) throws IOException {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		CSVWriter writer = new CSVWriter(new OutputStreamWriter(b), ',');
		writer.writeNext(CsvModel.getHeaders());
		for (Transaction t : transactions) {
			writer.writeNext(new CsvModel(t).getFields());
		}
		writer.close();
		b.close();
		return b;
	}

	public void importTransactions(File file) throws FileNotFoundException {
		// HeaderColumnNameMappingStrategy<CsvModel> strat = new HeaderColumnNameMappingStrategy<CsvModel>();
		HeaderColumnNameTranslateMappingStrategy<CsvModel> strat2 = new HeaderColumnNameTranslateMappingStrategy<CsvModel>();
		strat2.setColumnMapping(CsvModel.getColumnMapping());
		strat2.setType(CsvModel.class);

		CsvToBean<CsvModel> csv = new CsvToBean<CsvModel>();
		List<CsvModel> list = csv.parse(strat2, new CSVReader(new FileReader(file)));

		for (CsvModel model : list) {
			Symbol s = new Symbol();
			Transaction transaction = new Transaction();

			if (setSymbol(model, s) && setDate(model, transaction) && setQuantity(model, transaction) && setType(model, transaction)
			        && setPrice(model, transaction)) {
				controller.addSymbol(s);
				transaction.setSymbol(s);
				setName(model, s);
				setLabels(model, transaction);
				setCommission(model, transaction);
				controller.addTransaction(transaction);
			} 
		}
	}

	private boolean setLabels(CsvModel model, Transaction transaction) {
		if (model.getLabels() != null && !model.getLabels().isEmpty()) {
			for (String str : model.getLabels().replaceFirst("\\[", "").replaceAll("\\]", "").split(",")) {
				if (!str.isEmpty()) {
					Label label = new Label();
					label.setName(str);
					label = controller.addLabel(label);
					transaction.addLabel(label);
				}
			}
		}
		return true;
	}

	private boolean setCommission(CsvModel model, Transaction transaction) {
		if (model.getCommission() != null && !model.getCommission().isEmpty()) {
			transaction.setCommission(BigDecimalUtils.stringToBigDecimal(model.getCommission()));
		} else {
			transaction.setCommission(BigDecimal.ZERO);
			logger.debug("No commission found during import: Continuing");
		}
		return true;
	}

	private boolean setPrice(CsvModel model, Transaction transaction) {
		if (model.getPrice() != null && !model.getPrice().isEmpty()) {
			transaction.setPrice(BigDecimalUtils.stringToBigDecimal(model.getPrice()));
			return true;
		} else {
			logger.debug("Unable to import transaction: No price found");
		}
		return false;
	}

	private boolean setType(CsvModel model, Transaction transaction) {
		if (model.getType() != null && !model.getType().isEmpty()) {
			transaction.setQuantity(BigDecimalUtils.stringToBigDecimal(model.getQuantity()));
			try {
				TransactionType type = TransactionType.valueOf(model.getType().toUpperCase());
				transaction.setType(type);
				return true;
			} catch (IllegalArgumentException e) {
				logger.debug("Unable to import transaction: No valid type found");

			}
		} else {
			logger.debug("Unable to import transaction: No type found");
		}

		return false;
	}

	private boolean setQuantity(CsvModel model, Transaction transaction) {
		if (model.getQuantity() != null && !model.getQuantity().isEmpty()) {
			transaction.setQuantity(BigDecimalUtils.stringToBigDecimal(model.getQuantity()));
			return true;
		} else {
			logger.debug("Unable to import transaction: No quantity found");
		}
		return false;
	}

	private boolean setDate(CsvModel model, Transaction transaction) {
		if (model.getDate() != null && !model.getDate().isEmpty()) {
			int i = 0;
			Date date = null;
			while (date == null && i < CsvModel.DATE_FORMATS.length) {
				date = DateUtil.stringToToDate(CsvModel.DATE_FORMATS[i], model.getDate());
				i++;
			}

			if (date != null) {
				transaction.setDate(date);
				return true;
			} else {
				transaction.setDate(new Date());
				logger.debug("No valid date found during import: continuing with today's date");
			}
		}
		return false;
	}

	private boolean setName(CsvModel model, Symbol s) {
		if (model.getName() != null && !model.getName().isEmpty()) {
			s.setName(model.getName());
		} else {
			logger.debug("No name found during import: continuing");
		}
		return true;
	}

	private boolean setSymbol(CsvModel model, Symbol s) {
		if (model.getSymbol() != null && !model.getSymbol().isEmpty()) {
			s.setTicker(model.getSymbol());
			return true;
		} else {
			logger.debug("Unable to import transaction: No symbol found");
		}
		return false;
	}
}
