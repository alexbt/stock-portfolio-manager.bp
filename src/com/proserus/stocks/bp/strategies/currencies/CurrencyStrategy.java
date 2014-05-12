package com.proserus.stocks.bp.strategies.currencies;

import com.proserus.stocks.model.analysis.Analysis;

public interface CurrencyStrategy {
	void process(Analysis analysis, Collection<Transaction> transactions);
}
