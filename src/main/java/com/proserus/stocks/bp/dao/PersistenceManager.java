package com.proserus.stocks.bp.dao;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.commons.lang3.Validate;
import org.apache.log4j.Logger;

import com.google.inject.Singleton;
import com.proserus.stocks.bo.common.DatabasePaths;
import com.proserus.stocks.bp.events.Event;
import com.proserus.stocks.bp.events.EventBus;
import com.proserus.stocks.bp.events.EventListener;
import com.proserus.stocks.bp.events.ModelChangeEvents;

@Singleton
public class PersistenceManager implements EventListener {

    private static final String SHUTDOWN_COMMAND = "SHUTDOWN";

    private static final String YYYY_M_MDDHHMMSS = "yyyyMMddhhmmss";

    private static final String SHUTDOWN_TRUE = ";shutdown=true;";

    private static final String JDBC_HSQLDB_FILE = "jdbc:hsqldb:file:";

    private static final String HIBERNATE_CONNECTION_URL = "hibernate.connection.url";

    private static final String OLD = ".old.";

    private static final String PROPERTIES = ".properties";

    private static final String SCRIPT = ".script";

    private EntityManager em;

    private DatabasePaths dbPath;

    private static final Logger LOGGER = Logger.getLogger(PersistenceManager.class.getName());

    public PersistenceManager() {
        EventBus.getInstance().add(this, ModelChangeEvents.DATABASE_SELECTED);
        EventBus.getInstance().add(this, ModelChangeEvents.CURRENT_DATABASE_DELETED);
    }

    public Object persist(Object o) {
        Validate.notNull(o);

        EntityTransaction tx = em.getTransaction();
        boolean wasAlreadyActive = false;
        if (tx.isActive()) {
            wasAlreadyActive = true;
        } else {
            tx.begin();
        }

        try {
            em.persist(o);
        } catch (EntityExistsException e) {
            em.refresh(o);
        }

        if (!wasAlreadyActive) {
            tx.commit();
        }

        return o;
    }

    public void remove(Object o) {
        Validate.notNull(o);

        EntityTransaction tx = em.getTransaction();
        boolean wasAlreadyActive = false;
        if (tx.isActive()) {
            wasAlreadyActive = true;
        } else {
            tx.begin();
        }
        em.remove(o);

        if (!wasAlreadyActive) {
            tx.commit();
        }
    }

    public EntityTransaction getTransaction() {
        return em.getTransaction();
    }

    public Query createNamedQuery(String nameQuery) {
        return em.createNamedQuery(nameQuery);
    }

    public void close() {
        try {
            if (em.getTransaction().isActive()) {
                assert false : "Should never be here... but just in case";
                if (em.getTransaction().getRollbackOnly()) {
                    em.getTransaction().rollback();
                } else {
                    em.getTransaction().commit();
                }
            }
            em.getTransaction().begin();

            em.createNativeQuery(SHUTDOWN_COMMAND).executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Event event, Object model) {
        if (ModelChangeEvents.DATABASE_SELECTED.equals(event)) {
            dbPath = ModelChangeEvents.DATABASE_SELECTED.resolveModel(model);

            em = createEntityManager();
        } else if (ModelChangeEvents.CURRENT_DATABASE_DELETED.equals(event)) {
            String ts = new SimpleDateFormat(YYYY_M_MDDHHMMSS).format(Calendar.getInstance().getTime());
            File script = new File(dbPath.getSelectedDatabase().getPath() + SCRIPT);
            File properties = new File(dbPath.getSelectedDatabase().getPath() + PROPERTIES);
            LOGGER.info("Removing " + script.getAbsoluteFile());
            script.renameTo(new File(dbPath.getSelectedDatabase().getPath() + OLD + ts + SCRIPT));
            LOGGER.info("Removing " + properties.getAbsoluteFile());
            properties.renameTo(new File(dbPath.getSelectedDatabase().getPath() + OLD + ts + PROPERTIES));
        }
    }

    public EntityManager createEntityManager() {
        Properties p = new Properties();
        p.setProperty(HIBERNATE_CONNECTION_URL, JDBC_HSQLDB_FILE + dbPath.getSelectedDatabase().getPath() + SHUTDOWN_TRUE);
        EntityManagerFactory fact = Persistence.createEntityManagerFactory("stockPortfolioJpa", p);
        em = fact.createEntityManager();
        return em;
    }

    public Query createQuery(String query) {
        return em.createQuery(query);
    }

    public Query createNativeQuery(String query) {
        return em.createNativeQuery(query);
    }

}
