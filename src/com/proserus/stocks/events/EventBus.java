package com.proserus.stocks.events;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class EventBus {
	
	static private EventBus singleton = new EventBus();
	private EventBus(){
		
	}
	
	static public EventBus getInstance(){
		return singleton;
	}
	
	
	Map<Event, Collection<EventListener>> listeners = new HashMap<Event, Collection<EventListener>>();
	
	public void add(EventListener listener, Event... events){
		for(Event event: events){
			Collection<EventListener> col = listeners.get(event);
			if(col==null){
				col = new ArrayList<EventListener>();
				listeners.put(event, col);
			}
		}
	}

	
	public void fireEvent(Event event, Object model){
		Collection<EventListener> col = listeners.get(event);
		for(EventListener listener: col){
			listener.update(event, model);
		}
	}
	
	public void fireEvent(Event event){
		fireEvent(event, null);
	}
}
