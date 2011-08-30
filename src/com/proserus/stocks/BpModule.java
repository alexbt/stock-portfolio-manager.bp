package com.proserus.stocks;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.proserus.stocks.bp.AnalysisBp;
import com.proserus.stocks.bp.FilterBp;
import com.proserus.stocks.bp.LabelsBp;
import com.proserus.stocks.bp.OnlineUpdateBp;
import com.proserus.stocks.bp.SymbolsBp;
import com.proserus.stocks.bp.TransactionsBp;
import com.proserus.stocks.bp.YahooUpdateBp;
import com.proserus.stocks.controllers.CurrencyControllerImpl;
import com.proserus.stocks.controllers.PortfolioControllerImpl;
import com.proserus.stocks.controllers.iface.CurrencyController;
import com.proserus.stocks.controllers.iface.PortfolioController;

public class BpModule extends AbstractModule {
	  @Override 
	  protected void configure() {
	    bind(PortfolioController.class).to(PortfolioControllerImpl.class);
	    bind(CurrencyController.class).to(CurrencyControllerImpl.class);
	    bind(OnlineUpdateBp.class).to(YahooUpdateBp.class);
	    bind( AnalysisBp.class ).in( Scopes.SINGLETON );
	    bind( FilterBp.class ).in( Scopes.SINGLETON );
	    bind( LabelsBp.class ).in( Scopes.SINGLETON );
	    bind( SymbolsBp.class ).in( Scopes.SINGLETON );
	    bind( TransactionsBp.class ).in( Scopes.SINGLETON );
//	    bind( PersistenceManager.class ).in( Scopes.SINGLETON );
//	    bind(EntityManager.class).to(EntityManagerImpl.class);
	  }
	}