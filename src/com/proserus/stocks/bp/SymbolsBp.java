package com.proserus.stocks.bp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Transient;

import org.jfree.data.time.Year;

import com.google.inject.Inject;
import com.proserus.stocks.dao.PersistenceManager;
import com.proserus.stocks.model.common.ObservableModel;
import com.proserus.stocks.model.symbols.HistoricalPrice;
import com.proserus.stocks.model.symbols.Symbol;

public class SymbolsBp extends ObservableModel {

	private static final String INTERNET_PROPERTY = "internet";
	private static final String SYMBOLS_PROPERTIES = "symbols.properties";
	private HashMap<String, Symbol> symbols = new HashMap<String, Symbol>();
	private Properties ptrans = new Properties();
	public static Boolean automaticUpdate = false;
	
	
	private OnlineUpdateBp onlineUpdateBp;

	@Inject
	public void setOnlineUpdateBp(OnlineUpdateBp onlineUpdateBp) {
    	this.onlineUpdateBp = onlineUpdateBp;
    }

	@Transient
	private EntityManager em;

	public SymbolsBp() {
		em = PersistenceManager.getEntityManager();
	}

	public boolean setAutomaticUpdate(boolean flag) {
		//TODO legacy code..
		return false;
	}

	public Collection<Symbol> get() {
		Query query = em.createNamedQuery("symbol.findAll");
		return new HashSet<Symbol>(query.getResultList());
	}

	//TODO This should not allow adding a symbol with same name!
	public boolean updateSymbol(Symbol symbol) {
		PersistenceManager.persist(symbol);
		setChanged();
		notifyObservers();
		return true;
	}

	public void changeFilter() {
		setChanged();
		notifyObservers();
	}

	public void updatePrices() {
		onlineUpdateBp.retrieveCurrentPrice(get());
		setChanged();
		notifyObservers(SymbolUpdateEnum.CURRENT_PRICE);
	}

	public void updateHistoricalPrices() {
		for (Symbol symbol : get()) {
			updateHistoricalPrices(symbol);
		}
		setChanged();
		notifyObservers(SymbolUpdateEnum.HISTORICAL_PRICE);
	}

	public void update(HistoricalPrice hPrice) {
		PersistenceManager.persist(hPrice);
		setChanged();
		notifyObservers(SymbolUpdateEnum.HISTORICAL_PRICE);
	}

	public void updatePrices(Symbol symbol) {
		// TODO Manage Date better
		symbol.setPrice(onlineUpdateBp.retrieveCurrentPrice(symbol), DateUtil.getCurrentYear());
		PersistenceManager.persist(symbol);
	}

	public void updateHistoricalPrices(Symbol symbol) {
		symbol.setPrices(onlineUpdateBp.retrieveHistoricalPrices(symbol, new Year(1994)));
		PersistenceManager.persist(symbol);
	}

	public void remove(Symbol s) {
		PersistenceManager.remove(s);
		setChanged();
		notifyObservers();
	}

	void remove(String s) {
		s = s.toLowerCase();
		symbols.remove(s);
		setChanged();
		notifyObservers();
	}

	public Symbol add(Symbol symbol) {
		Symbol symbol2 = getSymbol(symbol.getTicker());
		if(symbol2 == null){
			PersistenceManager.persist(symbol);
			updatePrices(symbol);
			updateHistoricalPrices(symbol);
			setChanged();
			notifyObservers();
		}else{
			symbol = symbol2;
		}

		return symbol;
	}
	
	

	void init() {
		try {
			symbols = new HashMap<String, Symbol>();
			ptrans.load(new FileInputStream(config + SYMBOLS_PROPERTIES));
			String internet = ptrans.getProperty(INTERNET_PROPERTY);
			if (internet != null) {
				automaticUpdate = new Boolean(internet);
				// ptrans.remove(INTERNET_PROPERTY);
			}
			/*
			 * Iterator iterator = ptrans.keySet().iterator(); HashMap<String, String> str = new HashMap<String, String>();
			 * 
			 * while (iterator.hasNext()) { try { add(new Symbol(ptrans.getProperty((String) iterator.next()).toLowerCase())); } catch
			 * (SymbolAlreadyExistException e) { } }
			 */
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		setChanged();
		notifyObservers();
	}

	/*
	 * public void save() { try { ptrans.clear(); Iterator<String> iterator = symbols.keySet().iterator(); HashMap<String, String> str = new
	 * HashMap<String, String>(); while (iterator.hasNext()) { String val = iterator.next(); str.put(val, symbols.get(val).getContent()); }
	 * 
	 * ptrans.putAll(str); ptrans.put(INTERNET_PROPERTY, new Boolean(automaticUpdate).toString()); ptrans.store(new FileOutputStream(config
	 * + SYMBOLS_PROPERTIES), EMPTY_STR); } catch (FileNotFoundException e) { } catch (IOException e) { } }
	 */

	public boolean hasSymbol(String str) {
		return symbols.containsKey(str.toLowerCase());
	}

	public Symbol getSymbol(String ticker) {
		Symbol symbol = null;
		Query query = em.createNamedQuery("symbol.findByTicker");
		query.setParameter("ticker", ticker);
		try {
			symbol = (Symbol) query.getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			//TODO logging.
		}

		return symbol;
	}
}
