package com.proserus.stocks.bp.strategies;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.model.Filter;

public class CapitalGain implements SymbolStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + CapitalGain.class.getName());
	
	@Override
	public void process(Analysis analysis, Collection<Transaction> transactions, Filter filter) {
		BigDecimal value = BigDecimal.ZERO;
		BigDecimal divider = analysis.getQuantitySold().multiply(analysis.getAveragePrice());

		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("CapitalGainSYM = Sold amount of selling prices - (numberofSharesSold X average Price)");
			calculsLog.info("getTotalSold: " + analysis.getTotalSold());
			calculsLog.info("getQuantitySold: " + analysis.getQuantitySold());
			calculsLog.info("getAveragePrice: " + analysis.getAveragePrice());
		}
		if (divider.doubleValue()!=0) {
			value = analysis.getTotalSold().subtract(divider);
			calculsLog.info("Calculated CapitalGainSYM successfully!");
		}else{
			calculsLog.error("Failed to calculated CapitalGainSYM: (numberofSharesSold X average Price) is 0");
		}
		
		calculsLog.info("setCapitalGain = " +  value);
		analysis.setCapitalGain(value);
	}

}
