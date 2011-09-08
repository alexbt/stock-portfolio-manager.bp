package com.proserus.stocks.bp.strategies.impl;

import java.math.BigDecimal;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bo.transactions.TransactionType;
import com.proserus.stocks.bp.Filter;

public class QuantityBought extends AbstractStrategyCumulative {

	@Override
	public BigDecimal getTransactionValue(Transaction t, Filter filter) {
		// TODO Logging
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("Logging not completely implemented for this calcul");
		}
		BigDecimal value = BigDecimal.ZERO;

//		if (!filter.isFilteredYearAfter(t.getDateTime())) {
			if (t.getType().equals(TransactionType.BUY)) {
				value = t.getQuantity();
			}
//		}
		return value;
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setQuantityBuy: " + value);
		analysis.setQuantityBuy(value);
	}
}
