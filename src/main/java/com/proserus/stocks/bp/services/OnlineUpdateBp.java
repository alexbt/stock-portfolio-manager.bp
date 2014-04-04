package com.proserus.stocks.bp.services;

import java.math.BigDecimal;
import java.util.Collection;

import org.jfree.data.time.Year;

import com.proserus.stocks.bo.symbols.HistoricalPrice;
import com.proserus.stocks.bo.symbols.Symbol;

public interface OnlineUpdateBp {
	BigDecimal retrieveCurrentPrice(Symbol symbol);

	void retrieveCurrentPrice(Collection<Symbol> symbols);

	Collection<HistoricalPrice> retrieveHistoricalPrices(Symbol symbol, Year year);//FIXME Year JFree

}
