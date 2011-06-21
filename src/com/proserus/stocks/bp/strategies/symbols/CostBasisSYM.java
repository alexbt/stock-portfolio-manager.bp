package com.proserus.stocks.bp.strategies.symbols;

import java.math.BigDecimal;

import org.jfree.data.time.Year;
import org.joda.time.DateTime;

import com.proserus.stocks.bp.DateUtil;
import com.proserus.stocks.bp.FilterBp;
import com.proserus.stocks.model.analysis.Analysis;
import com.proserus.stocks.model.symbols.HistoricalPrice;
import com.proserus.stocks.model.transactions.Transaction;
import com.proserus.stocks.model.transactions.TransactionType;

public class CostBasisSYM extends AbstractStrategyCumulative {

	@Override
	public BigDecimal getTransactionValue(Transaction t, FilterBp filter) {
		//TODO logging
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("Logging not completely implemented for this calcul");
		}
		
		BigDecimal value = BigDecimal.ZERO;

		if (t.getType().equals(TransactionType.BUY)) {

			value = t.getQuantity();
			if (filter.isDateFiltered() && filter.isFilteredYearAfter(new DateTime(t.getDate()))) {
				HistoricalPrice h = t.getSymbol().getPrice((Year)DateUtil.getYearForUsablePrice(filter).previous());
				if (t.getSymbol().isCustomPriceFirst()) {
					value = value.multiply(h.getCustomPrice());
				} else {
					value = value.multiply(h.getPrice());
				}
			} else {
				value = value.multiply(t.getPrice());
			}
		}
		return value;
	}

	@Override
	public void setAnalysisValue(Analysis analysis, BigDecimal value) {
		calculsLog.info("setCost: " + value);
		analysis.setCost(value);
	}
}
