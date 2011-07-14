package com.proserus.stocks.bp;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;

import com.proserus.stocks.model.transactions.Transaction;
import com.proserus.stocks.utils.BigDecimalUtils;

public class CsvModel {
	public static final String DATE_FORMAT = "MMM dd, yyyy";
	private String symbol;
	private String name;
	private String price;
	private String quantity;
	private String commission;
	private String labels;
	private String type;
	private String date;

	public CsvModel() {

	}

	public CsvModel(Transaction transaction) {
		Format formatter = new SimpleDateFormat(DATE_FORMAT);
		date = formatter.format(transaction.getDate());
		symbol = transaction.getSymbol().getTicker();
		name = transaction.getSymbol().getName();
		type = transaction.getType().toString();
		quantity = BigDecimalUtils.getString(transaction.getQuantity());
		price = BigDecimalUtils.getString(transaction.getPrice());
		/** cash value here */
		commission = BigDecimalUtils.getString(transaction.getCommission());
		labels = transaction.getLabelsValues().toString();
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getCommission() {
		return commission;
	}
	
	private BigDecimal returnBigDecimal(String value){
		try {
			return new BigDecimal(value);
		} catch (NumberFormatException e) {
			return BigDecimal.ZERO;
		}
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	static public String[] getHeaders() {
		return new String[] { "Name", "Symbol", "Type", "Date", "Quantity", "Price", "Commission", "Labels" };
	}

	public String[] getFields() {
		return new String[] { getName(), getSymbol(), getType(), getDate(), getQuantity(), getPrice(), getCommission(), getLabels() };
	}

}