package com.proserus.stocks.controllers.iface;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Collection;
import java.util.Observer;

import org.jfree.data.time.Year;

import com.proserus.stocks.bp.FilterBp;
import com.proserus.stocks.model.ItemSelection;
import com.proserus.stocks.model.analysis.CurrencyAnalysis;
import com.proserus.stocks.model.analysis.SymbolAnalysis;
import com.proserus.stocks.model.symbols.HistoricalPrice;
import com.proserus.stocks.model.symbols.Symbol;
import com.proserus.stocks.model.transactions.Label;
import com.proserus.stocks.model.transactions.LabelImpl;
import com.proserus.stocks.model.transactions.Transaction;
import com.proserus.stocks.model.transactions.TransactionImpl;

public interface PortfolioController {
	Transaction addTransaction(Transaction t);

	void addTransactionObserver(Observer o);

	void remove(Transaction t);
	
	void setSelection(Transaction t);//TODO This should be handled entirely in the view layer..
	void setSelection(Symbol s);//TODO This should be handled entirely in the view layer..
	ItemSelection getSelection();//TODO This should be handled entirely in the view layer..
	
	void remove(Label label);

	LabelImpl addLabel(LabelImpl str);
	
	Collection<LabelImpl> getLabels();
	
	Year getFirstYear();
	
	void addFilterObserver(Observer o);

	Collection<TransactionImpl> getTransactions(FilterBp filter);

	void addLabelsObserver(Observer o);

	void updateTransaction(Transaction t);
	
	boolean updateSymbol(Symbol symbol);


	Collection<Symbol> getSymbols();

	boolean remove(Symbol s);

	Symbol addSymbol(Symbol symbol);
	Symbol getSymbol(String ticker);

	void addSymbolsObserver(Observer o);
	
	void addSelectionObserver(Observer o);
	
	void updatePrices();

	void updateHistoricalPrices();
	
	void addAnalysisObserver(Observer o);

	void addTransactionsObserver(Observer o);

	void setCustomFilter(String custom);
	
	Collection<? extends CurrencyAnalysis> getCurrencyAnalysis(FilterBp filter);

	Collection<? extends SymbolAnalysis> getSymbolAnalysis(FilterBp filter);
	
	void update(HistoricalPrice hPrice);
	
	void refresh();
	
	ByteArrayOutputStream exportTransactions(FilterBp filter);
	
	ByteArrayOutputStream exportTransactions();
	
	void importTransactions(File file);
	
}
