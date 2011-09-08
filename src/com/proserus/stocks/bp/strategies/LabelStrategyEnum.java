package com.proserus.stocks.bp.strategies;

import org.apache.log4j.Logger;

import com.proserus.stocks.bp.strategies.impl.MarketValue;
import com.proserus.stocks.bp.strategies.impl.SymbolStrategy;

public enum LabelStrategyEnum {
	MARKET_VALUE(new MarketValue());

	protected static Logger calculsLog = Logger.getLogger("calculs." + LabelStrategyEnum.class.getName());
	
	static {
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("--------------------------------------");
			calculsLog.info("--------------------------------------");
			for(LabelStrategyEnum en: values()){
				calculsLog.info("SymbolStrategies: " + en.name());
			}
			calculsLog.info("--------------------------------------");
		}
	}

	private SymbolStrategy strategy;

	LabelStrategyEnum(SymbolStrategy strategy) {
		this.strategy = strategy;
	}

	public SymbolStrategy getStrategy() {
		return strategy;
	}
}
