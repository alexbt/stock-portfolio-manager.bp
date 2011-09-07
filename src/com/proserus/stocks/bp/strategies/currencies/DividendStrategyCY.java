package com.proserus.stocks.bp.strategies.currencies;

import java.math.BigDecimal;

import com.proserus.stocks.model.analysis.Analysis;

public class DividendStrategyCY extends AbstractStrategyCumulative {
	@Override
	public BigDecimal getAnalysisValue(Analysis s) {
		calculsLog.info("getDividend: " + s.getDividend());
		return s.getDividend();
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setDividend: " + value);
		analysis.setDividend(value);
	}
}
