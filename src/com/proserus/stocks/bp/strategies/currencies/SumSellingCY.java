package com.proserus.stocks.bp.strategies.currencies;

import java.math.BigDecimal;

import com.proserus.stocks.model.analysis.Analysis;

public class SumSellingCY extends AbstractStrategyCumulative {

	@Override
	public BigDecimal getAnalysisValue(Analysis s) {
		calculsLog.info("getTotalSold: " + s.getTotalSold());
		return s.getTotalSold();
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setTotalSold: " + value);
		analysis.setTotalSold(value);
	}
}
