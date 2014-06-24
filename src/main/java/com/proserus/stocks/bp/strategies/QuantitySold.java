package com.proserus.stocks.bp.strategies;

import java.math.BigDecimal;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bo.transactions.TransactionType;
import com.proserus.stocks.bp.model.Filter;
public class QuantitySold extends AbstractStrategyCumulative {

	@Override
	public BigDecimal getTransactionValue(Transaction t, Filter filter) {
		//TODO Logging
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			//TODO logging
			calculsLog.info("Logging not implemented");
		}
		BigDecimal value = BigDecimal.ZERO;

		if (t.getType().equals(TransactionType.SELL)) {
			value = t.getQuantity();
		}
		return value;
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setQuantitySold: " + value);
		analysis.setQuantitySold(value);
	}
}
