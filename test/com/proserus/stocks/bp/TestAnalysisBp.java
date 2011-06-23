package com.proserus.stocks.bp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import junit.framework.TestCase;

import org.jfree.data.time.Year;
import org.mockito.Mockito;

import com.proserus.stocks.model.analysis.SymbolAnalysis;
import com.proserus.stocks.model.symbols.CurrencyEnum;
import com.proserus.stocks.model.symbols.Symbol;
import com.proserus.stocks.model.transactions.Transaction;
import com.proserus.stocks.model.transactions.TransactionType;

public class TestAnalysisBp extends TestCase {
	private TransactionsBp transactionsBp = Mockito.mock(TransactionsBp.class);

	private SymbolsBp symbolsBp = Mockito.mock(SymbolsBp.class);

	public void testRecalculate(){
		Symbol s = new Symbol();
		s.setCurrency(CurrencyEnum.CAD);
		s.setCustomPriceFirst(false);
		s.setName("Test symbol");
		s.setPrice(new BigDecimal(35), new Year(2010));
		s.setTicker("Test");
		
		Transaction t = new Transaction();
		t.setDate(new Date());
		t.setCommission(new BigDecimal(4.95));
		t.setPrice(new BigDecimal(36.29));
		t.setQuantity(new BigDecimal(10));
		t.setSymbol(s);
		t.setType(TransactionType.BUY);
		
		
		Collection<Symbol> symbols = new ArrayList<Symbol>();
		symbols.add(s);
		
		Collection<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(t);
		
		FilterBp filter = new FilterBp();
		Mockito.when(symbolsBp.get()).thenReturn(symbols);
		Mockito.when(transactionsBp.getTransactionsBySymbol(s, filter,false)).thenReturn(transactions);
		
		AnalysisBp analysis = new AnalysisBp();
		analysis.setSymbolsBp(symbolsBp);
		analysis.setTransactionsBp(transactionsBp);
		analysis.recalculate(filter);
		for(SymbolAnalysis symbolAnalysis: analysis.getSymbolAnalysis()){
			assertTrue(symbolAnalysis.getQuantity().intValue() == 10);
		}
		
	}
}