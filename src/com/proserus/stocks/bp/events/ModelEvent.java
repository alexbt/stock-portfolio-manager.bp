package com.proserus.stocks.bp.events;


public class ModelEvent<T> implements Event {
	
	public T resolveModel(Object model){
		assert model != null;
		return (T)model;
	}
	
	public void fire(T model){
		assert model != null;
		
		EventBus.getInstance().fireEvent(this, model);
	}
}
