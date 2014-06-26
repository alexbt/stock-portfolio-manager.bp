package com.proserus.stocks.bp.strategies.fw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.proserus.stocks.bp.strategies.basics.Commission;
import com.proserus.stocks.bp.strategies.basics.Dividend;
import com.proserus.stocks.bp.strategies.basics.MarketValue;
import com.proserus.stocks.bp.strategies.basics.QuantityBought;
import com.proserus.stocks.bp.strategies.basics.QuantityRemaining;
import com.proserus.stocks.bp.strategies.basics.QuantitySold;
import com.proserus.stocks.bp.strategies.basics.SumSellingPrices;
import com.proserus.stocks.bp.strategies.basics.TotalCost;
import com.proserus.stocks.bp.strategies.dates.EndOfPeriod;
import com.proserus.stocks.bp.strategies.dates.StartOfPeriod;

public enum BasicStrategiesEnum {
	QUANTITY_REMAINING(new QuantityRemaining()), //
	QUANTITY_SOLD(new QuantitySold()), //
	QUANTITY_BUY(new QuantityBought()), //
	DIVIDEND(new Dividend()), //
	COMMISSION(new Commission()), //
	TOTAL_COST(new TotalCost()), //
	SUM_PRICE_SOLD(new SumSellingPrices()), //
	START_OF_PERIOD(new StartOfPeriod()), //
	END_OF_PERIOD(new EndOfPeriod()), //
	MARKET_VALUE(new MarketValue()); //

	protected static Logger calculsLog = LoggerFactory.getLogger("calculs." + BasicStrategiesEnum.class.getName());
	static {
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("--------------------------------------");
			calculsLog.info("--------------------------------------");
			for (BasicStrategiesEnum en : values()) {
				calculsLog.info("SymbolStrategies: " + en);
			}
			calculsLog.info("--------------------------------------");
		}
	}

	private BasicStrategy<?> strategy;

	BasicStrategiesEnum(BasicStrategy<?> strategy) {
		this.strategy = strategy;
	}

	public BasicStrategy<?> getStrategy() {
		return strategy;
	}
}
