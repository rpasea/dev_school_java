package com.example.tweethibernate.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;

@Component
public class ProgrammaticTransactionManager {
    private static final Logger LOG = LoggerFactory.getLogger(ProgrammaticTransactionManager.class);
    public interface BusinessTransaction<T> {
        T execute(Session session);
    }

    private SessionFactory sessionFactory;

    public ProgrammaticTransactionManager(EntityManagerFactory entityManagerFactory) {
        this.sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
    }

    public <T> T doInTransaction(BusinessTransaction<T> businessTransaction) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            T result = businessTransaction.execute(session);
            tx.commit();
            return result;
        } catch (RuntimeException e) {
            LOG.error("Got excpetion in transaction", e);
            if (tx != null) {
                tx.rollback();
            }
            throw e;
        }
    }
}
