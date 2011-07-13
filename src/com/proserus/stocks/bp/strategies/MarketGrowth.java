package com.proserus.stocks.bp.strategies;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.proserus.stocks.model.analysis.Analysis;

public class MarketGrowth extends AnalysisStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + MarketGrowth.class.getName());

	protected void process(Analysis analysis) {
		BigDecimal value = BigDecimal.ZERO;

		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("getCurrentCost: " + analysis.getCurrentCost());
			calculsLog.info("getMarketValue: " + analysis.getMarketValue());
			calculsLog.info("marketGrowth = ((marketValue - currentCost) / currentCost) x 100");
		}

		if (analysis.getCurrentCost().intValue() != 0) {
			value = analysis.getMarketValue();
			value = value.subtract(analysis.getCurrentCost());
			value = value.divide(analysis.getCurrentCost(), BigDecimal.ROUND_UP);
			value = value.multiply(BigDecimal.valueOf(100));
			calculsLog.info("Calculated MarketGrowth successfully!");
		} else {
			calculsLog.info("Failed to calculate MarketGrowth: current Cost is 0");
		}

		calculsLog.info("setMarketGrowth = " + value);
		analysis.setMarketGrowth(value);
	}
}
