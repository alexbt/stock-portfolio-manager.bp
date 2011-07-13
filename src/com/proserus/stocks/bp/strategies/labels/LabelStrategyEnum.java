package com.proserus.stocks.bp.strategies.labels;

import org.apache.log4j.Logger;

import com.proserus.stocks.bp.strategies.symbols.MarketValueSYM;
import com.proserus.stocks.bp.strategies.symbols.SymbolStrategySYM;

public enum LabelStrategyEnum {
	MARKET_VALUE(new MarketValueSYM());

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

	private SymbolStrategySYM strategy;

	LabelStrategyEnum(SymbolStrategySYM strategy) {
		this.strategy = strategy;
	}

	public SymbolStrategySYM getStrategy() {
		return strategy;
	}
}
