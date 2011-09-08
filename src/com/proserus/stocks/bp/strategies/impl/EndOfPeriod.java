package com.proserus.stocks.bp.strategies.impl;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.Filter;
import com.proserus.stocks.bp.utils.DateUtil;

public class EndOfPeriod implements SymbolStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + EndOfPeriod.class.getName());
	
	@Override
	public void process(Analysis analysis, Collection<Transaction> transactions, Filter filter) {
		DateTime date = DateUtil.getFilteredEndDate(filter);
		if(calculsLog.isInfoEnabled()){
			calculsLog.info("--------------------------------------");
			calculsLog.info("EndOfPeriod = Today OR (last day of filtered year");
			calculsLog.info("EndOfPeriod = " + date);
		}
		analysis.setEndOfPeriod(date);
	}

}
