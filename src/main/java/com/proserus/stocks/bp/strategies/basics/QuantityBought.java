package com.proserus.stocks.bp.strategies.basics;

import java.math.BigDecimal;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bo.transactions.TransactionType;
import com.proserus.stocks.bp.model.Filter;
import com.proserus.stocks.bp.strategies.fw.BasicDecimalStrategy;

public class QuantityBought extends BasicDecimalStrategy {

	@Override
	public BigDecimal getTransactionValue(Transaction t, Filter filter) {
		// TODO Logging
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("Logging not implemented");
		}
		BigDecimal value = BigDecimal.ZERO;

		if (t.getType().equals(TransactionType.BUY)) {
			value = t.getQuantity();
		}
		return value;
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setQuantityBuy: {}", new Object[] { value });
		analysis.setQuantityBuy(value);
	}
}
