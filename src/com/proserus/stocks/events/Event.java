package com.proserus.stocks.events;

public class Event<T> {
	
	public T resolveModel(Object model){
		return (T)model;
	}
	
	public void fire(T model){
		EventBus.getInstance().fireEvent(this, model);
	}
}
