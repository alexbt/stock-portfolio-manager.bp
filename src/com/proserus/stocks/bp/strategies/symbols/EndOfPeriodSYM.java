package com.proserus.stocks.bp.strategies.symbols;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.proserus.stocks.bp.DateUtil;
import com.proserus.stocks.bp.FilterBp;
import com.proserus.stocks.model.analysis.Analysis;
import com.proserus.stocks.model.transactions.TransactionImpl;

public class EndOfPeriodSYM implements SymbolStrategySYM {
	protected static Logger calculsLog = Logger.getLogger("calculs." + EndOfPeriodSYM.class.getName());
	
	@Override
	public void process(Analysis analysis, Collection<TransactionImpl> transactions, FilterBp filter) {
		DateTime date = DateUtil.getFilteredEndDate(filter);
		if(calculsLog.isInfoEnabled()){
			calculsLog.info("--------------------------------------");
			calculsLog.info("EndOfPeriod = Today OR (last day of filtered year");
			calculsLog.info("EndOfPeriod = " + date);
		}
		analysis.setEndOfPeriod(date);
	}

}
