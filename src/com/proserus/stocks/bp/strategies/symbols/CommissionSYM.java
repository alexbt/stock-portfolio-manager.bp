package com.proserus.stocks.bp.strategies.symbols;

import java.math.BigDecimal;

import com.proserus.stocks.bp.Filter;
import com.proserus.stocks.model.analysis.Analysis;
import com.proserus.stocks.model.transactions.Transaction;

public class CommissionSYM extends AbstractStrategyCumulative {

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
