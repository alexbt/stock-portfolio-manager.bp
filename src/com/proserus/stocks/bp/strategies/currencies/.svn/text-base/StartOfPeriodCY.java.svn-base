package com.proserus.stocks.bp.strategies.currencies;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.proserus.stocks.model.analysis.Analysis;

public class StartOfPeriodCY implements CurrencyStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + StartOfPeriodCY.class.getName());

	@Override
	public void process(Analysis analysis, Analysis symbolAnalysis) {
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("Logging not completely implemented for this calcul");
		}
		DateTime value = new DateTime();
			if (symbolAnalysis.getStartOfPeriod().isBeforeNow()) {
				value = symbolAnalysis.getStartOfPeriod();
			}
		setAnalysisValue(analysis, value);
	}

	public void setAnalysisValue(Analysis analysis, DateTime value) {
		calculsLog.info("setStartOfPeriod: " + value);
		analysis.setStartOfPeriod(value);
	}

}
