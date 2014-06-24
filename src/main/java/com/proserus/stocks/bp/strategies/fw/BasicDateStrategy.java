package com.proserus.stocks.bp.strategies.fw;

import java.util.Calendar;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.model.Filter;

public abstract class BasicDateStrategy extends BasicStrategy<Calendar> {
	protected static Logger calculsLog = Logger.getLogger("calculs." + BasicDateStrategy.class.getName());

	@Override
	public void process(Analysis analysis, Collection<Transaction> transactions, Filter filter) {
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
		}
		Calendar value = getDateValue(transactions, filter);
		if (value == null) {
			value = getDefaultAnalysisValue();
		}
		setAnalysisValue(analysis, value);
	}

	public abstract void setAnalysisValue(Analysis analysis, Calendar value);

	@Override
	public Calendar getDefaultAnalysisValue() {
		return Calendar.getInstance();
	}

	@Override
	public Calendar getTransactionValue(Transaction t, Filter filter) {
		throw new AssertionError();
	}

	abstract public Calendar getDateValue(Collection<Transaction> transactions, Filter filter);
}
