package com.proserus.stocks.bp.services;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Transient;

import org.apache.commons.lang3.Validate;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.proserus.stocks.bo.symbols.HistoricalPrice;
import com.proserus.stocks.bo.symbols.Symbol;
import com.proserus.stocks.bp.dao.SymbolsDao;
import com.proserus.stocks.bp.model.Filter;
import com.proserus.stocks.bp.utils.DateUtils;

@Singleton
public class SymbolsBp {

	public static Boolean automaticUpdate = false;

	@Inject
	private SymbolsDao symbolsDao;

	private OnlineUpdateBp onlineUpdateBp;

	@Inject
	public void setOnlineUpdateBp(OnlineUpdateBp onlineUpdateBp) {
		this.onlineUpdateBp = onlineUpdateBp;
	}

	@Transient
	private EntityManager em;

	public SymbolsBp() {
	}

	public Collection<Symbol> get(Filter filter) {
		return symbolsDao.get(filter);
	}

	public Collection<Symbol> get() {
		return symbolsDao.get(new Filter());
	}

	public boolean updateSymbol(Symbol symbol) {
		Validate.notNull(symbol);
		symbolsDao.updateSymbol(symbol);
		return true;
	}

	public void updatePrices(Filter filter) {
		Collection<Symbol> symbols = get(filter);
		onlineUpdateBp.retrieveCurrentPrice(symbols);
		for (Symbol symbol : symbols) {
			symbolsDao.updatePrices(symbol);
		}
	}

	public void updateHistoricalPrices(Filter filter) {
		for (Symbol symbol : get(filter)) {
			updateHistoricalPrices(symbol);
		}
	}

	public void update(HistoricalPrice hPrice) {
		Validate.notNull(hPrice);
		symbolsDao.update(hPrice);
	}

	public void updatePrices(Symbol symbol) {
		Validate.notNull(symbol);
		symbol.setPrice(onlineUpdateBp.retrieveCurrentPrice(symbol), DateUtils.getCurrentYear());
		symbolsDao.updatePrices(symbol);
	}

	public void updateHistoricalPrices(Symbol symbol) {
		Validate.notNull(symbol);

		symbol.setPrices(onlineUpdateBp.retrieveHistoricalPrices(symbol));
		symbolsDao.updateHistoricalPrices(symbol);
	}

	public void remove(Symbol symbol) {
		Validate.notNull(symbol);

		symbolsDao.remove(symbol);
	}

	public Symbol add(Symbol symbol) {
		Validate.notNull(symbol);

		Symbol symbol2 = getSymbol(symbol.getTicker());
		if (symbol2 == null) {
			symbolsDao.add(symbol);
			updatePrices(symbol);
			updateHistoricalPrices(symbol);
		} else {
			symbol = symbol2;
		}

		return symbol;
	}

	public Symbol getSymbol(String ticker) {
		Validate.notNull(ticker);
		Validate.notEmpty(ticker);

		return symbolsDao.getSymbol(ticker);
	}
}
