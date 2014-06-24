package com.proserus.stocks.bp.strategies;

import java.util.Collection;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.model.Filter;

public interface SymbolStrategy {
	void process(Analysis analysis, Collection<Transaction> transactions, Filter filter);
}
