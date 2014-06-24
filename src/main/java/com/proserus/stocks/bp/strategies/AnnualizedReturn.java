package com.proserus.stocks.bp.strategies;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.utils.BigDecimalUtils;

public class AnnualizedReturn extends AbstractAnalysisStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + AnnualizedReturn.class.getName());

	private static final BigDecimal COMPOUNDING_PERIOD = new BigDecimal(12);

	@Override
	protected void process(Analysis analysis) {
		// BigDecimal value =
		// analysis.getDividendYield().add(analysis.getMarketGrowth().add(analysis.getCapitalGainPercent()));
		// analysis.setOverallReturn(value);
		if (analysis.getOverallReturn() == null) {
			analysis.setAnnualizedReturn(null);
			return;
		}
		BigDecimal value = analysis.getOverallReturn();

		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("AnnualizedReturn = (overallReturn/100+1)^(1/(CompoundingPeriod X numberOfYears)) - 1");
			calculsLog.info("getOverallReturn: " + analysis.getOverallReturn());
			calculsLog.info("CompoundingPeriod: " + COMPOUNDING_PERIOD);
			calculsLog.info("getNumberOfYears: " + analysis.getNumberOfYears());
		}

		if (!analysis.getNumberOfYears().equals(BigDecimal.ZERO)) {
			value = value.divide(BigDecimalUtils.HUNDRED).add(BigDecimal.ONE);
			BigDecimal divisor = analysis.getNumberOfYears().multiply(COMPOUNDING_PERIOD);
			Double dd = BigDecimal.ONE.divide(divisor, BigDecimal.ROUND_UP).doubleValue();
			Double result = Math.pow(value.doubleValue(), dd);

			if (result.isInfinite() || result.isNaN()) {
				value = BigDecimal.ZERO;
			} else {
				value = new BigDecimal(result).subtract(BigDecimal.ONE);
				value = value.multiply(COMPOUNDING_PERIOD).multiply(BigDecimalUtils.HUNDRED);
			}

			// TODO what if we are in the current year. should we calculate as
			// if a Full year has passed ??
			// TODO and what about the first year ?

			calculsLog.info("Calculated annualized return successfully!");
		} else {
			calculsLog.info("Failed to calculate  Annualized return: number of year is 0");
		}

		calculsLog.info("setAnnualizedReturn = " + value);
		if (Double.isInfinite(value.doubleValue()) || Double.isNaN(value.doubleValue())) {
			setValue(analysis, BigDecimal.ZERO);
		} else {
			setValue(analysis, value);
		}

		// From 3 years to Yearly Compound:
		// =(RateOnThreeYears+1)^(OneYear/ThreeYears)-1
		// From year to Monthly compound :
		// =(RateOneYear+1)^(OneYear/TwelveMonths)-1
		// From 3 years to Monthly compound:
		// =(RateOneYear+1)^(OneYear/(TwelveMonths X ThreeYears))-1

		//
	}

	@Override
	protected void setValue(Analysis analysis, BigDecimal value) {
		analysis.setAnnualizedReturn(value);
	}
}
