package com.proserus.stocks.bp.strategies.symbols;

import org.apache.log4j.Logger;

public enum SymbolStrategyEnum {

	QUANTITY_REMAINING(new QuantityRemainingSYM()),
	QUANTITY_SOLD(new QuantitySoldSYM()),
	QUANTITY_BUY(new QuantityBoughtSYM()),
	DIVIDEND(new DividendSYM()),
	COST_BASIS(new CostBasisSYM()),
	COMMISSION(new CommissionSYM()),
	AVERAGE_COST_PER_SHARE(new AverageCostPerShareSYM()),
	SUM_PRICE_SOLD(new SumSellingPricesSYM()),
	START_OF_PERIOD(new StartOfPeriodSYM()),
	END_OF_PERIOD(new EndOfPeriodSYM()),
	MARKET_VALUE(new MarketValueSYM()),
	CAPITAL_GAIN(new CapitalGainSYM());

	protected static Logger calculsLog = Logger.getLogger("calculs." + SymbolStrategyEnum.class.getName());
	static {
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("--------------------------------------");
			calculsLog.info("--------------------------------------");
			for(SymbolStrategyEnum en: values()){
				calculsLog.info("SymbolStrategies: " + en.name());
			}
			calculsLog.info("--------------------------------------");
		}
	}

	private SymbolStrategySYM strategy;

	SymbolStrategyEnum(SymbolStrategySYM strategy) {
		this.strategy = strategy;
	}

	public SymbolStrategySYM getStrategy() {
		return strategy;
	}
}
