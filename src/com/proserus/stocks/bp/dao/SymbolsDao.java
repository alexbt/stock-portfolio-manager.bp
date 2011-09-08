package com.proserus.stocks.bp.dao;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Query;

import org.apache.commons.lang3.Validate;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.proserus.stocks.bo.symbols.HistoricalPrice;
import com.proserus.stocks.bo.symbols.Symbol;

@Singleton
public class SymbolsDao {
	@Inject
	private PersistenceManager persistenceManager;

	public SymbolsDao() {
	}

	public Collection<Symbol> get() {
		Query query = persistenceManager.getEntityManager().createNamedQuery("symbol.findAll");
		return new HashSet<Symbol>(query.getResultList());
	}

	// TODO This should not allow adding a symbol with same name!
	public boolean updateSymbol(Symbol symbol) {
		Validate.notNull(symbol);
		persistenceManager.persist(symbol);
		return true;
	}

	public void update(HistoricalPrice hPrice) {
		Validate.notNull(hPrice);
		persistenceManager.persist(hPrice);
	}

	public void updatePrices(Symbol symbol) {
		Validate.notNull(symbol);
		// TODO Manage Date better
		persistenceManager.persist(symbol);
	}

	public void updateHistoricalPrices(Symbol symbol) {
		Validate.notNull(symbol);
		persistenceManager.persist(symbol);
	}

	public void remove(Symbol s) {
		Validate.notNull(s);
		persistenceManager.remove(s);
	}

	public Symbol add(Symbol symbol) {
		Validate.notNull(symbol);

		persistenceManager.persist(symbol);
		return symbol;
	}

	public Symbol getSymbol(String ticker) {
		Validate.notNull(ticker);
		Validate.notEmpty(ticker);

		Symbol symbol = null;
		Query query = persistenceManager.getEntityManager().createNamedQuery("symbol.findByTicker");
		query.setParameter("ticker", ticker);
		try {
			symbol = (Symbol) query.getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			// TODO logging.
		}

		return symbol;
	}
}
