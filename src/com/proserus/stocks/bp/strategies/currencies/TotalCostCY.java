package com.proserus.stocks.bp.strategies.currencies;

import java.math.BigDecimal;

import com.proserus.stocks.model.analysis.Analysis;

public class TotalCostCY extends AbstractStrategyCumulative {
	@Override
	public BigDecimal getAnalysisValue(Analysis s) {
		calculsLog.info("getTotalCost: " + s.getTotalCost());
		return s.getTotalCost();
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setTotalCost: " + value);
		analysis.setTotalCost(value);
	}
}
