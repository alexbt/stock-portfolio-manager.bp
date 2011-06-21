package com.proserus.stocks.bp.strategies.symbols;

import java.math.BigDecimal;

import com.proserus.stocks.bp.FilterBp;
import com.proserus.stocks.model.analysis.Analysis;
import com.proserus.stocks.model.transactions.Transaction;
import com.proserus.stocks.model.transactions.TransactionType;

public class SumSellingPricesSYM extends AbstractStrategyCumulative {

	@Override
	public BigDecimal getTransactionValue(Transaction t, FilterBp filter) {
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			//TODO logging
			calculsLog.info("Logging not completely implemented for this calcul");
		}
		BigDecimal value = BigDecimal.ZERO;
		//TODO Logging
		if (t.getType().equals(TransactionType.SELL)) {
			//TODO Consider the case if  "isUseCustomFirst"
			value = t.getPrice().multiply(t.getQuantity());
		}
		return value;
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setTotalSold: " + value);
		analysis.setTotalSold(value);
	}
}
