package com.proserus.stocks.bp.businesslogic;

import java.util.Collection;

import org.apache.commons.lang3.Validate;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.proserus.stocks.bo.transactions.Label;
import com.proserus.stocks.bp.dao.LabelsDao;

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
		Validate.notNull(labels);
		Validate.notEmpty(labels);		
		return labelsDao.getSubLabels(labels);
	}
	
	public Label add(Label label){
		Validate.notNull(label);
		label = labelsDao.add(label);
		return label;
	}

	public void remove(Label label) {
		Validate.notNull(label);
		labelsDao.remove(label);
	}
}
