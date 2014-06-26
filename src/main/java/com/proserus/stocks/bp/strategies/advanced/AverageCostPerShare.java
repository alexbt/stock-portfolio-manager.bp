package com.proserus.stocks.bp.strategies.advanced;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.analysis.ViewableAnalysis;
import com.proserus.stocks.bp.strategies.fw.AdvancedStrategy;

public class AverageCostPerShare extends AdvancedStrategy {
	protected static Logger calculsLog = LoggerFactory.getLogger("calculs." + AverageCostPerShare.class.getName());

	@Override
	public BigDecimal process(ViewableAnalysis analysis) {
		BigDecimal value = BigDecimal.ZERO;

		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("AverageCostPerShareSYM = Cost / Quantity");
			calculsLog.info("getQuantityBuy: " + analysis.getQuantityBuy());
			calculsLog.info("getCurrentCost: " + analysis.getTotalCost());
		}

		if (!analysis.getQuantityBuy().equals(BigDecimal.ZERO)) {
			value = analysis.getTotalCost().divide(analysis.getQuantityBuy(), BigDecimal.ROUND_UP);
			calculsLog.info("Calculated AverageCostPerShare successfully!");
		} else {
			calculsLog.error("Failed to calculated AverageCostPerShare: Quantity is 0");
		}

		calculsLog.info("setAveragePrice = " + value);
		return value;
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		analysis.setAveragePrice(value);
	}
}
