package com.proserus.stocks.bp.strategies;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.utils.BigDecimalUtils;

public class DividendYield extends AbstractAnalysisStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + DividendYield.class.getName());

	@Override
	protected void process(Analysis analysis) {
		BigDecimal value = BigDecimal.ZERO;

		BigDecimal divisor = analysis.getTotalCost().add(analysis.getTotalSold()).subtract(analysis.getCapitalGain());

		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("dividend yield = dividend / divisor");
			calculsLog.info("divisor = (getCurrentCost + getTotalSold - getCapitalGain)");
			calculsLog.info("getCurrentCost: " + analysis.getTotalCost());
			calculsLog.info("getTotalSold: " + analysis.getTotalSold());
			calculsLog.info("getCapitalGain: " + analysis.getCapitalGain());
			calculsLog.info("getDividend: " + analysis.getDividend());
			calculsLog.info("divisor: " + divisor);
		}

		// TODO use averageCostPerDay instead
		if (BigDecimalUtils.isPositive(divisor)) {
			value = analysis.getDividend().multiply(BigDecimalUtils.HUNDRED).divide(divisor, BigDecimal.ROUND_UP);
			calculsLog.info("Calculated dividend yield successfully");
		} else {
			calculsLog.info("Failed to calculate dividend yield: divisor is not over 0");
		}

		calculsLog.info("setDividendYield = " + value);
		setValue(analysis, value);
	}

	@Override
	protected void setValue(Analysis analysis, BigDecimal value) {
		analysis.setDividendYield(value);
	}
}
