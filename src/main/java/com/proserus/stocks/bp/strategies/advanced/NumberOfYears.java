package com.proserus.stocks.bp.strategies.advanced;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.analysis.ViewableAnalysis;
import com.proserus.stocks.bp.strategies.fw.AdvancedStrategy;
import com.proserus.stocks.bp.utils.DateUtils;

public class NumberOfYears extends AdvancedStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + NumberOfYears.class.getName());

	@Override
	public BigDecimal process(ViewableAnalysis analysis) {

		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("setNumberOfYears = Days.daysBetween(getStartOfPeriod,getEndOfPeriod) / 365");
			calculsLog.info("getStartOfPeriod: " + analysis.getStartOfPeriod());
			calculsLog.info("getEndOfPeriod: " + analysis.getEndOfPeriod());
		}

		double years = DateUtils.getYearsBetween(analysis.getStartOfPeriod(), analysis.getEndOfPeriod());
		calculsLog.info("Calculated NumberOfYears successfully!");

		calculsLog.info("setNumberOfYears = " + years + " years");
		return new BigDecimal(years);
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		analysis.setNumberOfYears(value);
	}
}
