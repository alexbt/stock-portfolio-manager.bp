package com.proserus.stocks.bp;

import java.util.Collection;
import java.util.HashMap;

import javax.persistence.EntityManager;
import javax.persistence.Transient;

import org.jfree.data.time.Year;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.proserus.stocks.dao.SymbolsDao;
import com.proserus.stocks.model.common.ObservableModel;
import com.proserus.stocks.model.symbols.HistoricalPrice;
import com.proserus.stocks.model.symbols.Symbol;

@Singleton
public class SymbolsBp extends ObservableModel {

	private HashMap<String, Symbol> symbols = new HashMap<String, Symbol>();
	public static Boolean automaticUpdate = false;
	
	@Inject 
	private SymbolsDao symbolsDao;
	
	private OnlineUpdateBp onlineUpdateBp;

	@Inject
	public void setOnlineUpdateBp(OnlineUpdateBp onlineUpdateBp) {
    	this.onlineUpdateBp = onlineUpdateBp;
    }

	@Transient
	private EntityManager em;

	public SymbolsBp() {
	}

	public boolean setAutomaticUpdate(boolean flag) {
		//TODO legacy code..
		return false;
	}

	public Collection<Symbol> get() {
		return symbolsDao.get();
//		Query query = em.createNamedQuery("symbol.findAll");
//		return new HashSet<Symbol>(query.getResultList());
	}

	//TODO This should not allow adding a symbol with same name!
	public boolean updateSymbol(Symbol symbol) {
		symbolsDao.updateSymbol(symbol);
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
		symbolsDao.update(hPrice);
		setChanged();
		notifyObservers(SymbolUpdateEnum.HISTORICAL_PRICE);
	}

	public void updatePrices(Symbol symbol) {
		// TODO Manage Date better
		symbol.setPrice(onlineUpdateBp.retrieveCurrentPrice(symbol), DateUtil.getCurrentYear());
		symbolsDao.updatePrices(symbol);
	}

	public void updateHistoricalPrices(Symbol symbol) {
		symbol.setPrices(onlineUpdateBp.retrieveHistoricalPrices(symbol, new Year(1994)));
		symbolsDao.updateHistoricalPrices(symbol);
	}

	public void remove(Symbol s) {
		symbolsDao.remove(s);
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
			symbolsDao.add(symbol);
			updatePrices(symbol);
			updateHistoricalPrices(symbol);
			setChanged();
			notifyObservers();
		}else{
			symbol = symbol2;
		}

		return symbol;
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
		return symbolsDao.getSymbol(ticker);
	}
}
