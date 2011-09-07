package com.proserus.stocks.bp.strategies.symbols;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.proserus.stocks.bp.Filter;
import com.proserus.stocks.model.analysis.Analysis;
import com.proserus.stocks.model.transactions.Transaction;

public class CostBasisSYM implements SymbolStrategySYM {
	protected static Logger calculsLog = Logger.getLogger("calculs." + CostBasisSYM.class.getName());
	
	@Override
	public void process(Analysis analysis, Collection<Transaction> transactions, Filter filter) {
		BigDecimal value = BigDecimal.ZERO;
		
		if (calculsLog.isInfoEnabled()) {
			calculsLog.info("--------------------------------------");
			calculsLog.info("CostBasisSYM = quantity x averagePrice");
			calculsLog.info("getQuantity: " + analysis.getQuantity());
			calculsLog.info("getAveragePrice: " + analysis.getAveragePrice());
		}
		value = analysis.getAveragePrice().multiply(analysis.getQuantity());
		
		//value = value.add(analysis.getTotalCost().subtract(value));
		//value.subtract(analysis.getTotalCost().subtract(analysis.getQua))
				
		calculsLog.info("setAveragePrice = " +  value);
		analysis.setCostBasis(value);
	}
}
