package com.proserus.stocks.bp.strategies.impl;

import java.math.BigDecimal;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.Filter;

public class Commission extends AbstractStrategyCumulative {

	@Override
	public BigDecimal getTransactionValue(Transaction t, Filter filter) {
		calculsLog.info("Commission: " + t.getCommission());
		return t.getCommission();
	}

	@Override
    public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setCommission: " + value);
		analysis.setCommission(value);
    }

}
