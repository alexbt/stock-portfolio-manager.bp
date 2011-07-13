package com.proserus.stocks.bp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

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
		// CSVWriter writer = new CSVWriter(new FileWriter(file), ',');
		writer.writeNext(transactionHeader());
		for (Transaction t : transactions) {
			writer.writeNext(transactionToCsv(t));
		}
		writer.close();
		b.close();
		return b;
	}

	public void importTransactions(File file) throws FileNotFoundException {
		CSVReader reader = new CSVReader(new FileReader(file));
		String[] nextLine;
		try {
			while ((nextLine = reader.readNext()) != null) {
				try {
					System.out.println(nextLine[0] + nextLine[1] + "etc...");
					Symbol s = new Symbol();
					s.setTicker(nextLine[0]);
					s.setName(nextLine[1]);

					Transaction t = new Transaction();
					t.setSymbol(s);

					t.setType(TransactionType.valueOf(nextLine[2].toUpperCase()));

					Format formatter = new SimpleDateFormat("MMM dd, yyyy");
					t.setDate((Date) formatter.parseObject(nextLine[3]));

					t.setQuantity(new BigDecimal(nextLine[4]));
					t.setPrice(new BigDecimal(nextLine[5]));
					t.setCommission(new BigDecimal(nextLine[6]));
					
					controller.addSymbol(s);
					for (String str : nextLine[7].replaceFirst("\\[", "").replaceAll("\\]", "").split(",")) {
						Label l = new Label();
						l.setName(str);
						l.addTransaction(t);
						controller.addLabel(l);
						controller.addTransaction(t);
					}
				} catch (IndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
				}catch (ParseException e) {
					// TODO Auto-generated catch block
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String[] transactionHeader() {
		return new String[] { "Symbol", "Name", "Type", "Date", "Shares", "Price", "Commission", "Notes" };
	}

	private String[] transactionToCsv(Transaction transaction) {
		Format formatter = new SimpleDateFormat("MMM dd, yyyy");
		return new String[] { transaction.getSymbol().getTicker(), transaction.getSymbol().getName(), transaction.getType().toString(),
		        formatter.format(transaction.getDate()), BigDecimalUtils.getString(transaction.getQuantity()),
		        BigDecimalUtils.getString(transaction.getPrice()),
		        /** cash value here */
		        BigDecimalUtils.getString(transaction.getCommission()), transaction.getLabelsValues().toString() };
	}
}
