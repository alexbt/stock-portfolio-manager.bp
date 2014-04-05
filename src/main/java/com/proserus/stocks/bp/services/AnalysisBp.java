package com.proserus.stocks.bp.services;

import java.util.ArrayList;
import java.util.Collection;

import org.jfree.data.time.Year;

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
import com.proserus.stocks.bp.utils.DateUtil;

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
		assert filter != null;

		calculatePerSymbol(filter);
		calculatePerCurrency(filter);
		// calculatePerLabels(filter);
	}

	public void calculateBySector(Filter filter) {
		assert filter != null;
		calculatePerSector(filter);
	}

	public void calculateByLabel(Filter filter) {
		assert filter != null;
		calculatePerLabel(filter);
	}

	public void calculateByYear(Filter filter, Year startYear) {
		assert filter != null;
		calculatePerYear(filter, startYear);
	}

	private void calculatePerSector(Filter filter) {
		assert filter != null;

		sectorAnalysis = new ArrayList<SectorAnalysis>();

		for (SectorEnum sector : SectorEnum.retrieveSortedSectors()) {
			Collection<Transaction> trans = transactionsDao.getTransactionsBySector(sector, filter, false);
			if (trans.size() > 0) {
				Analysis analysis = createAnalysis(trans, filter);
				analysis.setSector(sector);
				if (analysis.getQuantity().floatValue() != 0F) {
					sectorAnalysis.add(analysis);
				}
			}
		}
	}

	private void calculatePerYear(Filter filter, Year startYear) {
		assert filter != null;

		yearAnalysis = new ArrayList<YearAnalysis>();

		if (filter.isDateFiltered()) {
			Collection<Transaction> trans = transactionsDao.getTransactions(filter, false);
			if (trans.size() > 0) {
				Analysis analysis = createAnalysis(trans, filter);
				analysis.setYear(filter.getYear().getYear());
				if (analysis.getQuantity().floatValue() != 0F) {
					yearAnalysis.add(analysis);
				}
			}
		} else {
			while (startYear.getYear() <= DateUtil.getCurrentYear().getYear()) {
				filter.setYear(startYear);
				Collection<Transaction> trans = transactionsDao.getTransactions(filter, false);
				if (trans.size() > 0) {
					Analysis analysis = createAnalysis(trans, filter);
					analysis.setYear(startYear.getYear());
					if (analysis.getQuantity().floatValue() != 0F) {
						yearAnalysis.add(analysis);
					}
				}
				startYear = (Year) startYear.next();
			}
			filter.setYear(null);
		}
	}

	private void calculatePerLabel(Filter filter) {
		assert filter != null;

		labelAnalysis = new ArrayList<LabelAnalysis>();

		for (Label label : labelsDao.get()) {
			if (!filter.getLabels().contains(label)) {
				Collection<Transaction> trans = transactionsDao.getTransactionsByLabel(label, filter, false);
				if (trans.size() > 0) {
					Analysis analysis = createAnalysis(trans, filter);
					analysis.setLabel(label);
					if (analysis.getQuantity().floatValue() != 0F) {
						labelAnalysis.add(analysis);
					}
				}
			}
		}
	}

	private void calculatePerSymbol(Filter filter) {
		assert filter != null;

		symbolAnalysis = new ArrayList<SymbolAnalysis>();
		for (Symbol symbol : symbolsDao.get(filter)) {
			Collection<Transaction> trans = transactionsDao.getTransactionsBySymbol(symbol, filter, false);
			if (trans.size() > 0) {
				Analysis analysis = createAnalysis(trans, filter);
				analysis.setSymbol(symbol);
				if (analysis.getQuantity().floatValue() != 0F) {
					symbolAnalysis.add(analysis);
				}
			}
		}
	}

	private Analysis createAnalysis(Collection<Transaction> trans, Filter filter) {
		assert trans != null;
		assert filter != null;

		Analysis analysis = new AnalysisImpl();
		for (PerfOverviewStrategyEnum strategy : PerfOverviewStrategyEnum.values()) {
			strategy.getStrategy().process(analysis, trans, filter);
		}
		return analysis;
	}

	private void calculatePerCurrency(Filter filter) {
		assert filter != null;

		currencyAnalysis = new ArrayList<CurrencyAnalysis>();

		for (CurrencyEnum currency : CurrencyEnum.values()) {
			Collection<Transaction> trans = transactionsDao.getTransactionsByCurrency(currency, filter, false);
			if (!trans.isEmpty()) {
				Analysis analysis = createAnalysis(trans, filter);
				analysis.setCurrency(currency);
				if (analysis.getQuantity().floatValue() != 0F) {
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
	// Collection<Transaction> transactions = transactionsDao.getTransactions(filter, false);
	// for(Label label: filter.getLabels()){
	// Collection<Transaction> trans = transactionsDao.getTransactionsByLabel(label);
	// trans = CollectionUtils.union(trans, transactions);
	// }
	// }
	// }

}