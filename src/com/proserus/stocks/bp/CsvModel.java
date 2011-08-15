package com.proserus.stocks.bp;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.proserus.stocks.model.transactions.Transaction;
import com.proserus.stocks.utils.BigDecimalUtils;

public class CsvModel {
	public static final String[] DATE_FORMATS = new String[]{"MMM dd, yyyy","yyyy-MM-dd","yyyyMMdd", "yyyy/MM/dd","MMM dd yyyy","MMM-dd-yyyy","MMM/dd/yyyy"};
	private String symbol;
	private String name = "";
	private String price;
	private String quantity;
	private String commission = "0.00";
	private String labels = "";
	private String type;
	private String date;
	
	public CsvModel() {

	}

	public CsvModel(Transaction transaction) {
		Format formatter = new SimpleDateFormat(DATE_FORMATS[0]);
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
	
	//google header
	public void setShares(String quantity) {
		this.quantity = quantity;
	}

	public String getCommission() {
		return commission;
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
	
	public void setNotes(String labels) {
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
	
	static public Map<String,String> getColumnMapping() {
		Map<String,String> mapping = new HashMap<String,String>();
		mapping.put("Name","Name");
		mapping.put("Company","Name");
		mapping.put("CompanyName","Name");
		mapping.put("Company Name","Name");
		mapping.put("﻿Name","Name");//Google format when 1st column
		
		mapping.put("Symbol","Symbol");
		mapping.put("Ticker","Symbol");
		mapping.put("Asset","Symbol");//Other
		mapping.put("﻿Symbol","Symbol");//Google format when 1st column
		
		mapping.put("Type","Type");
		mapping.put("Action","Type");
		mapping.put("Activity","Type");
		mapping.put("Transaction","Type");
		mapping.put("﻿Type","Type");//Google format when 1st column
		
		mapping.put("Date","Date");
		mapping.put("ValueDate","Date");
		mapping.put("PurchaseDate","Date");
		mapping.put("Purchase Date","Date");
		mapping.put("Value Date","Date");
		mapping.put("﻿Date","Date");//Google format when 1st column
		
		mapping.put("Quantity","Quantity");
		mapping.put("Shares/Ratio","Quantity");
		mapping.put("Number Of Shares","Quantity");
		mapping.put("NumberOfShares","Quantity");
		mapping.put("Shares","Quantity"); //Google format
		mapping.put("﻿Shares","Quantity");//Google format when 1st column
		
		mapping.put("Price","Price");
		mapping.put("Price Per Share","Price");
		mapping.put("PricePerShare","Price");
		mapping.put("Average Price","Price");
		mapping.put("AveragePrice","Price");
		mapping.put("Cost","Price");
		mapping.put("﻿Price","Price");//Google format when 1st column
		
		mapping.put("Commission","Commission");
		mapping.put("﻿Commission","Commission");//Google format when 1st column
		
		mapping.put("Labels","Labels");
		mapping.put("Other","Labels");
		mapping.put("Notes","Labels");//Google format
		mapping.put("﻿Notes","Labels");//Google format when 1st column
		return mapping;
	}

	public String[] getFields() {
		return new String[] { getName(), getSymbol(), getType(), getDate(), getQuantity(), getPrice(), getCommission(), getLabels() };
	}

}