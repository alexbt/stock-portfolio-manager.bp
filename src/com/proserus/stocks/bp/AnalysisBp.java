package com.proserus.stocks.bp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.proserus.stocks.bp.strategies.StrategyEnum;
import com.proserus.stocks.bp.strategies.currencies.CurrencyStrategyEnum;
import com.proserus.stocks.bp.strategies.symbols.SymbolStrategyEnum;
import com.proserus.stocks.model.analysis.Analysis;
import com.proserus.stocks.model.analysis.AnalysisImpl;
import com.proserus.stocks.model.analysis.CurrencyAnalysis;
import com.proserus.stocks.model.analysis.SymbolAnalysis;
import com.proserus.stocks.model.common.ObservableModel;
import com.proserus.stocks.model.symbols.CurrencyEnum;
import com.proserus.stocks.model.symbols.Symbol;
import com.proserus.stocks.model.transactions.Transaction;

public class AnalysisBp extends ObservableModel {
	private TransactionsBp transactionsBp = TransactionsBp.getInstance();

	private SymbolsBp symbolsBp = SymbolsBp.getInstance();

	private static AnalysisBp singleton = new AnalysisBp();
	Collection<Analysis> symbolAnalysis;
	Collection<Analysis> currencyAnalysis;

	private AnalysisBp() {
		recalculate(new FilterBp());
	}

	public static AnalysisBp getInstance() {
		return singleton;
	}

	public void recalculate(FilterBp filter) {
		calculatePerSymbol(filter);
		calculatePerCurrency();
		setChanged();
		notifyObservers();
	}

	private void calculatePerSymbol(FilterBp filter) {
		symbolAnalysis = new ArrayList<Analysis>();
		for (Symbol symbol : symbolsBp.get()) {
			Collection<Transaction> trans = transactionsBp.getTransactionsBySymbol(symbol, filter, false);
			if (trans.size() > 0) {
				Analysis analysis = createAnalysis(trans, filter);
				analysis.setSymbol(symbol);
				if(analysis.getQuantity().floatValue()!=0F){
					symbolAnalysis.add(analysis);
				}
			}
		}
	}

	private Analysis createAnalysis(Collection<Transaction> trans, FilterBp filter) {
		Analysis analysis = new AnalysisImpl();
		for (SymbolStrategyEnum strategy : SymbolStrategyEnum.values()) {
			strategy.getStrategy().process(analysis, trans, filter);
		}
		for (StrategyEnum strategy : StrategyEnum.values()) {
			strategy.getStrategy().process(analysis, trans, filter);
		}
		return analysis;
	}

	private void calculatePerCurrency() {
		//TODO choose better variable name
		//TODO cut in 2 methods..
		currencyAnalysis = new ArrayList<Analysis>();
		Map<CurrencyEnum, Analysis> byCurrencies = new HashMap<CurrencyEnum, Analysis>();
		for (SymbolAnalysis symbolAnalysis : getSymbolAnalysis()) {
			
			CurrencyEnum currency = symbolAnalysis.getSymbol().getCurrency();
			Analysis currencyAnalysis = byCurrencies.get(currency);
			if (currencyAnalysis == null) {
				currencyAnalysis = new AnalysisImpl();
				currencyAnalysis.setCurrency(currency);

				byCurrencies.put(currency, currencyAnalysis);
				this.currencyAnalysis.add(currencyAnalysis);
			}
			for (CurrencyStrategyEnum strategy : CurrencyStrategyEnum.values()) {
				strategy.getStrategy().process(currencyAnalysis, (Analysis)symbolAnalysis);
			}
		}
		
		for(Analysis analysis: byCurrencies.values()){
			for (StrategyEnum strategy : StrategyEnum.values()) {
				strategy.getStrategy().process(analysis, null);
			}
		}
	}

	public Collection<? extends SymbolAnalysis> getSymbolAnalysis() {
		return symbolAnalysis;
	}

	public Collection<? extends CurrencyAnalysis> getCurrencyAnalysis() {
		return currencyAnalysis;
	}
}