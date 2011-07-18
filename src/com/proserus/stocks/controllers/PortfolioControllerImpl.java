package com.proserus.stocks.controllers;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Observer;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jfree.data.time.Year;

import com.google.inject.Inject;
import com.proserus.stocks.bp.AnalysisBp;
import com.proserus.stocks.bp.DateUtil;
import com.proserus.stocks.bp.FilterBp;
import com.proserus.stocks.bp.ImportExportBp;
import com.proserus.stocks.bp.LabelsBp;
import com.proserus.stocks.bp.SharedFilter;
import com.proserus.stocks.bp.SymbolsBp;
import com.proserus.stocks.bp.TransactionsBp;
import com.proserus.stocks.controllers.iface.CurrencyController;
import com.proserus.stocks.controllers.iface.PortfolioController;
import com.proserus.stocks.dao.PersistenceManager;
import com.proserus.stocks.model.ItemSelection;
import com.proserus.stocks.model.analysis.CurrencyAnalysis;
import com.proserus.stocks.model.analysis.SymbolAnalysis;
import com.proserus.stocks.model.symbols.CurrencyEnum;
import com.proserus.stocks.model.symbols.HistoricalPrice;
import com.proserus.stocks.model.symbols.Symbol;
import com.proserus.stocks.model.transactions.Label;
import com.proserus.stocks.model.transactions.Transaction;

public class PortfolioControllerImpl implements PortfolioController {
	private SymbolsBp symbolsBp;
	private LabelsBp labelsBp;
	private TransactionsBp transactionsBp;
	private AnalysisBp analysisBp;
	private SharedFilter sharedFilter;
	private FilterBp filterBp;
	private ImportExportBp importExportBp;
	private CurrencyController currencyController;
	
	private ItemSelection itemSelection = new ItemSelection();
	
	public ItemSelection getItemSelection() {
    	return itemSelection;
    }
	
	public void select(Transaction t){
		itemSelection.setSelectedTransaction(t);
	}
	
	public void select(Symbol s){
		itemSelection.setSelectedSymbol(s);
	}


	@Inject
	public void setCurrencyController(CurrencyController currencyController) {
    	this.currencyController = currencyController;
    }
	
	@Inject
	public void setImportExportBp(ImportExportBp importExportBp) {
    	this.importExportBp = importExportBp;
    }

	@Inject
	public void setSharedFilter(SharedFilter sharedFilter) {
    	this.sharedFilter = sharedFilter;
    }

	@Inject
	public void setSymbolsBp(SymbolsBp symbolsBp) {
		this.symbolsBp = symbolsBp;
	}

	@Inject
	public void setLabelsBp(LabelsBp labelsBp) {
		this.labelsBp = labelsBp;
	}

	@Inject
	public void setTransactionsBp(TransactionsBp transactionsBp) {
		this.transactionsBp = transactionsBp;
	}

	@Inject
	public void setAnalysisBp(AnalysisBp analysisBp) {
		this.analysisBp = analysisBp;
		//TODO do not do logic here..
		//analysisBp.recalculate(new FilterBp());
	}


	@Inject
	public void setFilterBp(FilterBp filterBp) {
		this.filterBp = filterBp;
	}

	public PortfolioControllerImpl() {
	}

	@Override
	public boolean updateSymbol(Symbol symbol) {
		boolean val = symbolsBp.updateSymbol(symbol);
		analysisBp.recalculate(filterBp);
		return val;
	}

	@Override
	public Collection<Symbol> getSymbols() {
		return symbolsBp.get();
	}

	@Override
	public void addAnalysisObserver(Observer o) {
		analysisBp.addObserver(o);
	}

	@Override
	public boolean remove(Symbol s) {
		if (transactionsBp.contains(s)) {
			return false;
		}
		symbolsBp.remove(s);
		return true;
	}

	@Override
	public Collection<? extends CurrencyAnalysis> getCurrencyAnalysis(FilterBp filter) {
		return analysisBp.getCurrencyAnalysis();
	}

	@Override
	public Collection<? extends SymbolAnalysis> getSymbolAnalysis(FilterBp filter) {
		return analysisBp.getSymbolAnalysis();
	}

	@Override
	public Year getFirstYear() {
		EntityManager em = PersistenceManager.getEntityManager();
		Query query = em.createNamedQuery("transaction.findMinDate");
		Date val = (Date) query.getSingleResult();
		// TODO Manage Date better
		if (val != null) {
			return new Year(val);
		} else {
			return DateUtil.getCurrentYear();
		}
	}

