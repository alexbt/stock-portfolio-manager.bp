package com.proserus.stocks.bp.strategies.currencies;

import java.math.BigDecimal;

import com.proserus.stocks.model.analysis.Analysis;

public class MarketValueCY extends AbstractStrategyCumulative {
	@Override
	public BigDecimal getAnalysisValue(Analysis s) {
		calculsLog.info("getMarketValue: " + s.getMarketValue());
		return s.getMarketValue();
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setMarketValue: " + value);
		analysis.setMarketValue(value);
	}
}
