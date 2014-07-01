package com.proserus.stocks.bp.strategies.advanced;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.analysis.ViewableAnalysis;
import com.proserus.stocks.bp.strategies.fw.AdvancedStrategy;

public class CostBasis extends AdvancedStrategy {
	protected static Logger calculsLog = LoggerFactory.getLogger("calculs." + CostBasis.class.getName());

	@Override
	public BigDecimal process(ViewableAnalysis analysis) {
		BigDecimal value = BigDecimal.ZERO;

		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("CostBasisSYM = quantity x averagePrice");
			calculsLog.info("getQuantity: {}", new Object[] { analysis.getQuantity() });
			calculsLog.info("getAveragePrice: {}", new Object[] { analysis.getAveragePrice() });
		}
		value = analysis.getAveragePrice().multiply(analysis.getQuantity());

		// value = value.add(analysis.getTotalCost().subtract(value));
		// value.subtract(analysis.getTotalCost().subtract(analysis.getQua))

		calculsLog.info("setAveragePrice = " + value);
		return value;
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		analysis.setCostBasis(value);
	}
}
