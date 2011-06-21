package com.proserus.stocks.controllers;

import java.util.Observer;

import com.proserus.stocks.controllers.iface.CurrencyController;
import com.proserus.stocks.model.symbols.CurrencyEnum;
import com.proserus.stocks.model.symbols.DefaultCurrency;

public class CurrencyControllerImpl implements CurrencyController {

	private static CurrencyController controller = new CurrencyControllerImpl();
	private DefaultCurrency currencies = new DefaultCurrency();

	private CurrencyControllerImpl() {
	}

	static public CurrencyController getInstance() {
		return controller;
	}

	public void setDefaultCurrency(CurrencyEnum currency) {
		currencies.setDefault(currency);
		currencies.save();
	}

	public CurrencyEnum getDefaultCurrency() {
		return currencies.getDefault();
	}

	public void addCurrenciesObserver(Observer o) {
		currencies.addObserver(o);
	}

	public void save() {
		currencies.save();
	}
}
