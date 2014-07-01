package com.proserus.stocks.bp.events;

import java.util.Collection;

import com.proserus.stocks.bo.analysis.CurrencyAnalysis;
import com.proserus.stocks.bo.analysis.LabelAnalysis;
import com.proserus.stocks.bo.analysis.SectorAnalysis;
import com.proserus.stocks.bo.analysis.SymbolAnalysis;
import com.proserus.stocks.bo.analysis.YearAnalysis;
import com.proserus.stocks.bo.common.DatabasePaths;
import com.proserus.stocks.bo.symbols.CurrencyEnum;
import com.proserus.stocks.bo.symbols.Symbol;
import com.proserus.stocks.bo.transactions.Label;
import com.proserus.stocks.bo.transactions.Transaction;

public class ModelChangeEvents {
	static public ModelEvent<Collection<Transaction>> TRANSACTION_UPDATED = new ModelEvent<Collection<Transaction>>();

	static public ModelEvent<Collection<Symbol>> SYMBOLS_UPDATED = new ModelEvent<Collection<Symbol>>();

	static public ModelEvent<Collection<Symbol>> SYMBOLS_LIST_UPDATED = new ModelEvent<Collection<Symbol>>();

	static public ModelEvent<Collection<Symbol>> FILTER_SYMBOLS = new ModelEvent<Collection<Symbol>>();

	static public NoModelEvent SYMBOLS_HISTORICAL_PRICE_UPDATED = new NoModelEvent();

	static public NoModelEvent SYMBOLS_CURRENT_PRICE_UPDATED = new NoModelEvent();

	static public ModelEvent<Collection<Label>> LABELS_UPDATED = new ModelEvent<Collection<Label>>();

	static public ModelEvent<Collection<SymbolAnalysis>> SYMBOL_ANALYSIS_UPDATED = new ModelEvent<Collection<SymbolAnalysis>>();

	static public ModelEvent<Collection<CurrencyAnalysis>> CURRENCY_ANALYSIS_UPDATED = new ModelEvent<Collection<CurrencyAnalysis>>();

	static public ModelEvent<Collection<SectorAnalysis>> SECTOR_ANALYSIS_UPDATED = new ModelEvent<Collection<SectorAnalysis>>();

	static public ModelEvent<Collection<LabelAnalysis>> LABEL_ANALYSIS_UPDATED = new ModelEvent<Collection<LabelAnalysis>>();

	static public ModelEvent<Collection<YearAnalysis>> YEAR_ANALYSIS_UPDATED = new ModelEvent<Collection<YearAnalysis>>();

	static public ModelEvent<Transaction> TRANSACTION_SELECTION_CHANGED = new ModelEvent<Transaction>();

	static public ModelEvent<Symbol> SYMBOL_SELECTION_CHANGED = new ModelEvent<Symbol>();

	static public final ModelEvent<CurrencyEnum> CURRENCY_DEFAULT_CHANGED = new ModelEvent<CurrencyEnum>();

	static public final ModelEvent<DatabasePaths> DATABASE_DETECTED = new ModelEvent<DatabasePaths>();

	static public final ModelEvent<DatabasePaths> DATABASE_SELECTED = new ModelEvent<DatabasePaths>();

	static public final NoModelEvent CURRENT_DATABASE_DELETED = new NoModelEvent();

	public static final NoModelEvent LOADING_COMPLETED = new NoModelEvent();

}
