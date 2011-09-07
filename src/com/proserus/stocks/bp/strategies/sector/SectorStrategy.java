package com.proserus.stocks.bp.strategies.sector;

import java.util.Collection;

import com.proserus.stocks.bp.Filter;
import com.proserus.stocks.model.analysis.SectorAnalysis;
import com.proserus.stocks.model.transactions.Transaction;

public interface SectorStrategy {
	void process(SectorAnalysis analysis, Collection<Transaction> transactions, Filter filter);

}
