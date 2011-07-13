package com.proserus.stocks.bp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.time.Year;
import org.joda.time.DateTime;

import com.google.inject.Inject;
import com.proserus.stocks.model.common.ObservableModel;
import com.proserus.stocks.model.symbols.Symbol;
import com.proserus.stocks.model.transactions.Label;

public class FilterBp extends ObservableModel {
	private SymbolsBp symbolsBp;
	
	@Inject
	public void setSymbolsBp(SymbolsBp symbolsBp) {
    	this.symbolsBp = symbolsBp;
    }

	@Inject
	public void setTransactionsBp(TransactionsBp transactionsBp) {
    	this.transactionsBp = transactionsBp;
    }

	@Inject
	public void setAnalysisBp(AnalysisBp analysisBp) {
    	this.analysisBp = analysisBp;
    }

	private TransactionsBp transactionsBp;
	private AnalysisBp analysisBp;

	private Map<String, Label> labels = new HashMap<String, Label>();

	private Year year = null;

	public Collection<Label> getLabels() {
		return labels.values();
	}

	public Map<String, Label> getLabelsMap() {
		return labels;
	}

	public void setLabels(Map<String, Label> labels) {
		this.labels = labels;
		setChanged();
		notifyObservers();
		transactionsBp.changeFilter();
		symbolsBp.changeFilter();
		analysisBp.recalculate(this);
	}

	public void setYear(Year year) {
		if (year != null) {
			this.year = new Year(year.getYear());
		} else {
			this.year = null;
		}
		setChanged();
		notifyObservers();
		transactionsBp.changeFilter();
		symbolsBp.changeFilter();
		analysisBp.recalculate(this);
	}

	public boolean isDateFiltered() {
		return year != null;
	}
	
	public boolean isFiltered(){
		return isDateFiltered() || isSymbolFiltered() || isLabelsFiltered();
	}
	
	public boolean isSymbolFiltered() {
		return symbol!=null && !symbol.getTicker().isEmpty();
	}
	
	public boolean isLabelsFiltered() {
		return !labels.isEmpty();
	}

	public boolean isFilteredYearAfter(DateTime date) {
		//TODO Manage Date better
		return isDateFiltered() && (getYear().getYear() > date.getYear());
	}

	public Year getYear() {
		return year;
	}
	
	public void addLabel(Label label) {
		this.labels.put(label.getName(), label);
		setChanged();
		notifyObservers();
		transactionsBp.changeFilter();
		symbolsBp.changeFilter();
		analysisBp.recalculate(this);
	}

	public void removeLabel(Label label) {
		this.labels.remove(label.getName());
		setChanged();
		notifyObservers();
		transactionsBp.changeFilter();
		symbolsBp.changeFilter();
		analysisBp.recalculate(this);
	}

	public Symbol getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
		setChanged();
		notifyObservers();
		transactionsBp.changeFilter();
		symbolsBp.changeFilter();
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
