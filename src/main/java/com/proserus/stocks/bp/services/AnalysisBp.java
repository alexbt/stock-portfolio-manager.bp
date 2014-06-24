package com.proserus.stocks.bp.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.Validate;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.proserus.stocks.bo.analysis.Analysis;
import com.proserus.stocks.bo.analysis.AnalysisImpl;
import com.proserus.stocks.bo.analysis.CurrencyAnalysis;
import com.proserus.stocks.bo.analysis.LabelAnalysis;
import com.proserus.stocks.bo.analysis.SectorAnalysis;
import com.proserus.stocks.bo.analysis.SymbolAnalysis;
import com.proserus.stocks.bo.analysis.YearAnalysis;
import com.proserus.stocks.bo.symbols.CurrencyEnum;
import com.proserus.stocks.bo.symbols.SectorEnum;
import com.proserus.stocks.bo.symbols.Symbol;
import com.proserus.stocks.bo.transactions.Label;
import com.proserus.stocks.bo.transactions.Transaction;
import com.proserus.stocks.bp.dao.LabelsDao;
import com.proserus.stocks.bp.dao.SymbolsDao;
import com.proserus.stocks.bp.dao.TransactionsDao;
import com.proserus.stocks.bp.model.Filter;
import com.proserus.stocks.bp.utils.DateUtils;

@Singleton
public class AnalysisBp {
	@Inject
	private TransactionsDao transactionsDao;

	@Inject
	private SymbolsDao symbolsDao;

	@Inject
	private LabelsDao labelsDao;

	private Collection<SymbolAnalysis> symbolAnalysis;
	private Collection<CurrencyAnalysis> currencyAnalysis;
	private Collection<YearAnalysis> yearAnalysis;

	public Collection<YearAnalysis> getYearAnalysis() {
		return yearAnalysis;
	}

	public Collection<LabelAnalysis> getLabelAnalysis() {
		return labelAnalysis;
	}

	private Collection<SectorAnalysis> sectorAnalysis;

	private Collection<LabelAnalysis> labelAnalysis;

	public AnalysisBp() {
		// recalculate(new FilterBp());
	}

	public void recalculate(Filter filter) {
		Validate.notNull(filter);

		calculatePerSymbol(filter);
		calculatePerCurrency(filter);
	}

	public void calculateBySector(Filter filter) {
		Validate.notNull(filter);
		calculatePerSector(filter);
	}

	public void calculateByLabel(Filter filter) {
		Validate.notNull(filter);
		calculatePerLabel(filter);
	}

	public void calculateByYear(Filter filter, int startYear) {
		Validate.notNull(filter);
		calculatePerYear(filter, startYear);
	}

	private void calculatePerSector(Filter filter) {
		Validate.notNull(filter);

		sectorAnalysis = new ArrayList<SectorAnalysis>();

		for (SectorEnum sector : SectorEnum.retrieveSortedSectors()) {
			Collection<Transaction> trans = transactionsDao.getTransactionsBySector(sector, filter, false);
			if (!trans.isEmpty()) {
				Analysis analysis = createAnalysis(trans, filter);
				analysis.setSector(sector);
				if (!analysis.getQuantity().equals(BigDecimal.ZERO)) {
					sectorAnalysis.add(analysis);
				}
			}
		}
	}

	private void calculatePerYear(Filter filter, int startYear) {
		Validate.notNull(filter);

		yearAnalysis = new ArrayList<YearAnalysis>();

		if (filter.isDateFiltered()) {
			Collection<Transaction> trans = transactionsDao.getTransactions(filter, false);
			if (!trans.isEmpty()) {
				Analysis analysis = createAnalysis(trans, filter);
				analysis.setYear(filter.getYear());
				// before: if (analysis.getQuantity().floatValue() != 0F) {
				if (!analysis.getQuantity().equals(BigDecimal.ZERO)) {
					yearAnalysis.add(analysis);
				}
			}
		} else {
			while (startYear <= DateUtils.getCurrentYear()) {
				filter.setYear(startYear);
				Collection<Transaction> trans = transactionsDao.getTransactions(filter, false);
				if (!trans.isEmpty()) {
					Analysis analysis = createAnalysis(trans, filter);
					analysis.setYear(startYear);
					if (!analysis.getQuantity().equals(BigDecimal.ZERO)) {
						yearAnalysis.add(analysis);
					}
				}
				startYear++;
			}
			filter.setYear(null);
		}
	}

	private void calculatePerLabel(Filter filter) {
		Validate.notNull(filter);

		labelAnalysis = new ArrayList<LabelAnalysis>();

		for (Label label : labelsDao.get()) {
			if (!filter.getLabels().contains(label)) {
				Collection<Transaction> trans = transactionsDao.getTransactionsByLabel(label, filter, false);
				if (trans.size() > 0) {
					Analysis analysis = createAnalysis(trans, filter);
					analysis.setLabel(label);
					if (!analysis.getQuantity().equals(BigDecimal.ZERO)) {
						labelAnalysis.add(analysis);
					}
				}
			}
		}
	}

	private void calculatePerSymbol(Filter filter) {
		Validate.notNull(filter);

		symbolAnalysis = new ArrayList<SymbolAnalysis>();
		for (Symbol symbol : symbolsDao.get(filter)) {
			Collection<Transaction> trans = transactionsDao.getTransactionsBySymbol(symbol, filter, false);
			if (!trans.isEmpty()) {
				Analysis analysis = createAnalysis(trans, filter);
				analysis.setSymbol(symbol);
				if (!analysis.getQuantity().equals(BigDecimal.ZERO)) {
					symbolAnalysis.add(analysis);
				}
			}
		}
	}

	private Analysis createAnalysis(Collection<Transaction> trans, Filter filter) {
		Validate.notNull(trans);
		Validate.notEmpty(trans);
		Validate.notNull(filter);

		Analysis analysis = new AnalysisImpl();
		for (PerfOverviewStrategyEnum strategy : PerfOverviewStrategyEnum.values()) {
			strategy.getStrategy().process(analysis, trans, filter);
		}
		return analysis;
	}

	private void calculatePerCurrency(Filter filter) {
		Validate.notNull(filter);

		currencyAnalysis = new ArrayList<CurrencyAnalysis>();

		for (CurrencyEnum currency : CurrencyEnum.values()) {
			Collection<Transaction> trans = transactionsDao.getTransactionsByCurrency(currency, filter, false);
			if (!trans.isEmpty()) {
				Analysis analysis = createAnalysis(trans, filter);
				analysis.setCurrency(currency);
				if (!analysis.getQuantity().equals(BigDecimal.ZERO)) {
					currencyAnalysis.add(analysis);
				}
			}
		}
	}

	public Collection<SymbolAnalysis> getSymbolAnalysis() {
		return symbolAnalysis;
	}

	public Collection<CurrencyAnalysis> getCurrencyAnalysis() {
		return currencyAnalysis;
	}

	public Collection<SectorAnalysis> getSectorAnalysis() {
		return sectorAnalysis;
	}

	// private void calculatePerLabels(Filter filter) {
	// assert filter != null;
	//
	// if(filter.isLabelsFiltered()){
	// Collection<Transaction> transactions =
	// transactionsDao.getTransactions(filter, false);
	// for(Label label: filter.getLabels()){
	// Collection<Transaction> trans =
	// transactionsDao.getTransactionsByLabel(label);
	// trans = CollectionUtils.union(trans, transactions);
	// }
	// }
	// }

}