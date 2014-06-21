package com.proserus.stocks.bp.strategies;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.model.Filter;
import com.proserus.stocks.bp.utils.DateUtil;
public class StartOfPeriod implements SymbolStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + CapitalGain.class.getName());
	
	@Override
	public void process(Analysis analysis, Collection<Transaction> transactions, Filter filter) {
		//TODO Logging
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("Logging not completely implemented for this calcul");
		}
		if (transactions.size() > 0) {
			Transaction t = transactions.iterator().next();
			// TODO Manage Date better
			DateTime date = DateUtil.getFilteredStartDate(new DateTime(t.getDate()), filter);//TODO remove Joda
			analysis.setStartOfPeriod(date);
		}
	}

}
