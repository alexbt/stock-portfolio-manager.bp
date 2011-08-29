package com.proserus.stocks.bp.strategies.symbols;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.proserus.stocks.bp.FilterBp;
import com.proserus.stocks.model.analysis.Analysis;
import com.proserus.stocks.model.transactions.Transaction;

public abstract class AbstractStrategyCumulative implements SymbolStrategySYM {
	protected static Logger calculsLog = Logger.getLogger("calculs." + AbstractStrategyCumulative.class.getName());
	@Override
	public void process(Analysis analysis, Collection<Transaction> transactions, FilterBp filter) {
		if(calculsLog.isInfoEnabled()){
			calculsLog.info("--------------------------------------");
		}
		BigDecimal value = BigDecimal.ZERO;
		for (Transaction t : transactions) {
			value = value.add(getTransactionValue(t, filter));
		}
		setAnalysisValue(analysis, value);
	}

	public abstract BigDecimal getTransactionValue(Transaction t, FilterBp filter);

	public abstract void setAnalysisValue(Analysis analysis, BigDecimal value);
}
