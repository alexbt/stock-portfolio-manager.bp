package com.proserus.stocks.bp.strategies;

import java.math.BigDecimal;

import org.jfree.data.time.Year;
import org.joda.time.DateTime;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.symbols.HistoricalPrice;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bo.transactions.TransactionType;
import com.proserus.stocks.bp.model.Filter;
import com.proserus.stocks.bp.utils.DateUtil;
public class TotalCost extends AbstractStrategyCumulative {

	@Override
	public BigDecimal getTransactionValue(Transaction t, Filter filter) {
		//TODO logging
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("Logging not completely implemented for this calcul");
		}
		
		BigDecimal value = BigDecimal.ZERO;

		if (t.getType().equals(TransactionType.BUY)) {
			value = t.getQuantity();
			if (filter.isDateFiltered() && filter.isFilteredYearAfter(new DateTime(t.getDate()))) {
				//FIXME Year JFree
				HistoricalPrice h = t.getSymbol().getPrice((Year)DateUtil.getYearForUsablePrice(filter).previous());
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
