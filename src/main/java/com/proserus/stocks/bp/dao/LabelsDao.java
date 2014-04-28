package com.proserus.stocks.bp.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.Validate;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.proserus.stocks.bo.transactions.Label;

@Singleton
public class LabelsDao {
	@Inject
	private PersistenceManager persistenceManager;

	public LabelsDao() {
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Label> get() {
		Query query = persistenceManager.createNamedQuery("label.findAll");
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Label> getSubLabels(Collection<Label> labels) {
		Validate.notNull(labels);
		Validate.notEmpty(labels);
		
		Query query = persistenceManager.createNamedQuery("label.findSubLabels");
		query.setParameter("labels", labels);
		return query.getResultList();
	}
	
	public Label add(Label label){
		Validate.notNull(label);
		
		Query query = persistenceManager.createNamedQuery("label.findByName");
		query.setParameter("label", label.getName());
		try{
		Label l = (Label)query.getSingleResult();
		label = l;
		}catch(javax.persistence.NoResultException e){
			label = (Label)persistenceManager.persist(label);
		}
		
		return label;
	}

	public void remove(Label label) {
		Validate.notNull(label);
		
		persistenceManager.remove(label);
	}
}
