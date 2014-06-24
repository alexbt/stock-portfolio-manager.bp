package com.proserus.stocks.bp.strategies;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.model.Filter;

public abstract class AbstractStrategyCumulative implements SymbolStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + AbstractStrategyCumulative.class.getName());

	@Override
	public void process(Analysis analysis, Collection<Transaction> transactions, Filter filter) {
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
		}
		BigDecimal value = BigDecimal.ZERO;
		for (Transaction t : transactions) {
			value = value.add(getTransactionValue(t, filter));
		}
		setAnalysisValue(analysis, value);
	}

	public abstract BigDecimal getTransactionValue(Transaction t, Filter filter);

	public abstract void setAnalysisValue(Analysis analysis, BigDecimal value);
}
