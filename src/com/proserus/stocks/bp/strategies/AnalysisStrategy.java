package com.proserus.stocks.bp.strategies;

import java.util.Collection;

import com.proserus.stocks.bp.Filter;
import com.proserus.stocks.bp.strategies.currencies.CurrencyStrategy;
import com.proserus.stocks.bp.strategies.symbols.SymbolStrategySYM;
import com.proserus.stocks.model.analysis.Analysis;
import com.proserus.stocks.model.transactions.Transaction;

public abstract class AnalysisStrategy implements SymbolStrategySYM, CurrencyStrategy {

	@Override
	public void process(Analysis analysis, Collection<Transaction> transactions, Filter filter) {
		process(analysis);

	}

	@Override
	public void process(Analysis analysis, Analysis symbolAnalysis) {
		process(analysis);
	}

	protected abstract void process(Analysis analysis);
}
