package com.proserus.stocks.bp.strategies.basics;

import java.math.BigDecimal;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.symbols.HistoricalPrice;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bo.transactions.TransactionType;
import com.proserus.stocks.bp.model.Filter;
import com.proserus.stocks.bp.strategies.fw.BasicDecimalStrategy;
import com.proserus.stocks.bp.utils.DateUtils;

public class MarketValue extends BasicDecimalStrategy {

	@Override
	public BigDecimal getTransactionValue(Transaction t, Filter filter) {
		// TODO Logging
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info(this.getClass().getName() + " - Symbol: " + t.getSymbol().getTicker());
			calculsLog.info("getQuantity: " + t.getQuantity());
		}
		BigDecimal value = BigDecimal.ZERO;
		if (!t.getType().equals(TransactionType.DIVIDEND)) {
			HistoricalPrice h = t.getSymbol().getPrice(DateUtils.getFilteredYear(filter));
			if (calculsLog.isInfoEnabled()) {
				calculsLog.info("getCustomPrice: " + h.getCustomPrice());
				calculsLog.info("getPrice: " + h.getPrice());
			}
			value = t.getQuantity();
			if (t.getSymbol().isCustomPriceFirst()) {
				calculsLog.info("=> Using custom price");
				value = value.multiply(h.getCustomPrice());
			} else {
				value = value.multiply(h.getPrice());
				calculsLog.info("=> Using online price (not custom)");
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
