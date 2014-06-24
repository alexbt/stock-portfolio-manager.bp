package com.proserus.stocks.bp.strategies;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bp.utils.DateUtils;

public class NumberOfYears extends AbstractAnalysisStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + NumberOfYears.class.getName());

	@Override
	public void process(Analysis analysis) {

		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("setNumberOfYears = Days.daysBetween(getStartOfPeriod,getEndOfPeriod) / 365");
			calculsLog.info("getStartOfPeriod: " + analysis.getStartOfPeriod());
			calculsLog.info("getEndOfPeriod: " + analysis.getEndOfPeriod());
		}

		double years = DateUtils.getYearsBetween(analysis.getStartOfPeriod(), analysis.getEndOfPeriod());
		calculsLog.info("Calculated NumberOfYears successfully!");

		calculsLog.info("setNumberOfYears = " + years + " years");
		setValue(analysis, new BigDecimal(years));
	}

	@Override
	protected void setValue(Analysis analysis, BigDecimal value) {
		analysis.setNumberOfYears(value);
	}
}
