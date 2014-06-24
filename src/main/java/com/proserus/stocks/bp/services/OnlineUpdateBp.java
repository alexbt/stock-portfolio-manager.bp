package com.proserus.stocks.bp.services;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

import com.proserus.stocks.bo.symbols.HistoricalPrice;
import com.proserus.stocks.bo.symbols.Symbol;

public interface OnlineUpdateBp {
	BigDecimal retrieveCurrentPrice(Symbol symbol);

	void retrieveCurrentPrice(Collection<Symbol> symbols);

	Set<HistoricalPrice> retrieveHistoricalPrices(Symbol symbol);

}
