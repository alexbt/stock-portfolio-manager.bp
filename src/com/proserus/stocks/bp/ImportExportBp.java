package com.proserus.stocks.bp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.List;

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
//		HeaderColumnNameMappingStrategy<CsvModel> strat = new HeaderColumnNameMappingStrategy<CsvModel>();
		HeaderColumnNameTranslateMappingStrategy<CsvModel> strat2 = new HeaderColumnNameTranslateMappingStrategy<CsvModel>();
		strat2.setColumnMapping(CsvModel.getColumnMapping());
		strat2.setType(CsvModel.class);

		CsvToBean<CsvModel> csv = new CsvToBean<CsvModel>();
		List<CsvModel> list = csv.parse(strat2, new CSVReader(new FileReader(file)));

		for (CsvModel model : list) {
			if(!model.getSymbol().isEmpty()){
				Symbol s = new Symbol();
				s.setTicker(model.getSymbol());
				s.setName(model.getName());
				controller.addSymbol(s);
				
				Transaction transaction = new Transaction();
				transaction.setSymbol(s);
				transaction.setType(TransactionType.valueOf(model.getType().toUpperCase()));
				transaction.setDate(DateUtil.stringToToDate(CsvModel.DATE_FORMAT, model.getDate()));
				transaction.setQuantity(BigDecimalUtils.stringToBigDecimal(model.getQuantity()));
				transaction.setPrice(BigDecimalUtils.stringToBigDecimal(model.getPrice()));
				transaction.setCommission(BigDecimalUtils.stringToBigDecimal(model.getCommission()));
	
				for (String str : model.getLabels().replaceFirst("\\[", "").replaceAll("\\]", "").split(",")) {
					if (!str.isEmpty()) {
						Label label = new Label();
						label.setName(str);
						label = controller.addLabel(label);
						transaction.addLabel(label);
					}
				}
				controller.addTransaction(transaction);
			}
		}
	}
}
