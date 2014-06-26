package com.proserus.stocks.bp.events;

public class ModelEvent<T> implements Event {

	@SuppressWarnings("unchecked")
	public T resolveModel(Object model) {
		return (T) model;
	}

	public void fire(T model) {
		EventBus.getInstance().fireEvent(this, model);
	}
}
