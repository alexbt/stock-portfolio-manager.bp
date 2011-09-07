package com.proserus.stocks.events;

public class ModelEvent<T> implements Event {
	
	public T resolveModel(Object model){
		return (T)model;
	}
	
	public void fire(T model){
		EventBus.getInstance().fireEvent(this, model);
	}
}
