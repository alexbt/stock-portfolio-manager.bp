package com.proserus.stocks.bp.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.Validate;

public class EventBus {
	
	static private EventBus singleton = new EventBus();
	private EventBus(){
		
	}
	
	static public EventBus getInstance(){
		return singleton;
	}
	
	
	Map<Event, Collection<EventListener>> listeners = new HashMap<Event, Collection<EventListener>>();
	
	public void add(EventListener listener, Event... events){
		Validate.notNull(listener);
		Validate.notNull(events);
		Validate.notEmpty(events);
		
		for(Event event: events){
			Collection<EventListener> col = listeners.get(event);
			if(col==null){
				col = new ArrayList<EventListener>();
				listeners.put(event, col);
			}
			col.add(listener);
		}
	}

	
	public void fireEvent(Event event, Object model){
		Validate.notNull(event);
		
		Collection<EventListener> col = listeners.get(event);
		
		if(col != null){
			for(EventListener listener: col){
				listener.update(event, model);
			}
		}
	}
	
	public void fireEvent(Event event){
		fireEvent(event, null);
	}
}
