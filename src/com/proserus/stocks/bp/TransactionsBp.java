package com.proserus.stocks.bp;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jfree.data.time.Year;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.proserus.stocks.dao.TransactionsDao;
import com.proserus.stocks.model.symbols.CurrencyEnum;
import com.proserus.stocks.model.symbols.Symbol;
import com.proserus.stocks.model.transactions.Label;
import com.proserus.stocks.model.transactions.Transaction;

@Singleton
public class TransactionsBp{
	
	@Inject
	TransactionsDao transactionsDao;

	private Map<Integer, Integer> years = new HashMap<Integer, Integer>();
	private int minYear = 99999;
	private int maxYear = -1;

	public Transaction add(Transaction t) {
		//TODO Manage Date better
		if (!years.containsKey(t.getDate().getYear())) {
			years.put(t.getDate().getYear(), t.getDate().getYear());
		}
		minYear = Math.min(t.getDate().getYear(), minYear);
		maxYear = Math.max(t.getDate().getYear(), maxYear);

		transactionsDao.add(t);

		return t;
	}
	
	public Year getFirstYear(){
		Date val = transactionsDao.getFirstYear();
		// TODO Manage Date better
		if (val != null) {
			return new Year(val);
		} else {
			return DateUtil.getCurrentYear();
		}
		
	}

	public boolean contains(Symbol symbol) {
		//TODO use count instead ?
		return transactionsDao.getTransactionsBySymbol(symbol, true).size() > 0;
	}
	
	public Collection<Transaction> getTransactions() {
		return transactionsDao.getTransactions();
	}
	
	
	public Collection<Transaction> getTransactions(Filter filter, boolean dateFlag) {
		return transactionsDao.getTransactions(filter, dateFlag);
	}
	
	public Collection<Transaction> getTransactionsByCurrency(CurrencyEnum currency, Filter filter, boolean dateFlag) {
		return transactionsDao.getTransactionsByCurrency(currency, filter, dateFlag);
	}

	public Collection<Transaction> getTransactionsByLabel(Label label) {
		return transactionsDao.getTransactionsByLabel(label);
	}

	public void updateTransaction(Transaction t){
		transactionsDao.add(t);
	}

	public void remove(Transaction t) {
		transactionsDao.remove(t);
	}

	public TransactionsBp() {	
	}

	public int getMinYear() {
		return minYear;
	}

	public int getMaxYear() {
		return maxYear;
	}
}
