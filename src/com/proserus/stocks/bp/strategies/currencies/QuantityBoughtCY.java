package com.proserus.stocks.bp.strategies.currencies;

import java.math.BigDecimal;

import com.proserus.stocks.model.analysis.Analysis;

public class QuantityBoughtCY extends AbstractStrategyCumulative {

	@Override
	public BigDecimal getAnalysisValue(Analysis s) {
		calculsLog.info("getQuantityBought: " + s.getQuantityBuy());
		return s.getQuantityBuy();
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setQuantityBought: " + value);
		analysis.setQuantityBuy(value);
	}
}
