package com.proserus.stocks.bp.strategies.dates;

import java.util.Calendar;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.model.Filter;
import com.proserus.stocks.bp.strategies.fw.BasicDateStrategy;
import com.proserus.stocks.bp.utils.DateUtils;

public class EndOfPeriod extends BasicDateStrategy {
	protected static Logger calculsLog = LoggerFactory.getLogger("calculs." + EndOfPeriod.class.getName());

	@Override
	public Calendar getDefaultAnalysisValue() {
		return Calendar.getInstance();
	}

	@Override
	public void setAnalysisValue(Analysis analysis, Calendar value) {
		analysis.setEndOfPeriod(value);
	}

	@Override
	public Calendar getDateValue(Collection<Transaction> transactions, Filter filter) {
		Calendar endDate = null;
		if (filter.isDateFiltered()) {
			endDate = DateUtils.getEndOfYear(filter.getYear());
		}
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("EndOfPeriod = Today OR (last day of filtered year");
			calculsLog.info("EndOfPeriod = " + endDate);
		}

		return endDate;
	}
}
