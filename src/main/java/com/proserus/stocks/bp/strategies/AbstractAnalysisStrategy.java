package com.proserus.stocks.bp.strategies;

import java.util.Collection;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.model.Filter;

public abstract class AbstractAnalysisStrategy implements SymbolStrategy {

	@Override
	public void process(Analysis analysis, Collection<Transaction> transactions, Filter filter) {
		assert analysis != null;
		assert transactions != null;
		assert filter != null;
		
		process(analysis);

	}

	protected abstract void process(Analysis analysis);
}
