package com.proserus.stocks.bp.strategies.currencies;

import java.math.BigDecimal;

import com.proserus.stocks.model.analysis.Analysis;

public class QuantitySoldCY extends AbstractStrategyCumulative {

	@Override
	public BigDecimal getAnalysisValue(Analysis s) {
		calculsLog.info("getQuantitySold: " + s.getQuantitySold());
		return s.getQuantitySold();
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setQuantitySold: " + value);
		analysis.setQuantitySold(value);
	}
}
