package com.proserus.stocks.bp.strategies;

import java.math.BigDecimal;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bo.transactions.TransactionType;
import com.proserus.stocks.bp.model.Filter;
public class QuantityRemaining extends AbstractStrategyCumulative {

	@Override
	public BigDecimal  getTransactionValue(Transaction t, Filter filter) {
		//TODO Logging
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info(this.getClass().getName() + " - Symbol: " + t.getSymbol().getTicker());
			calculsLog.info("Transaction Type: " + t.getType());
			calculsLog.info("getQuantity: " + t.getQuantity());
		}
		BigDecimal  value = BigDecimal .ZERO;
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
		calculsLog.info("setQuantity: " + value);
		analysis.setQuantity(value);
	}
}
