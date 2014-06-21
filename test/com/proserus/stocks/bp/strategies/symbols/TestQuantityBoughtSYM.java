import java.util.Calendar;

//package com.proserus.stocks.bp.strategies.symbols;
//
//import java.math.BigDecimal;
//import java.util.Date;
//
//import junit.framework.TestCase;
//
//import org.hibernate.ejb.TransactionImpl;
//
//import com.proserus.stocks.bp.FilterBp;
//import com.proserus.stocks.bo.symbols.CurrencyEnum;
//import com.proserus.stocks.bo.symbols.Symbol;
//import com.proserus.stocks.bo.transactions.Transaction;
//import com.proserus.stocks.bo.transactions.TransactionType;
//
//public class TestQuantityBoughtSYM extends TestCase {
//BoBuilder boBuilder = BoBuilderI
//	public void testTransactionWithoutFilter(){
//		
//		Symbol s = new SymbolImpl();
//		s.setCurrency(CurrencyEnum.CAD);
//		s.setCustomPriceFirst(false);
//		s.setName("Test symbol");
//		s.setPrice(new BigDecimal(35), new Year(2010));
//		s.setTicker("Test");
//		
//		Transaction t = new TransactionImpl();
//		t.setDate(Calendar.getInstance().getTime());
//		t.setCommission(new BigDecimal(4.95));
//		t.setPrice(new BigDecimal(36.29));
//		t.setQuantity(new BigDecimal(10));
//		t.setSymbol(s);
//		t.setType(TransactionType.BUY);
//		
//		BigDecimal dec = new QuantityBoughtSYM().getTransactionValue(t, new FilterBp());
//		
//		assertTrue(dec.intValue() == 10);
//	}
//}
