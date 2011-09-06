package com.proserus.stocks.bp;

import java.math.BigDecimal;
import java.util.Collection;

import org.jfree.data.time.Year;

import com.proserus.stocks.model.symbols.HistoricalPrice;
import com.proserus.stocks.model.symbols.Symbol;

public interface OnlineUpdateBp {
	BigDecimal retrieveCurrentPrice(Symbol symbol);

	void retrieveCurrentPrice(Collection<Symbol> symbols);

	Collection<HistoricalPrice> retrieveHistoricalPrices(Symbol symbol, Year year);

}
