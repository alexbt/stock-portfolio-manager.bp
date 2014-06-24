package com.proserus.stocks.bp.strategies.basics;

import java.math.BigDecimal;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bo.transactions.TransactionType;
import com.proserus.stocks.bp.model.Filter;
import com.proserus.stocks.bp.strategies.fw.BasicDecimalStrategy;

public class SumSellingPrices extends BasicDecimalStrategy {

	@Override
	public BigDecimal getTransactionValue(Transaction t, Filter filter) {
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			// TODO logging
			calculsLog.info("Logging not implemented");
		}
		BigDecimal value = BigDecimal.ZERO;
		// TODO Logging
		if (t.getType().equals(TransactionType.SELL)) {
			value = t.getPrice().multiply(t.getQuantity()).add(t.getCommission());
		}
		return value;
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setTotalSold: " + value);
		analysis.setTotalSold(value);
	}
}
