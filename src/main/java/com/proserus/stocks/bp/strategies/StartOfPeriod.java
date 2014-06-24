package com.proserus.stocks.bp.strategies;

import java.util.Calendar;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.model.Filter;
import com.proserus.stocks.bp.utils.DateUtils;

public class StartOfPeriod implements SymbolStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + CapitalGain.class.getName());

	@Override
	public void process(Analysis analysis, Collection<Transaction> transactions, Filter filter) {
		// TODO Logging
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("Logging not implemented for this");
		}
		Calendar startDate = Calendar.getInstance();
		if (filter.isDateFiltered()) {
			startDate = DateUtils.getStartOfYear(filter.getYear());
		} else {
			for (Transaction t : transactions) {
				if (t.getCalendar().before(startDate)) {
					startDate = t.getCalendar();
				}
			}
		}
		analysis.setStartOfPeriod(startDate);
	}
}
