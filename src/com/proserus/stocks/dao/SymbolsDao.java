package com.proserus.stocks.dao;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Query;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.proserus.stocks.model.symbols.HistoricalPrice;
import com.proserus.stocks.model.symbols.Symbol;

@Singleton
public class SymbolsDao {
	@Inject
	private PersistenceManager persistenceManager;

	public SymbolsDao() {
	}

	public boolean setAutomaticUpdate(boolean flag) {
		//TODO legacy code..
		return false;
	}

	public Collection<Symbol> get() {
		Query query = persistenceManager.getEntityManager().createNamedQuery("symbol.findAll");
		return new HashSet<Symbol>(query.getResultList());
	}

	//TODO This should not allow adding a symbol with same name!
	public boolean updateSymbol(Symbol symbol) {
		persistenceManager.persist(symbol);
		return true;
	}


	public void update(HistoricalPrice hPrice) {
		persistenceManager.persist(hPrice);
	}

	public void updatePrices(Symbol symbol) {
		// TODO Manage Date better
		persistenceManager.persist(symbol);
	}

	public void updateHistoricalPrices(Symbol symbol) {
		persistenceManager.persist(symbol);
	}

	public void remove(Symbol s) {
		persistenceManager.remove(s);
	}

	public Symbol add(Symbol symbol) {
		persistenceManager.persist(symbol);
		return symbol;
	}
	
	public Symbol getSymbol(String ticker) {
		Symbol symbol = null;
		Query query = persistenceManager.getEntityManager().createNamedQuery("symbol.findByTicker");
		query.setParameter("ticker", ticker);
		try {
			symbol = (Symbol) query.getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			//TODO logging.
		}

		return symbol;
	}
}
