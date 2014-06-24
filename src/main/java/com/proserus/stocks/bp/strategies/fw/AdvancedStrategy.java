package com.proserus.stocks.bp.strategies.fw;

import java.math.BigDecimal;
import java.util.Collection;

import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.analysis.ViewableAnalysis;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.model.Filter;

public abstract class AdvancedStrategy implements SymbolStrategy<BigDecimal> {

	@Override
	public void process(Analysis analysis, Collection<Transaction> transactions, Filter filter) {
		assert analysis != null;
		assert transactions != null;
		assert filter != null;

		BigDecimal value;
		try {
			value = process(analysis);
			if (value == null || Double.valueOf(value.doubleValue()).isInfinite() || Double.valueOf(value.doubleValue()).isNaN()) {
				value = getDefaultAnalysisValue();
			}
		} catch (NumberFormatException e) {
			value = getDefaultAnalysisValue();
		}

		setAnalysisValue(analysis, value);
	}

	protected abstract BigDecimal process(ViewableAnalysis analysis);

	@Override
	public BigDecimal getDefaultAnalysisValue() {
		return BigDecimal.ZERO;
	}
}
