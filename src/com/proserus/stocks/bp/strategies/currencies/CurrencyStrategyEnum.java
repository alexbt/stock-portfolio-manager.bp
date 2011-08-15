package com.proserus.stocks.bp.strategies.currencies;

import org.apache.log4j.Logger;

public enum CurrencyStrategyEnum {
	COST_BASIS(new CostBasisCY()),
	TOTAL_COST(new TotalCostCY()),
	COMMISSION(new CommissionCY()),
	QUANTITY_BUY(new QuantityBoughtCY()),
	QUANTITY(new QuantityCY()),
	QUANTITY_SOLD(new QuantitySoldCY()),
	CAPITAL_GAIN(new CapitalGainCY()),
	DIVIDEND(new DividendStrategyCY()),
	MARKET_VALUE(new MarketValueCY()),
	AVERAGE_PRICE(new AveragePriceCY()),
	SOLD_COST(new SumSellingCY()),
	START_OF_PERIOD(new StartOfPeriodCY()),
	END_OF_PERIOD(new EndOfPeriodCY());

	protected static Logger calculsLog = Logger.getLogger("calculs." + CurrencyStrategyEnum.class.getName());
	static {
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("--------------------------------------");
			calculsLog.info("--------------------------------------");
			for (CurrencyStrategyEnum en : values()) {
				calculsLog.info("CurrencyStrategies: " + en.name());
			}
			calculsLog.info("--------------------------------------");
		}
	}

	private CurrencyStrategy strategy;

	private CurrencyStrategyEnum(CurrencyStrategy strategy) {
		this.strategy = strategy;
	}

	public CurrencyStrategy getStrategy() {
		return strategy;
	}
}
