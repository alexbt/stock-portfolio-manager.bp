package com.proserus.stocks.bp.strategies;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.proserus.stocks.bo.analysis.Analysis;

public class MarketGrowth extends AbstractAnalysisStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + MarketGrowth.class.getName());

	protected void process(Analysis analysis) {
		BigDecimal value = BigDecimal.ZERO;

		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("getCurrentCost: " + analysis.getTotalCost());
			calculsLog.info("getMarketValue: " + analysis.getMarketValue());
			calculsLog.info("marketGrowth = ((marketValue - currentCost) / currentCost) x 100");
		}

		try{
			if (!analysis.getTotalCost().equals(BigDecimal.ZERO)) {
		
			value = analysis.getMarketValue();
			value = value.subtract(analysis.getCostBasis());
			value = value.divide(analysis.getCostBasis(), BigDecimal.ROUND_UP);
			value = value.multiply(BigDecimal.valueOf(100));
			calculsLog.info("Calculated MarketGrowth successfully!");
		} else {
			calculsLog.info("Failed to calculate MarketGrowth: current Cost is 0");
		}
			
			calculsLog.info("setMarketGrowth = " + value);
			analysis.setMarketGrowth(value);
		}catch(java.lang.ArithmeticException e){
			calculsLog.info("setMarketGrowth = Infinite");
			analysis.setMarketGrowth(null);
		}
		
	}
}
