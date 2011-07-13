package com.proserus.stocks.bp.strategies.currencies;

import java.math.BigDecimal;

import com.proserus.stocks.model.analysis.Analysis;

public class CapitalGainCY extends AbstractStrategyCumulative {

	@Override
	public BigDecimal getAnalysisValue(Analysis s) {
		calculsLog.info("getCapitalGain: " + s.getCapitalGain());
		return s.getCapitalGain();
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setCapitalGain: " + value);
		analysis.setCapitalGain(value);
	}
}
