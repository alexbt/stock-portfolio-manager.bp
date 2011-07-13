package com.proserus.stocks.bp.strategies.currencies;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.proserus.stocks.model.analysis.Analysis;

public class EndOfPeriodCY implements CurrencyStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + EndOfPeriodCY.class.getName());

	@Override
	public void process(Analysis analysis, Analysis symbolAnalysis) {
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("Logging not completely implemented for this calcul");
		}
		DateTime value = analysis.getStartOfPeriod();
		if (symbolAnalysis.getEndOfPeriod().isAfter(value)) {
			value = symbolAnalysis.getEndOfPeriod();
		}
		setAnalysisValue(analysis, value);
	}

	public void setAnalysisValue(Analysis analysis, DateTime value) {
		calculsLog.info("setEndOfPeriod: " + value);
		analysis.setEndOfPeriod(value);
	}

}
