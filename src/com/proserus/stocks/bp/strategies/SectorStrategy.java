package com.proserus.stocks.bp.strategies;

import java.util.Collection;

import com.proserus.stocks.bo.analysis.SectorAnalysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.Filter;

public interface SectorStrategy {
	void process(SectorAnalysis analysis, Collection<Transaction> transactions, Filter filter);

}
