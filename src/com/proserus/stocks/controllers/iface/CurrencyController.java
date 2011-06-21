package com.proserus.stocks.controllers.iface;

import java.util.Observer;

import com.proserus.stocks.model.symbols.CurrencyEnum;


public interface CurrencyController {
	void setDefaultCurrency(CurrencyEnum cur);

	public CurrencyEnum getDefaultCurrency();
	void addCurrenciesObserver(Observer o);

	void save();
}
