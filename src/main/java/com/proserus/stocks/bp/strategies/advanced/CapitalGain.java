package com.proserus.stocks.bp.strategies.advanced;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.analysis.ViewableAnalysis;
import com.proserus.stocks.bp.strategies.fw.AdvancedStrategy;

public class CapitalGain extends AdvancedStrategy {
	protected static Logger calculsLog = LoggerFactory.getLogger("calculs." + CapitalGain.class.getName());

	@Override
	public BigDecimal process(ViewableAnalysis analysis) {
		BigDecimal value = BigDecimal.ZERO;
		BigDecimal divider = analysis.getQuantitySold().multiply(analysis.getAveragePrice());

		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("CapitalGainSYM = Sold amount of selling prices - (numberofSharesSold X average Price)");
			calculsLog.info("getTotalSold: " + analysis.getTotalSold());
			calculsLog.info("getQuantitySold: " + analysis.getQuantitySold());
			calculsLog.info("getAveragePrice: " + analysis.getAveragePrice());
		}
		if (!divider.equals(BigDecimal.ZERO)) {
			value = analysis.getTotalSold().subtract(divider);
			calculsLog.info("Calculated CapitalGainSYM successfully!");
		} else {
			calculsLog.error("Failed to calculated CapitalGainSYM: (numberofSharesSold X average Price) is 0");
		}

		calculsLog.info("setCapitalGain = " + value);
		return value;
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		analysis.setCapitalGain(value);
	}

}
