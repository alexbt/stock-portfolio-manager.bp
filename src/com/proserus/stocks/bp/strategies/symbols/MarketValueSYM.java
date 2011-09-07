package com.proserus.stocks.bp.strategies.symbols;

import java.math.BigDecimal;

import com.proserus.stocks.bp.DateUtil;
import com.proserus.stocks.bp.Filter;
import com.proserus.stocks.model.analysis.Analysis;
import com.proserus.stocks.model.symbols.HistoricalPrice;
import com.proserus.stocks.model.transactions.Transaction;
import com.proserus.stocks.model.transactions.TransactionType;

public class MarketValueSYM extends AbstractStrategyCumulative {

	@Override
	public BigDecimal getTransactionValue(Transaction t, Filter filter) {
		//TODO Logging
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info(this.getClass().getName() + " - Symbol: " + t.getSymbol().getTicker());
			calculsLog.info("getQuantity: " + t.getQuantity());
		}
		BigDecimal  value = BigDecimal.ZERO;
		if (!t.getType().equals(TransactionType.DIVIDEND)) {
			HistoricalPrice h = t.getSymbol().getPrice(DateUtil.getYearForUsablePrice(filter));
			if (calculsLog.isInfoEnabled()) {
				calculsLog.info("getCustomPrice: " + h.getCustomPrice());
				calculsLog.info("getPrice: " + h.getPrice());
			}
			value = t.getQuantity();
			if(t.getSymbol().isCustomPriceFirst()){
				calculsLog.info("=> Using custom price");
				value = value.multiply(h.getCustomPrice());
			}else{
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
	