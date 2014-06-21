package com.proserus.stocks.bp.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;

import com.google.inject.Singleton;
import com.proserus.stocks.bo.common.BoBuilder;
import com.proserus.stocks.bo.symbols.HistoricalPrice;
import com.proserus.stocks.bo.symbols.Symbol;
import com.proserus.stocks.bp.utils.DateUtil;

@Singleton
public class YahooUpdateBp implements OnlineUpdateBp {
	@Inject private BoBuilder boBuilder;
	
	private static final String COMMA_STR = ",";
	private static final String DOT_TO = ".to";
	private static final String DASH_TO = "-to";
	private static final String DASH_UN = "-un";
	private static final String DOT_UN = "\\.un";

	private static final String QUOTE = "\",|\"";
  //http://www.gummy-stuff.org/Yahoo-data.htm
	// http://quote.yahoo.com/d/quotes.csv?s=usdcad=X&f=nl1&e=.csv
	private static String URL_START = "http://finance.yahoo.com/d/quotes.csv?s=";
	private static String URL_END = "&f=nl1&e=.csv";

	// doc: http://code.google.com/p/yahoo-finance-managed/

	private static String URL_HIST_START = "http://ichart.finance.yahoo.com/table.csv?s=";
	private static String URL_HIST_END = "&c=1975&f=2010&g=m";

	// http://ichart.finance.yahoo.com/table.csv?s=goog&c=1975&f=2010&g=m
	public BigDecimal retrieveCurrentPrice(Symbol symbol) {
		Validate.notNull(symbol);
		
		BigDecimal value = BigDecimal.ZERO;
		String tAddress = URL_START + symbol.getTicker().replaceFirst(DOT_UN, DASH_UN).replaceAll(DASH_TO, DOT_TO)
		        + URL_END;
		URL tDocument;
		try {
			tDocument = new URL(tAddress);
			URLConnection tConnection;
			tConnection = tDocument.openConnection();
			tConnection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(tConnection.getInputStream()));
			char[] buffer = new char[100];
			in.read(buffer);
			String[] str = new String(buffer).split(QUOTE);
			if (str != null && str.length >= 3) {
				if(symbol.getName().isEmpty() && !symbol.getTicker().toUpperCase().equals(str[1])){
					symbol.setName(str[1]);
				}
				value = new BigDecimal(Double.parseDouble(str[2]));
			} else {
				value = BigDecimal.ONE.negate();
			}
		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
		return value;
	}

	public void retrieveCurrentPrice(Collection<Symbol> symbols) {
		Validate.notNull(symbols);
		
		String listOfSymbols = "";
		for (Symbol s : symbols) {
			listOfSymbols = listOfSymbols + s.getTicker() + "+";
		}

		if (listOfSymbols.length() > 0) {
			listOfSymbols = listOfSymbols.substring(0, listOfSymbols.length() - 1);

			String tAddress = URL_START + listOfSymbols.replaceFirst(DOT_UN, DASH_UN).replaceAll(DASH_TO, DOT_TO)
			        + URL_END;
			URL tDocument;
			try {
				tDocument = new URL(tAddress);
				URLConnection tConnection;
				tConnection = tDocument.openConnection();
				tConnection.connect();
				BufferedReader in = new BufferedReader(new InputStreamReader(tConnection.getInputStream()));

				for (Symbol s : symbols) {
					String re = in.readLine();
					// in.read(buffer);
					String[] str = re.split(QUOTE);
					if (str != null && str.length >= 3) {
						if((s.getName().isEmpty()) && !s.getTicker().toUpperCase().equals(str[1])){
							s.setName(str[1]);
						}
						//TODO Manage Date better
						s.setPrice(new BigDecimal(Double.parseDouble(str[2])), DateUtil.getCurrentYear());
					}
				}
			} catch (MalformedURLException e) {
			} catch (IOException e) {
			}
		}
	}

	public Collection<HistoricalPrice> retrieveHistoricalPrices(Symbol symbol, int year) {
		Validate.notNull(symbol);
		
		Collection<HistoricalPrice> prices = symbol.getPrices();
		Map<Integer, HistoricalPrice> mapPrices = symbol.getMapPrices();

		String tAddress = URL_HIST_START + symbol.getTicker().replaceFirst(DOT_UN, DASH_UN).replaceAll(DASH_TO, DOT_TO)
		        + URL_HIST_END;
		URL tDocument;
		try {
			tDocument = new URL(tAddress);
			URLConnection tConnection;
			tConnection = tDocument.openConnection();
			tConnection.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(tConnection.getInputStream()));
			String str;
			Integer previousYear = DateUtil.getCurrentYear()-1;
			while (true) {
				while ((str=in.readLine())!=null){
					if(str.startsWith(String.valueOf(previousYear))){
						break;
					}
				}
				
				if(str==null){
					break;
				}

				Float value = new Float(str.split(COMMA_STR)[6]);
				HistoricalPrice h = (HistoricalPrice) mapPrices.get(previousYear);
				if (h == null) {
					h = boBuilder.getHistoricalPrice();
					prices.add(h);
				}

				if (h.getCustomPrice() == null) {
					h.setCustomPrice(new BigDecimal(value));
				}
				if (h.getYear() == null) {
					//TODO Manage Date better
					h.setYear(previousYear);
				}
				h.setPrice(new BigDecimal(value));
				previousYear = previousYear--;
			}

		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}

		return prices;
	}

}
