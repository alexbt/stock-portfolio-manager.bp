package com.proserus.stocks.bp;

import java.util.Collection;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.proserus.stocks.dao.LabelsDao;
import com.proserus.stocks.model.transactions.Label;

@Singleton
public class LabelsBp {

	@Inject
	private LabelsDao labelsDao;
	
	public LabelsBp() {
	}
	
	public Collection<Label> get() {
		return labelsDao.get();
	}
	
	public Collection<Label> getSubLabels(Collection<Label> labels) {
		return labelsDao.getSubLabels(labels);
	}
	
	public Label add(Label label){
		labelsDao.add(label);
		return label;
	}

	public void remove(Label label) {
		labelsDao.remove(label);
	}
}
