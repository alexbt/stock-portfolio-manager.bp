package com.proserus.stocks.bp.strategies;

import org.apache.log4j.Logger;

import com.proserus.stocks.bp.strategies.impl.AverageCostPerShare;
import com.proserus.stocks.bp.strategies.impl.CapitalGain;
import com.proserus.stocks.bp.strategies.impl.Commission;
import com.proserus.stocks.bp.strategies.impl.CostBasis;
import com.proserus.stocks.bp.strategies.impl.Dividend;
import com.proserus.stocks.bp.strategies.impl.EndOfPeriod;
import com.proserus.stocks.bp.strategies.impl.MarketValue;
import com.proserus.stocks.bp.strategies.impl.QuantityBought;
import com.proserus.stocks.bp.strategies.impl.QuantityRemaining;
import com.proserus.stocks.bp.strategies.impl.QuantitySold;
import com.proserus.stocks.bp.strategies.impl.StartOfPeriod;
import com.proserus.stocks.bp.strategies.impl.SumSellingPrices;
import com.proserus.stocks.bp.strategies.impl.SymbolStrategy;
import com.proserus.stocks.bp.strategies.impl.TotalCost;

public enum PerfOverviewStrategyEnum {

	QUANTITY_REMAINING(new QuantityRemaining()),
	QUANTITY_SOLD(new QuantitySold()),
	QUANTITY_BUY(new QuantityBought()),
	DIVIDEND(new Dividend()),
	TOTAL_COST(new TotalCost()),
	COMMISSION(new Commission()),
	AVERAGE_COST_PER_SHARE(new AverageCostPerShare()),
	COST_BASIS(new CostBasis()),
	SUM_PRICE_SOLD(new SumSellingPrices()),
	START_OF_PERIOD(new StartOfPeriod()),
	END_OF_PERIOD(new EndOfPeriod()),
	MARKET_VALUE(new MarketValue()),
	CAPITAL_GAIN(new CapitalGain());

	protected static Logger calculsLog = Logger.getLogger("calculs." + PerfOverviewStrategyEnum.class.getName());
	static {
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("--------------------------------------");
			calculsLog.info("--------------------------------------");
			for(PerfOverviewStrategyEnum en: values()){
				calculsLog.info("SymbolStrategies: " + en.name());
			}
			calculsLog.info("--------------------------------------");
		}
	}

	private SymbolStrategy strategy;

	PerfOverviewStrategyEnum(SymbolStrategy strategy) {
		this.strategy = strategy;
	}

	public SymbolStrategy getStrategy() {
		return strategy;
	}
}
