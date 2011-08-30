package com.proserus.stocks.dao;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class PersistenceManager {

	@Inject
	private EntityManager em;

	public Object persist(Object o) {
		//EntityManagerFactory emf  = Persistence.createEntityManagerFactory("jpaDemo");
		//EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		try{
			em.persist(o);
		}catch(EntityExistsException e){
			em.refresh(o);
		}
		
		tx.commit();
		//em.close();
		return o;
		//emf.close();
	}
	
	
	public void remove(Object o){
		//EntityManagerFactory emf  = Persistence.createEntityManagerFactory("jpaDemo");
		//EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		if(!tx.isActive()){
			tx.begin();
		}
		em.remove(o);
		tx.commit();
		//em.close();
		//emf.close();
	}
	
	public EntityManager getEntityManager(){
		if(em==null){
			try{
				em = Persistence.createEntityManagerFactory("jpaDemo").createEntityManager();
			} catch (Throwable e) {
				//ignore
			}
		}
		return em;
	}
	
	public void close(){
		em.close();
	}
	
	
}
