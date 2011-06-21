package com.proserus.stocks.bp.strategies.currencies;

import java.math.BigDecimal;

import com.proserus.stocks.model.analysis.Analysis;

public class CostCY extends AbstractStrategyCumulative {
	@Override
	public BigDecimal getAnalysisValue(Analysis s) {
		calculsLog.info("getCurrentCost: " + s.getCurrentCost());
		return s.getCurrentCost();
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setCost: " + value);
		analysis.setCost(value);
	}
}
