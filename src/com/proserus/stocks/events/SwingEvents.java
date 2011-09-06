package com.proserus.stocks.events;

import java.util.Collection;

import com.proserus.stocks.model.analysis.CurrencyAnalysis;
import com.proserus.stocks.model.analysis.SymbolAnalysis;
import com.proserus.stocks.model.symbols.Symbol;
import com.proserus.stocks.model.transactions.Label;
import com.proserus.stocks.model.transactions.Transaction;

public class SwingEvents {

	static public Event<Collection<Transaction>> TRANSACTION_UPDATED = new Event<Collection<Transaction>>();
	
	static public Event<Collection<Symbol>> SYMBOLS_UPDATED = new Event<Collection<Symbol>>();
	
	static public Event<Collection<Symbol>> SYMBOLS_CURRENT_PRICE_UPDATED = new Event<Collection<Symbol>>();
	
	static public Event<Collection<Symbol>> SYMBOLS_HISTORICAL_PRICE_UPDATED = new Event<Collection<Symbol>>();
	
	static public Event<Collection<Label>> LABELS_UPDATED = new Event<Collection<Label>>();
	
	static public Event<Collection<? extends SymbolAnalysis>> SYMBOL_ANALYSIS_UPDATED = new Event<Collection<? extends SymbolAnalysis>>();
	
	static public Event<Collection<? extends CurrencyAnalysis>> CURRENCY_ANALYSIS_UPDATED = new Event<Collection<? extends CurrencyAnalysis>>();
	
	static public Event<Transaction> TRANSACTION_SELECTION_CHANGED = new Event<Transaction>();
	
	static public Event<Symbol> SYMBOL_SELECTION_CHANGED = new Event<Symbol>();
	
}
