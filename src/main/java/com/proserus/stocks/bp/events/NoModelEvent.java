package com.proserus.stocks.bp.events;

public class NoModelEvent implements Event {
	public void fire(){
		EventBus.getInstance().fireEvent(this, null);
	}
}
