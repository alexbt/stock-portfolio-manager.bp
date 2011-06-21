package com.proserus.stocks.bp.strategies.currencies;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.proserus.stocks.model.analysis.Analysis;

public class AveragePriceCY implements CurrencyStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + AveragePriceCY.class.getName());

	@Override
	public void process(Analysis analysis, Analysis symbolAnalysis) {
		BigDecimal value = BigDecimal.ZERO;
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("Logging not completely implemented for this calcul");
		}
		if (analysis.getQuantity().doubleValue() > 0) {
			value = analysis.getCurrentCost().divide(analysis.getQuantity(), BigDecimal.ROUND_UP);
		}
		
		calculsLog.info("setAveragePrice: " + value);
		analysis.setAveragePrice(value);
	}
}
