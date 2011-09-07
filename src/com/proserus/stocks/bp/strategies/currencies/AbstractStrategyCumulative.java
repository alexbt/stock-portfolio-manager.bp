package com.proserus.stocks.bp.strategies.currencies;

import java.math.BigDecimal;

import org.apache.log4j.Logger;

import com.proserus.stocks.model.analysis.Analysis;

public abstract class AbstractStrategyCumulative implements CurrencyStrategy {
	protected static Logger calculsLog = Logger.getLogger("calculs." + SumSellingCY.class.getName());
	
	@Override
	public void process(Analysis analysis, Analysis symbolAnalysis) {
		if(calculsLog.isInfoEnabled()){
			calculsLog.info("--------------------------------------");
		}
		
		BigDecimal value = getAnalysisValue(analysis);
		if(value==null){
			value = BigDecimal.ZERO;
		}
		value = value.add(getAnalysisValue(symbolAnalysis));
		setAnalysisValue(analysis, value);
	}

	public abstract BigDecimal getAnalysisValue(Analysis s);

	public abstract void setAnalysisValue(Analysis analysis, BigDecimal value);
	
}
