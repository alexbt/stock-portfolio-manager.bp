package com.proserus.stocks.bp.strategies;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.proserus.stocks.model.analysis.Analysis;

public class OverallReturn extends AnalysisStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + OverallReturn.class.getName());

	@Override
	protected void process(Analysis analysis) {
		// BigDecimal value = analysis.getDividendYield().add(analysis.getMarketGrowth().add(analysis.getCapitalGainPercent()));
		// analysis.setOverallReturn(value);
		double value = 0d;
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("Overall Return = (capitalGain / (currentCost+totalSold)) + marketGrowth + dividendYield");
			calculsLog.info("getCapitalGain: " + analysis.getCapitalGain());
			calculsLog.info("getCurrentCost: " + analysis.getCurrentCost());
			calculsLog.info("getTotalSold: " + analysis.getTotalSold());
			calculsLog.info("getMarketGrowth: " + analysis.getMarketGrowth());
			calculsLog.info("getDividendYield: " + analysis.getDividendYield());
		}

		if (!analysis.getCapitalGain().equals(BigDecimal.ZERO)) {
			value = analysis.getCapitalGain().doubleValue() / analysis.getCurrentCost().add(analysis.getTotalSold()).doubleValue();
		} else {
			calculsLog.info("Capital gain is 0: (currentCost+totalSold): Overall return is market growth + dividend yield.");
		}
		value += analysis.getMarketGrowth().doubleValue();
		value += analysis.getDividendYield().doubleValue();
		calculsLog.info("Calculated OverallReturn successfully!");

		calculsLog.info("setOverallReturn: " + value);
		analysis.setOverallReturn(new BigDecimal(value));
	}
}
