package com.proserus.stocks.bp.strategies.advanced;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.analysis.ViewableAnalysis;
import com.proserus.stocks.bp.strategies.fw.AdvancedStrategy;

public class OverallReturn extends AdvancedStrategy {
	protected static Logger calculsLog = LoggerFactory.getLogger("calculs." + OverallReturn.class.getName());

	@Override
	protected BigDecimal process(ViewableAnalysis analysis) {
		// BigDecimal value =
		// analysis.getDividendYield().add(analysis.getMarketGrowth().add(analysis.getCapitalGainPercent()));
		// analysis.setOverallReturn(value);
		BigDecimal value = BigDecimal.ZERO;
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("Overall Return = (capitalGain / (currentCost+totalSold)) + marketGrowth + dividendYield");
			calculsLog.info("getCapitalGain: {}", new Object[] { analysis.getCapitalGain() });
			calculsLog.info("getCurrentCost: {}", new Object[] { analysis.getTotalCost() });
			calculsLog.info("getTotalSold: {}", new Object[] { analysis.getTotalSold() });
			calculsLog.info("getMarketGrowth: {}", new Object[] { analysis.getMarketGrowth() });
			calculsLog.info("getDividendYield: {}", new Object[] { analysis.getDividendYield() });
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

		calculsLog.info("setOverallReturn: {}", new Object[] { value });
		return value;
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		analysis.setOverallReturn(value);
	}
}
