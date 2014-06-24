package com.proserus.stocks.bp.strategies.dates;

import java.util.Calendar;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.model.Filter;
import com.proserus.stocks.bp.strategies.advanced.CapitalGain;
import com.proserus.stocks.bp.strategies.fw.BasicDateStrategy;
import com.proserus.stocks.bp.utils.DateUtils;

public class StartOfPeriod extends BasicDateStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + CapitalGain.class.getName());

	@Override
	public void setAnalysisValue(Analysis analysis, Calendar value) {
		analysis.setStartOfPeriod(value);
	}

	@Override
	public Calendar getDefaultAnalysisValue() {
		return Calendar.getInstance();
	}

	@Override
	public Calendar getDateValue(Collection<Transaction> transactions, Filter filter) {
		Calendar startDate = null;
		if (filter.isDateFiltered()) {
			startDate = DateUtils.getStartOfYear(filter.getYear());
		} else {
			for (Transaction t : transactions) {
				if (t.getCalendar().before(startDate)) {
					startDate = t.getCalendar();
				}
			}
		}
		return startDate;
	}

}
