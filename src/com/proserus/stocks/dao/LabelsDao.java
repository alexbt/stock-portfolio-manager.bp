package com.proserus.stocks.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.proserus.stocks.model.common.ObservableModel;
import com.proserus.stocks.model.transactions.Label;

@Singleton
public class LabelsDao extends ObservableModel {
	@Inject
	private PersistenceManager persistenceManager;

	public LabelsDao() {
	}
	
	public Collection<Label> get() {
		EntityManager em  = persistenceManager.getEntityManager();
		Query query = em.createNamedQuery("label.findAll");
		return query.getResultList();
	}
	
	public Collection<Label> getSubLabels(Collection<Label> labels) {
		EntityManager em  = persistenceManager.getEntityManager();
		Query query = em.createNamedQuery("label.findSubLabels");
		query.setParameter("labels", labels);
		return query.getResultList();
	}
	
	public Label add(Label label){
		EntityManager em  = persistenceManager.getEntityManager();
		Query query = em.createNamedQuery("label.findByName");
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
		persistenceManager.remove(label);
	}
}
