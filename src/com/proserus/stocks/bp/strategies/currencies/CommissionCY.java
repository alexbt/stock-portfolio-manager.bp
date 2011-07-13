package com.proserus.stocks.bp.strategies.currencies;

import java.math.BigDecimal;

import com.proserus.stocks.model.analysis.Analysis;

public class CommissionCY extends AbstractStrategyCumulative {

	@Override
	public BigDecimal getAnalysisValue(Analysis s) {
		calculsLog.info("getCommission: " + s.getCommission());
		return s.getCommission();
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setCommission: " + value);
		analysis.setCommission(value);
	}
}
