package com.proserus.stocks.bp.strategies;

import java.math.BigDecimal;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.symbols.HistoricalPrice;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bo.transactions.TransactionType;
import com.proserus.stocks.bp.model.Filter;
import com.proserus.stocks.bp.utils.DateUtils;
public class TotalCost extends AbstractStrategyCumulative {

	@Override
	public BigDecimal getTransactionValue(Transaction t, Filter filter) {
		//TODO logging
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("Logging not implemented");
		}
		
		BigDecimal value = BigDecimal.ZERO;

		if (t.getType().equals(TransactionType.BUY)) {
			value = t.getQuantity();
			if (filter.isDateFiltered() && filter.isTransactionBeforeFilteredYear(t.getCalendar())) {
				HistoricalPrice h = t.getSymbol().getPrice(DateUtils.getFilteredPreviousYear(filter));
				if (t.getSymbol().isCustomPriceFirst()) {
					value = value.multiply(h.getCustomPrice());
				} else {
					value = value.multiply(h.getPrice());
				}
			} else {
				value = value.multiply(t.getPrice());
			}
			
			value = value.add(t.getCommission());
		}
		
		return value;
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setCost: " + value);
		analysis.setTotalCost(value);
	}
}
