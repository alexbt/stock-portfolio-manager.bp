package com.proserus.stocks.bp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.time.Year;
import org.joda.time.DateTime;

import com.google.inject.Inject;
import com.proserus.stocks.model.symbols.Symbol;
import com.proserus.stocks.model.transactions.Label;
import com.proserus.stocks.model.transactions.TransactionType;

public class FilterBp {
	@Inject
	private AnalysisBp analysisBp;

	private Map<String, Label> labels = new HashMap<String, Label>();

	private Year year = null;
	
	private TransactionType type = null;

	public Collection<Label> getLabels() {
		return labels.values();
	}

	public Map<String, Label> getLabelsMap() {
		return labels;
	}

	public void setLabels(Map<String, Label> labels) {
		this.labels = labels;
		analysisBp.recalculate(this);
	}

	public void setYear(Year year) {
		if (year != null) {
			this.year = new Year(year.getYear());
		} else {
			this.year = null;
		}
		analysisBp.recalculate(this);
	}

	public boolean isDateFiltered() {
		return year != null;
	}
	
	public boolean isFiltered(){
		return isDateFiltered() || isSymbolFiltered() || isLabelsFiltered() || isTypeFiltered();
	}
	
	public boolean isSymbolFiltered() {
		return symbol!=null && !symbol.getTicker().isEmpty();
	}
	
	public boolean isTypeFiltered() {
		return type != null;
	}
	
	public boolean isLabelsFiltered() {
		return !labels.isEmpty();
	}

	public boolean isFilteredYearAfter(DateTime date) {
		//TODO Manage Date better
		return isDateFiltered() && (getYear().getYear() > date.getYear());
	}
	
	public void setTransactionType(TransactionType type){
		this.type = type;
		analysisBp.recalculate(this);
	}
	

	public TransactionType getTransactionType() {
    	return type;
    }

	public Year getYear() {
		return year;
	}
	
	public void addLabel(Label label) {
		this.labels.put(label.getName(), label);
		analysisBp.recalculate(this);
	}

	public void removeLabel(Label label) {
		this.labels.remove(label.getName());
		analysisBp.recalculate(this);
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
		analysisBp.recalculate(this);
	}

	private Symbol symbol = null;

	public String toString() {
		String str = "";
		String labelsStr = labels.values().toString();
		if (labelsStr.compareTo("[]") == 0) {
			labelsStr = "[   ]";
		}

		if (year != null) {
			str += "        Year: [" + year.getYear() + "]";
		} else {
			str += "        Year: [   ]";
		}

		if (symbol != null) {
			str += "        Symbol: [" + getSymbol().getTicker() + "]";
		} else {
			str += "        Symbol: [   ]";
		}

		str += "        Labels: " + labelsStr;
		return str;
	}
}
