package com.proserus.stocks.bp.strategies;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.log4j.Logger;

import com.proserus.stocks.bo.analysis.Analysis;

public class OverallReturn extends AbstractAnalysisStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + OverallReturn.class.getName());

	@Override
	protected void process(Analysis analysis) {
		// BigDecimal value =
		// analysis.getDividendYield().add(analysis.getMarketGrowth().add(analysis.getCapitalGainPercent()));
		// analysis.setOverallReturn(value);
		BigDecimal value = BigDecimal.ZERO;
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("Overall Return = (capitalGain / (currentCost+totalSold)) + marketGrowth + dividendYield");
			calculsLog.info("getCapitalGain: " + analysis.getCapitalGain());
			calculsLog.info("getCurrentCost: " + analysis.getTotalCost());
			calculsLog.info("getTotalSold: " + analysis.getTotalSold());
			calculsLog.info("getMarketGrowth: " + analysis.getMarketGrowth());
			calculsLog.info("getDividendYield: " + analysis.getDividendYield());
		}

		if (!analysis.getCapitalGain().equals(BigDecimal.ZERO)) {
			value = analysis.getCapitalGain().divide(analysis.getTotalCost().add(analysis.getTotalSold()), RoundingMode.HALF_EVEN);
		} else {
			calculsLog.info("Capital gain is 0: (currentCost+totalSold): Overall return is market growth + dividend yield.");
		}
		if (analysis.getMarketGrowth() != null) {
			value = value.add(analysis.getMarketGrowth());
		}
		value = value.add(analysis.getDividendYield());
		calculsLog.info("Calculated OverallReturn successfully!");

		calculsLog.info("setOverallReturn: " + value);
		setValue(analysis, value);
	}

	@Override
	protected void setValue(Analysis analysis, BigDecimal value) {
		analysis.setOverallReturn(value);
	}
}
