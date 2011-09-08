package com.proserus.stocks.bp.businesslogic;

import java.util.ArrayList;
import java.util.Collection;

import com.google.inject.Inject;
import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.analysis.AnalysisImpl;
import com.proserus.stocks.bo.analysis.CurrencyAnalysis;
import com.proserus.stocks.bo.analysis.SymbolAnalysis;
import com.proserus.stocks.bo.symbols.CurrencyEnum;
import com.proserus.stocks.bo.symbols.Symbol;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.Filter;
import com.proserus.stocks.bp.dao.SymbolsDao;
import com.proserus.stocks.bp.dao.TransactionsDao;
import com.proserus.stocks.bp.strategies.PerfOverviewStrategyEnum;
import com.proserus.stocks.bp.strategies.StrategyEnum;

public class AnalysisBp {
	@Inject
	private TransactionsDao transactionsDao;
	
	@Inject
	private SymbolsDao symbolsDao;

	private Collection<Analysis> symbolAnalysis;
	private Collection<Analysis> currencyAnalysis;

	public AnalysisBp() {
		//recalculate(new FilterBp());
	}

	public void recalculate(Filter filter) {
		assert filter != null;
		
		calculatePerSymbol(filter);
		calculatePerCurrency(filter);
//		calculatePerLabels(filter);
	}

	private void calculatePerSymbol(Filter filter) {
		assert filter != null;
		
		symbolAnalysis = new ArrayList<Analysis>();
		for (Symbol symbol : symbolsDao.get()) {
			Collection<Transaction> trans = transactionsDao.getTransactionsBySymbol(symbol, filter, false);
			if (trans.size() > 0) {
				Analysis analysis = createAnalysis(trans, filter);
				analysis.setSymbol(symbol);
				if(analysis.getQuantity().floatValue() != 0F){
					symbolAnalysis.add(analysis);
				}
			}
		}
	}
	
	private Analysis createAnalysis(Collection<Transaction> trans, Filter filter) {
		assert trans != null;
		assert filter != null;
		
		Analysis analysis = new AnalysisImpl();
		for (PerfOverviewStrategyEnum strategy : PerfOverviewStrategyEnum.values()) {
			strategy.getStrategy().process(analysis, trans, filter);
		}
		for (StrategyEnum strategy : StrategyEnum.values()) {
			strategy.getStrategy().process(analysis, trans, filter);
		}
		return analysis;
	}

	private void calculatePerCurrency(Filter filter) {
		assert filter != null;

		currencyAnalysis = new ArrayList<Analysis>();
		
		for(CurrencyEnum currency: CurrencyEnum.values()){
			Collection<Transaction> trans = transactionsDao.getTransactionsByCurrency(currency, filter, false);
			if(!trans.isEmpty()){
				Analysis analysis = createAnalysis(trans, filter);
				analysis.setCurrency(currency);
				if(analysis.getQuantity().floatValue() != 0F){
					currencyAnalysis.add(analysis);
				} 
			}
		}
	}

	public Collection<? extends SymbolAnalysis> getSymbolAnalysis() {
		return symbolAnalysis;
	}

	public Collection<? extends CurrencyAnalysis> getCurrencyAnalysis() {
		return currencyAnalysis;
	}
	
//	private void calculatePerLabels(Filter filter) {
//		assert filter != null;
//		
//		if(filter.isLabelsFiltered()){
//			Collection<Transaction> transactions = transactionsDao.getTransactions(filter, false);
//			for(Label label: filter.getLabels()){
//				Collection<Transaction> trans = transactionsDao.getTransactionsByLabel(label);
//				trans = CollectionUtils.union(trans, transactions);
//			}
//		}
//	}

}