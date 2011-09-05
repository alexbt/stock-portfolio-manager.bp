package com.proserus.stocks.events;

import java.util.Collection;

import com.proserus.stocks.model.transactions.Transaction;

public class SwingEvents {

	static public Event<Collection<Transaction>> TRANSACTION_UPDATED = new Event<Collection<Transaction>>();
	
}
