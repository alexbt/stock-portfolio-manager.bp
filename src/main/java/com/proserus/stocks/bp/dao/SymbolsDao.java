package com.proserus.stocks.bp.dao;

import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Query;

import org.apache.commons.lang3.Validate;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.proserus.stocks.bo.symbols.CurrencyEnum;
import com.proserus.stocks.bo.symbols.HistoricalPrice;
import com.proserus.stocks.bo.symbols.SectorEnum;
import com.proserus.stocks.bo.symbols.Symbol;
import com.proserus.stocks.bp.model.Filter;

@Singleton
public class SymbolsDao {
	@Inject
	private PersistenceManager persistenceManager;

	public SymbolsDao() {
	}

	@SuppressWarnings("unchecked")
	public Collection<Symbol> get(Filter filter) {
		String str = "SELECT s FROM Symbol s WHERE 1=1 AND id<>-1";
		str += getFilterQuery(filter);
		str += getAscendingOrder();
		Query query = persistenceManager.createQuery(str);

		return new HashSet<Symbol>(query.getResultList());
	}

	private String getSymbolQuery(Symbol symbol) {

		String query = "";
		if (symbol != null) {
			query = " AND " + " id=" + symbol.getId();
		}
		return query;
	}

	private String getAscendingOrder() {
		return " ORDER BY ticker ASC";
	}

	private String getFilterQuery(Filter filter) {
		Validate.notNull(filter);
		return getSymbolQuery(filter.getSymbol()) + getCurrencyQuery(filter.getCurrency()) + getSectorQuery(filter.getSector());
	}

	private String getCurrencyQuery(CurrencyEnum currency) {

		String query = "";
		if (currency != null) {
			query = " AND " + " currency='" + currency.getId() + "'";
		}
		return query;
	}

	private String getSectorQuery(SectorEnum sector) {

		String query = "";
		if (sector != null) {
			query = " AND " + " sector='" + sector.getId() + "'";
		}
		return query;
	}

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
		Validate.isTrue(getSymbol(symbol.getTicker()) == null);

		persistenceManager.persist(symbol);
		return symbol;
	}

	public Symbol getSymbol(String ticker) {
		Validate.notNull(ticker);
		Validate.notEmpty(ticker);

		Symbol symbol = null;
		Query query = persistenceManager.createNamedQuery("symbol.findByTicker");
		query.setParameter("ticker", ticker);
		try {
			symbol = (Symbol) query.getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			// TODO logging.
		}

		return symbol;
	}
}
