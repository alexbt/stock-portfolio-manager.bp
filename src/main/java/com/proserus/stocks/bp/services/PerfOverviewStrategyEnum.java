package com.proserus.stocks.bp.services;

import org.apache.log4j.Logger;

import com.proserus.stocks.bp.strategies.AnnualizedReturn;
import com.proserus.stocks.bp.strategies.AverageCostPerShare;
import com.proserus.stocks.bp.strategies.CapitalGain;
import com.proserus.stocks.bp.strategies.Commission;
import com.proserus.stocks.bp.strategies.CostBasis;
import com.proserus.stocks.bp.strategies.Dividend;
import com.proserus.stocks.bp.strategies.DividendYield;
import com.proserus.stocks.bp.strategies.EndOfPeriod;
import com.proserus.stocks.bp.strategies.MarketGrowth;
import com.proserus.stocks.bp.strategies.MarketValue;
import com.proserus.stocks.bp.strategies.NumberOfYears;
import com.proserus.stocks.bp.strategies.OverallReturn;
import com.proserus.stocks.bp.strategies.QuantityBought;
import com.proserus.stocks.bp.strategies.QuantityRemaining;
import com.proserus.stocks.bp.strategies.QuantitySold;
import com.proserus.stocks.bp.strategies.StartOfPeriod;
import com.proserus.stocks.bp.strategies.SumSellingPrices;
import com.proserus.stocks.bp.strategies.SymbolStrategy;
import com.proserus.stocks.bp.strategies.TotalCost;

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
	CAPITAL_GAIN(new CapitalGain()),
	NUMBER_OF_YEARS(new NumberOfYears()),
	MARKET_GROWTH(new MarketGrowth()),
	DIVIDEND_YIELD(new DividendYield()),
	OVERALL_RETURN(new OverallReturn()),
	ANNUALIZED_RETURN(new AnnualizedReturn());

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
