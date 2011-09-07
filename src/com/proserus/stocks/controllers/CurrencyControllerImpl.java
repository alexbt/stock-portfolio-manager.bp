package com.proserus.stocks.controllers;

import com.proserus.stocks.events.SwingEvents;
import com.proserus.stocks.model.symbols.CurrencyEnum;
import com.proserus.stocks.model.symbols.DefaultCurrency;

public class CurrencyControllerImpl{

	private DefaultCurrency currencies = new DefaultCurrency();

	public CurrencyControllerImpl() {
	}

	public void setDefaultCurrency(CurrencyEnum currency) {
		currencies.setDefault(currency);
		SwingEvents.CURRENCY_DEFAULT_CHANGED.fire(currencies.getDefault());
		currencies.save();
	}

	public CurrencyEnum getDefaultCurrency() {
		return currencies.getDefault();
	}

	public void save() {
		currencies.save();
	}
}
