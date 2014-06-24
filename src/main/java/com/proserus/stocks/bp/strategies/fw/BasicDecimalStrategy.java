package com.proserus.stocks.bp.strategies.fw;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.model.Filter;

public abstract class BasicDecimalStrategy extends BasicStrategy<BigDecimal> {
	protected static Logger calculsLog = Logger.getLogger("calculs." + BasicDecimalStrategy.class.getName());

	@Override
	public void process(Analysis analysis, Collection<Transaction> transactions, Filter filter) {
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
		}
		BigDecimal value = getDefaultAnalysisValue();
		try {
			for (Transaction t : transactions) {
				value = value.add(getTransactionValue(t, filter));
			}

			if (value == null || Double.valueOf(value.doubleValue()).isInfinite() || Double.valueOf(value.doubleValue()).isNaN()) {
				value = getDefaultAnalysisValue();// TODO Add logging
			}
		} catch (NumberFormatException e) {
			value = getDefaultAnalysisValue();// TODO Add logging
		}
		setAnalysisValue(analysis, value);
	}

	public abstract BigDecimal getTransactionValue(Transaction t, Filter filter);

	public abstract void setAnalysisValue(Analysis analysis, BigDecimal value);

	@Override
	public BigDecimal getDefaultAnalysisValue() {
		return BigDecimal.ZERO;
	}
}
