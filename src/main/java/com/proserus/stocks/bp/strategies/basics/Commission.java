package com.proserus.stocks.bp.strategies.basics;

import java.math.BigDecimal;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.model.Filter;
import com.proserus.stocks.bp.strategies.fw.BasicDecimalStrategy;

public class Commission extends BasicDecimalStrategy {

	@Override
	public BigDecimal getTransactionValue(Transaction t, Filter filter) {
		calculsLog.info("Commission: {}", new Object[] { t.getCommission() });
		return t.getCommission();
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setCommission: {}", new Object[] { value });
		analysis.setCommission(value);
	}

}
