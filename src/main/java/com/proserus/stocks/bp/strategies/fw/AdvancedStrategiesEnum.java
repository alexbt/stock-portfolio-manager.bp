package com.proserus.stocks.bp.strategies.fw;

import org.apache.log4j.Logger;

import com.proserus.stocks.bp.strategies.advanced.AnnualizedReturn;
import com.proserus.stocks.bp.strategies.advanced.AverageCostPerShare;
import com.proserus.stocks.bp.strategies.advanced.CapitalGain;
import com.proserus.stocks.bp.strategies.advanced.CostBasis;
import com.proserus.stocks.bp.strategies.advanced.DividendYield;
import com.proserus.stocks.bp.strategies.advanced.MarketGrowth;
import com.proserus.stocks.bp.strategies.advanced.NumberOfYears;
import com.proserus.stocks.bp.strategies.advanced.OverallReturn;

public enum AdvancedStrategiesEnum {
	AVERAGE_COST_PER_SHARE(new AverageCostPerShare()), //
	COST_BASIS(new CostBasis()), //
	CAPITAL_GAIN(new CapitalGain()), //
	NUMBER_OF_YEARS(new NumberOfYears()), //
	MARKET_GROWTH(new MarketGrowth()), //
	DIVIDEND_YIELD(new DividendYield()), //
	OVERALL_RETURN(new OverallReturn()), //
	ANNUALIZED_RETURN(new AnnualizedReturn());

	protected static Logger calculsLog = Logger.getLogger("calculs." + AdvancedStrategiesEnum.class.getName());
	static {
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("--------------------------------------");
			calculsLog.info("--------------------------------------");
			for (AdvancedStrategiesEnum en : values()) {
				calculsLog.info("SymbolStrategies: " + en);
			}
			calculsLog.info("--------------------------------------");
		}
	}

	private AdvancedStrategy strategy;

	AdvancedStrategiesEnum(AdvancedStrategy strategy) {
		this.strategy = strategy;
	}

	public AdvancedStrategy getStrategy() {
		return strategy;
	}
}
