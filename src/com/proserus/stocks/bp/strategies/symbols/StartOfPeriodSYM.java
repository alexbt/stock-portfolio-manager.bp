package com.proserus.stocks.bp.strategies.symbols;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.proserus.stocks.bp.DateUtil;
import com.proserus.stocks.bp.FilterBp;
import com.proserus.stocks.model.analysis.Analysis;
import com.proserus.stocks.model.transactions.Transaction;

public class StartOfPeriodSYM implements SymbolStrategySYM {
	protected static Logger calculsLog = Logger.getLogger("calculs." + CapitalGainSYM.class.getName());
	
	@Override
	public void process(Analysis analysis, Collection<Transaction> transactions, FilterBp filter) {
		//TODO Logging
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("Logging not completely implemented for this calcul");
		}
		if (transactions.size() > 0) {
			Transaction t = transactions.iterator().next();
			// TODO Manage Date better
			DateTime date = DateUtil.getFilteredStartDate(new DateTime(t.getDate()), filter);
			analysis.setStartOfPeriod(date);
		}
	}

}
