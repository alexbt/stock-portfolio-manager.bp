package com.proserus.stocks.bp.strategies.advanced;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.analysis.ViewableAnalysis;
import com.proserus.stocks.bo.utils.BigDecimalUtils;
import com.proserus.stocks.bp.strategies.fw.AdvancedStrategy;

public class MarketGrowth extends AdvancedStrategy {
	protected static Logger calculsLog = LoggerFactory.getLogger("calculs." + MarketGrowth.class.getName());

	protected BigDecimal process(ViewableAnalysis analysis) {
		BigDecimal value = BigDecimal.ZERO;

		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("getCurrentCost: {}", new Object[] { analysis.getTotalCost() });
			calculsLog.info("getMarketValue: {}", new Object[] { analysis.getMarketValue() });
			calculsLog.info("marketGrowth = ((marketValue - currentCost) / currentCost) x 100");
		}

		try {
			if (!analysis.getTotalCost().equals(BigDecimal.ZERO)) {

				value = analysis.getMarketValue();
				value = value.subtract(analysis.getCostBasis());
				value = value.divide(analysis.getCostBasis(), BigDecimal.ROUND_UP);
				value = value.multiply(BigDecimalUtils.HUNDRED);
				calculsLog.info("Calculated MarketGrowth successfully!");
			} else {
				calculsLog.info("Failed to calculate MarketGrowth: current Cost is 0");
			}

			calculsLog.info("setMarketGrowth = {}", new Object[] { value });
		} catch (java.lang.ArithmeticException e) {
			calculsLog.info("setMarketGrowth = Infinite");
			value = null;
		}

		return value;

	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		analysis.setMarketGrowth(value);
	}
}