	@Override
	public Symbol addSymbol(Symbol symbol) {
		currencyController.setDefaultCurrency(((CurrencyEnum) symbol.getCurrency()));
		return symbolsBp.add(symbol);
	}

	public Symbol getSymbol(String ticker) {
		return symbolsBp.getSymbol(ticker);
	}

	@Override
	public void addSymbolsObserver(Observer o) {
		symbolsBp.addObserver(o);
//		o.update(symbolsBp, null);
	}

	@Override
	public void addTransactionsObserver(Observer o) {
		transactionsBp.addObserver(o);
	}

	@Override
	public void addFilterObserver(Observer o) {
		filterBp.addObserver(o);
	}

	@Override
	public void setCustomFilter(String custom) {
		throw new AssertionError();
	}

	@Override
	public Collection<Transaction> getTransactions(FilterBp filter) {
		return transactionsBp.getTransactions(filter, true);
	}
	
	public void refresh(){
		analysisBp.recalculate(new FilterBp());
		
		symbolsBp.notifyObservers();
		transactionsBp.notifyObservers();
		
		labelsBp.notifyObservers();
		
		sharedFilter.notifyObservers();
		filterBp.notifyObservers();
	}

	@Override
	public Transaction addTransaction(Transaction t) {
		currencyController.setDefaultCurrency(((CurrencyEnum) t.getSymbol().getCurrency()));
		Symbol s = addSymbol(t.getSymbol());
		t.setSymbol(s);
		t = transactionsBp.add(t);
		analysisBp.recalculate(sharedFilter);
		return t;
	}

	@Override
	public void addTransactionObserver(Observer o) {
		transactionsBp.addObserver(o);
//		o.update(transactionsBp, null);
	}

	@Override
	public void remove(Transaction t) {
		for (Object o : t.getLabelsValues().toArray()) {
			t.removeLabel((Label) o);
		}
		transactionsBp.remove(t);
		analysisBp.recalculate(sharedFilter);
	}

	@Override
	public void remove(Label label) {
		Collection<Transaction> transactions = transactionsBp.getTransactionsByLabel(label);
		for (Transaction t : transactions) {
			t.removeLabel(label);
		}
		labelsBp.remove(label);
		analysisBp.recalculate(sharedFilter);
	}

	@Override
	public void addLabelsObserver(Observer o) {
		labelsBp.addObserver(o);
		o.update(labelsBp, null);//TODO remove this eventually..
	}

	@Override
	public void updateTransaction(Transaction t){
		transactionsBp.updateTransaction(t);
		analysisBp.recalculate(sharedFilter);
	}

	@Override
	public Label addLabel(Label label) {
		Label l = labelsBp.add(label);
		analysisBp.recalculate(sharedFilter);
		return l;
	}

	@Override
	public Collection<Label> getLabels() {
		return labelsBp.get();
	}

	@Override
	public void updatePrices() {
		symbolsBp.updatePrices();
		analysisBp.recalculate(sharedFilter);
	}

	@Override
	public void updateHistoricalPrices() {
		symbolsBp.updateHistoricalPrices();
		analysisBp.recalculate(sharedFilter);
	}

	@Override
	public void update(HistoricalPrice hPrice) {
		symbolsBp.update(hPrice);
		analysisBp.recalculate(sharedFilter);
	}

	@Override
    public ByteArrayOutputStream exportTransactions(FilterBp filter) {
	    try {
	        return importExportBp.exportTransactions(transactionsBp.getTransactions(filter, true));
        } catch (IOException e) {
        }
        return null;
    }

	@Override
    public ByteArrayOutputStream exportTransactions() {
		try {
	        return importExportBp.exportTransactions(transactionsBp.getTransactions());
        } catch (IOException e) {
        	int i=0;
        }
        return null;
    }

	@Override
    public void importTransactions(File file) {
		try {
	        importExportBp.importTransactions(file);
	        refresh();
        } catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
		
    }


	@Override
    public void addSelectionObserver(Observer o) {
	    itemSelection.addObserver(o);	    
    }

	@Override
    public void setSelection(Transaction t) {
		itemSelection.setSelectedTransaction(t);
    }

	@Override
    public void setSelection(Symbol s) {
		itemSelection.setSelectedSymbol(s);
    }

	@Override
    public ItemSelection getSelection() {
	    return itemSelection;
    }
}
