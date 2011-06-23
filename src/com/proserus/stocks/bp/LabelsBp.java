package com.proserus.stocks.bp;

import java.util.Collection;

import javax.persistence.Embeddable;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Transient;

import com.proserus.stocks.dao.PersistenceManager;
import com.proserus.stocks.model.common.ObservableModel;
import com.proserus.stocks.model.transactions.Label;
import com.proserus.stocks.model.transactions.Transaction;

@Embeddable
public class LabelsBp extends ObservableModel {
	private static LabelsBp singleton = new LabelsBp();

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Integer id;
	
	@Transient
	private EntityManager em;
	
	public LabelsBp() {
		em = PersistenceManager.getEntityManager();
	}
	
	public static LabelsBp getInstance() {
    	return singleton;
    }

	public Collection<Label> get() {
		EntityManager em  = PersistenceManager.getEntityManager();
		Query query = em.createNamedQuery("label.findAll");
		return query.getResultList();
	}
	
	public Collection<Label> getByTransactions(Transaction t) {
		EntityManager em  = PersistenceManager.getEntityManager();
		Query query = em.createNamedQuery("label.findAllByTransaction");
		query.setParameter("transactionId", t.getId());
		return query.getResultList();
	}
	public Label add(Label label){
		EntityManager em  = PersistenceManager.getEntityManager();
		Query query = em.createNamedQuery("label.findByName");
		query.setParameter("label", label.getName());
		try{
		Label l = (Label)query.getSingleResult();
		label = l;
		}catch(javax.persistence.NoResultException e){
			label = (Label)PersistenceManager.persist(label);
		}
		
		setChanged();
		notifyObservers();
		return label;
	}

	public void remove(Label label) {
		PersistenceManager.remove(label);
		setChanged();
		notifyObservers();
	}
}
