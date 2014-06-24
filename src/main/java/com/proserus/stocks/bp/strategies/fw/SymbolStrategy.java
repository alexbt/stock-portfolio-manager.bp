package com.proserus.stocks.bp.strategies.fw;

import java.util.Collection;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.model.Filter;

public interface SymbolStrategy<T> {
	void process(Analysis analysis, Collection<Transaction> transactions, Filter filter);

	public abstract void setAnalysisValue(Analysis analysis, T value);

	public abstract T getDefaultAnalysisValue();
}
