package com.proserus.stocks.bp.model;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.proserus.stocks.bo.symbols.CurrencyEnum;
import com.proserus.stocks.bo.symbols.SectorEnum;
import com.proserus.stocks.bo.symbols.Symbol;
import com.proserus.stocks.bo.transactions.Label;
import com.proserus.stocks.bo.transactions.TransactionType;
import com.proserus.stocks.bo.utils.LoggerUtils;

public class Filter {
	private Map<String, Label> labels = new HashMap<String, Label>();

	private Integer year = null;

	private TransactionType type = null;

	private SectorEnum sector = null;

	public SectorEnum getSector() {
		return sector;
	}

	public void setSector(SectorEnum sector) {
		this.sector = sector;
	}

	public CurrencyEnum getCurrency() {
		return currency;
	}

	public void setCurrency(CurrencyEnum currency) {
		this.currency = currency;
	}

	private CurrencyEnum currency = null;

	public Collection<Label> getLabels() {
		return labels.values();
	}

	public Map<String, Label> getLabelsMap() {
		return labels;
	}

	public void setLabels(Map<String, Label> labels) {
		this.labels = labels;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public boolean isDateFiltered() {
		return year != null;
	}

	public boolean isFiltered() {
		return isDateFiltered() || isSymbolFiltered() || isLabelsFiltered() || isTypeFiltered() || isCurrencyFiltered()
				|| isSectorFiltered();
	}

	public boolean isSymbolFiltered() {
		return symbol != null && !symbol.getTicker().isEmpty();
	}

	public boolean isTypeFiltered() {
		return type != null;
	}

	public boolean isSectorFiltered() {
		return sector != null;
	}

	public boolean isCurrencyFiltered() {
		return currency != null;
	}

	public boolean isLabelsFiltered() {
		return !labels.isEmpty();
	}

	public boolean isTransactionBeforeFilteredYear(Calendar calendar) {
		return isDateFiltered() && (getYear() > calendar.get(Calendar.YEAR));
	}

	public void setTransactionType(TransactionType type) {
		this.type = type;
	}

	public TransactionType getTransactionType() {
		return type;
	}

	public Integer getYear() {
		return year;
	}

	public void addLabel(Label label) {
		this.labels.put(label.getName(), label);
	}

	public void removeLabel(Label label) {
		this.labels.remove(label.getName());
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	private Symbol symbol = null;

	@Override
	public String toString() {
		assert LoggerUtils.validateCalledFromLogger() : LoggerUtils.callerException();
		return "Filter [labels=" + labels + ", year=" + year + ", type=" + type + ", sector=" + sector + ", currency=" + currency
				+ ", symbol=" + symbol + "]";
	}

}
