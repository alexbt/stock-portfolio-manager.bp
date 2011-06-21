package com.proserus.stocks.bp.strategies.symbols;

import java.math.BigDecimal;

import com.proserus.stocks.bp.DateUtil;
import com.proserus.stocks.bp.FilterBp;
import com.proserus.stocks.model.analysis.Analysis;
import com.proserus.stocks.model.symbols.HistoricalPrice;
import com.proserus.stocks.model.transactions.Transaction;
import com.proserus.stocks.model.transactions.TransactionType;

public class MarketValueSYM extends AbstractStrategyCumulative {

	@Override
	public BigDecimal  getTransactionValue(Transaction t, FilterBp filter) {
		//TODO Logging
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("Logging not completely implemented for this calcul");
		}
		BigDecimal  value = BigDecimal.ZERO;
		if (!t.getType().equals(TransactionType.DIVIDEND)) {
			HistoricalPrice h = t.getSymbol().getPrice(DateUtil.getYearForUsablePrice(filter));
			value = t.getQuantity();
			if(t.getSymbol().isCustomPriceFirst()){
				value = value.multiply(h.getCustomPrice());
			}else{
				value = value.multiply(h.getPrice());
			}
			if (t.getType().equals(TransactionType.SELL)) {
				value = value.negate();
			}
		}
		return value;
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setMarketValue: " + value);
		analysis.setMarketValue(value);
	}
}
	