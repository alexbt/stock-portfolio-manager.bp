package com.proserus.stocks.bp.strategies;

import com.proserus.stocks.bp.strategies.impl.AnnualizedReturn;
import com.proserus.stocks.bp.strategies.impl.DividendYield;
import com.proserus.stocks.bp.strategies.impl.MarketGrowth;
import com.proserus.stocks.bp.strategies.impl.NumberOfYears;
import com.proserus.stocks.bp.strategies.impl.OverallReturn;

public enum StrategyEnum {
	NUMBER_OF_YEARS(new NumberOfYears()),
	MARKET_GROWTH(new MarketGrowth()),
	DIVIDEND_YIELD(new DividendYield()),
	OVERALL_RETURN(new OverallReturn()),
	ANNUALIZED_RETURN(new AnnualizedReturn());

	private AnalysisStrategy strategy;

	StrategyEnum(AnalysisStrategy strategy) {
		this.strategy = strategy;
	}

	public AnalysisStrategy getStrategy() {
		return strategy;
	}
}
