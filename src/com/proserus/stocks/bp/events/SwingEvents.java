package com.proserus.stocks.bp.events;

import java.util.Collection;

import com.proserus.stocks.bo.analysis.CurrencyAnalysis;
import com.proserus.stocks.bo.analysis.SymbolAnalysis;
import com.proserus.stocks.bo.symbols.CurrencyEnum;
import com.proserus.stocks.bo.symbols.Symbol;
import com.proserus.stocks.bo.transactions.Label;
import com.proserus.stocks.bo.transactions.Transaction;

public class SwingEvents {

	static public ModelEvent<Collection<Transaction>> TRANSACTION_UPDATED = new ModelEvent<Collection<Transaction>>();
	
	static public ModelEvent<Collection<Symbol>> SYMBOLS_UPDATED = new ModelEvent<Collection<Symbol>>();
	
	static public NoModelEvent SYMBOLS_HISTORICAL_PRICE_UPDATED = new NoModelEvent();
	
	static public NoModelEvent SYMBOLS_CURRENT_PRICE_UPDATED = new NoModelEvent();
	
	static public ModelEvent<Collection<Label>> LABELS_UPDATED = new ModelEvent<Collection<Label>>();
	
	static public ModelEvent<Collection<? extends SymbolAnalysis>> SYMBOL_ANALYSIS_UPDATED = new ModelEvent<Collection<? extends SymbolAnalysis>>();
	
	static public ModelEvent<Collection<? extends CurrencyAnalysis>> CURRENCY_ANALYSIS_UPDATED = new ModelEvent<Collection<? extends CurrencyAnalysis>>();
	
	static public ModelEvent<Transaction> TRANSACTION_SELECTION_CHANGED = new ModelEvent<Transaction>();
	
	static public ModelEvent<Symbol> SYMBOL_SELECTION_CHANGED = new ModelEvent<Symbol>();
	
	static public ModelEvent<CurrencyEnum> CURRENCY_DEFAULT_CHANGED = new ModelEvent<CurrencyEnum>();
	
}
