package com.proserus.stocks.events;

public class NoModelEvent implements Event {
	public void fire(){
		EventBus.getInstance().fireEvent(this, null);
	}
}
