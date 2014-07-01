package com.proserus.stocks.bp.strategies.basics;

import java.math.BigDecimal;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bo.transactions.TransactionType;
import com.proserus.stocks.bp.model.Filter;
import com.proserus.stocks.bp.strategies.fw.BasicDecimalStrategy;

public class QuantityRemaining extends BasicDecimalStrategy {

	@Override
	public BigDecimal getTransactionValue(Transaction t, Filter filter) {
		// TODO Logging
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("{} - Symbol: {}", new Object[] { this.getClass().getName(), t.getSymbol().getTicker() });
			calculsLog.info("Transaction Type: {}", new Object[] { t.getType() });
			calculsLog.info("getQuantity: {}", new Object[] { t.getQuantity() });
		}
		BigDecimal value = BigDecimal.ZERO;
		if (!TransactionType.DIVIDEND.equals(t.getType())) {
			value = t.getQuantity();
			if (t.getType().equals(TransactionType.SELL)) {
				value = value.negate();
			}
		}
		return value;
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setQuantity: {}", new Object[] { value });
		analysis.setQuantity(value);
	}
}
