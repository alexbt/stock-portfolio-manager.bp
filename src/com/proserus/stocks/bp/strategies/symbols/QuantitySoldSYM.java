package com.proserus.stocks.bp.strategies.symbols;

import java.math.BigDecimal;

import com.proserus.stocks.bp.Filter;
import com.proserus.stocks.model.analysis.Analysis;
import com.proserus.stocks.model.transactions.Transaction;
import com.proserus.stocks.model.transactions.TransactionType;

public class QuantitySoldSYM extends AbstractStrategyCumulative {

	@Override
	public BigDecimal getTransactionValue(Transaction t, Filter filter) {
		//TODO Logging
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			//TODO logging
			calculsLog.info("Logging not completely implemented for this calcul");
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
