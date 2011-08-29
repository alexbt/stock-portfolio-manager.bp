package com.proserus.stocks.bp.strategies.symbols;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.proserus.stocks.bp.FilterBp;
import com.proserus.stocks.model.analysis.Analysis;
import com.proserus.stocks.model.transactions.Transaction;

public class AverageCostPerShareSYM implements SymbolStrategySYM {
	protected static Logger calculsLog = Logger.getLogger("calculs." + AverageCostPerShareSYM.class.getName());
	
	@Override
	public void process(Analysis analysis, Collection<Transaction> transactions, FilterBp filter) {
		BigDecimal value = BigDecimal.ZERO;
		
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("AverageCostPerShareSYM = Cost / Quantity");
			calculsLog.info("getQuantityBuy: " + analysis.getQuantityBuy());
			calculsLog.info("getCurrentCost: " + analysis.getTotalCost());
		}
		
		if (!analysis.getQuantityBuy().equals(BigDecimal.ZERO)) {
			value = analysis.getTotalCost().divide(analysis.getQuantityBuy(),BigDecimal.ROUND_UP);
			calculsLog.info("Calculated AverageCostPerShare successfully!");
		}else{
			calculsLog.error("Failed to calculated AverageCostPerShare: Quantity is 0");
		}
		
		calculsLog.info("setAveragePrice = " +  value);
		analysis.setAveragePrice(value);
	}
}
