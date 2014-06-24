package com.proserus.stocks.bp.strategies;

import java.util.Calendar;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.model.Filter;
import com.proserus.stocks.bp.utils.DateUtils;

public class EndOfPeriod implements SymbolStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + EndOfPeriod.class.getName());

	@Override
	public void process(Analysis analysis, Collection<Transaction> transactions, Filter filter) {
		Calendar endDate;
		if (filter.isDateFiltered()) {
			endDate = DateUtils.getEndOfYear(filter.getYear());
		} else {
			endDate = Calendar.getInstance();
		}
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("EndOfPeriod = Today OR (last day of filtered year");
			calculsLog.info("EndOfPeriod = " + endDate);
		}
		analysis.setEndOfPeriod(endDate);
	}

}
