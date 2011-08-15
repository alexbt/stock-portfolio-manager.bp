package com.proserus.stocks.bp.strategies;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.proserus.stocks.model.analysis.Analysis;

public class AnnualizedReturn extends AnalysisStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + AnnualizedReturn.class.getName());

	private static final int COMPOUNDING_PERIOD = 12;
	private static final int CONSTANT_ONE = 1;

	@Override
	protected void process(Analysis analysis) {
		// BigDecimal value = analysis.getDividendYield().add(analysis.getMarketGrowth().add(analysis.getCapitalGainPercent()));
		// analysis.setOverallReturn(value);
		if(analysis.getOverallReturn() == null){
			analysis.setAnnualizedReturn(null);
			return;
		}
		double value = analysis.getOverallReturn().doubleValue();

		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("AnnualizedReturn = (overallReturn/100+1)^(1/(CompoundingPeriod X numberOfYears)) - 1");
			calculsLog.info("getOverallReturn: " + analysis.getOverallReturn());
			calculsLog.info("CompoundingPeriod: " + COMPOUNDING_PERIOD);
			calculsLog.info("getNumberOfYears: " + analysis.getNumberOfYears());
		}

		if (!analysis.getNumberOfYears().equals(BigDecimal.ZERO)) {
			value /= 100;
			// value /= analysis.getNumberOfYears();
			value += CONSTANT_ONE;
			value = Math.pow(value, (double) 1 / (double) (COMPOUNDING_PERIOD * analysis.getNumberOfYears().doubleValue()));
			value -= CONSTANT_ONE;

			// TODO what if we are in the current year. should we calculate as if a Full year was passed ??
			// TODO and what about the first year ?
			value *= COMPOUNDING_PERIOD;
			value *= 100;
			calculsLog.info("Calculated annualized return successfully!");
		} else {
			calculsLog.info("Failed to calculate  Annualized return: number of year is 0");
		}

		calculsLog.info("setAnnualizedReturn = " + value);
		if(Double.isInfinite(value) || Double.isNaN(value)){
			analysis.setAnnualizedReturn(BigDecimal.ZERO);
		}else{
			analysis.setAnnualizedReturn(new BigDecimal(value));
		}

		// From 3 years to Yearly Compound: =(RateOnThreeYears+1)^(OneYear/ThreeYears)-1
		// From year to Monthly compound : =(RateOneYear+1)^(OneYear/TwelveMonths)-1
		// From 3 years to Monthly compound: =(RateOneYear+1)^(OneYear/(TwelveMonths X ThreeYears))-1

		//
	}
}
