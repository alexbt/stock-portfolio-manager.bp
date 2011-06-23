package com.proserus.stocks.bp;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Transient;

import org.jfree.data.time.Year;

import com.proserus.stocks.dao.PersistenceManager;
import com.proserus.stocks.model.common.ObservableModel;
import com.proserus.stocks.model.symbols.CurrencyEnum;
import com.proserus.stocks.model.symbols.Symbol;
import com.proserus.stocks.model.transactions.Label;
import com.proserus.stocks.model.transactions.Transaction;

public class TransactionsBp extends ObservableModel {
	@Transient
	private EntityManager em;

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

		t = (Transaction) PersistenceManager.persist(t);

		setChanged();
		notifyObservers();
		return t;
	}

	public void changeFilter() {
		setChanged();
		notifyObservers();
	}

	public boolean contains(Symbol symbol) {
		return getTransactionsBySymbol(symbol, true).size() > 0;
	}
	
	public Collection<Transaction> getTransactionsBySymbol(Symbol s, boolean dateFlag) {
		String str = "SELECT t FROM Transaction t where 1=1";
		str += getSymbolQuery(s);
		str += getAscendingOrder();
		Query query = em.createQuery(str);
		return query.getResultList();
	}

	public Collection<Transaction> getTransactionsBySymbol(Symbol s, FilterBp filter, boolean dateFlag) {
		String str = "SELECT t FROM Transaction t where 1=1";
		str += getFilterQuery(filter, dateFlag);
		str += getSymbolQuery(s);
		str += getAscendingOrder();
		Query query = em.createQuery(str);
		return query.getResultList();
	}
	
	public Collection<Transaction> getTransactions(FilterBp filter, boolean dateFlag) {
		String str = "SELECT t FROM Transaction t where 1=1";
		str += getFilterQuery(filter,dateFlag);
		str += getAscendingOrder();
		Query query = em.createQuery(str);
		return query.getResultList();
	}
	
	public Collection<Transaction> getTransactionsByCurrency(CurrencyEnum currency, FilterBp filter, boolean dateFlag) {
		String str = "SELECT t FROM Transaction t, Symbol s WHERE symbol_id=s.id";
		str += getFilterQuery(filter, dateFlag);
		str += getCurrencyQuery(currency);
		str += getAscendingOrder();
		Query query = em.createQuery(str);
		return query.getResultList();
	}

	private String getFilterQuery(FilterBp filter, boolean dateFlag) {
		//TODO Manage Date better
		return getLabelQuery(filter.getLabels()) + getSymbolQuery(filter.getSymbol()) + getDateQuery(filter.getYear(),dateFlag);
	}

	private String getLabelQuery(Collection<Label> labels) {
		String query = "";
		for (Label label : labels) {

			query += " AND " + label.getId() + " " + Transaction.IN_LABELS;
		}
		return query;
	}

	private String getSymbolQuery(Symbol symbol) {
		String query = "";
		if (symbol != null) {
			query = " AND " + " symbol_id=" + symbol.getId();
		}
		return query;
	}

	private String getDateQuery(Year year, boolean dateFlag) {
		String query = "";
		if (year != null) {
			String DATE_DB_FORMAT_MIN = "-12-31 23:59:59";
			String DATE_DB_FORMAT_MAX = "-01-01 00:00:00";

			if(dateFlag){
				query += " AND " + " date>'" + (year.getYear()-1) + DATE_DB_FORMAT_MIN + "'";
			}
			query += " AND " + " date<'" + (year.getYear()+1) + DATE_DB_FORMAT_MAX + "'";
		}
		return query;
	}

	private String getAscendingOrder() {
		return " ORDER BY date ASC";
	}

	private String getCurrencyQuery(CurrencyEnum currency) {
		String query = "";
		if (currency != null) {
			//FIXME Do not use ordinal
			query = " AND " + " currency=" + currency.ordinal();
		}
		return query;
	}

	public Collection<Transaction> getTransactionsByLabel(Label label) {
		Query query = em.createNamedQuery("transaction.findAllByLabel");
		query.setParameter("label", label);
		return query.getResultList();
	}


	/*
	 * public Collection<Transaction> getTransactions() { return getTransactionsByFilter(Filter.getInstance()); /*EntityManager em =
	 * PersistenceManager.getEntityManager(); Query query = em.createNamedQuery("transaction.findAll"); Collection<Transaction> col = null;
	 * 
	 * try { col = query.getResultList(); } catch (EntityNotFoundException e) { col = new ArrayList<Transaction>(); }
	 * 
	 * return col; }
	 */
	public void updateTransaction(Transaction t){
		add(t);
	}

	public void remove(Transaction t) {
		PersistenceManager.remove(t);
		setChanged();
		notifyObservers();
	}

	public TransactionsBp() {	
		em = PersistenceManager.getEntityManager();
	}

	public int getMinYear() {
		return minYear;
	}

	public int getMaxYear() {
		return maxYear;
	}
}
