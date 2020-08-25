package com.javakonst.db;

import com.javakonst.entity.Security;
import com.javakonst.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CRUDServiceDBSecurity implements CRUDServiceDB<Security> {

    @Override
    public void entityCreate(Security security) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(security);
        transaction.commit();
        session.close();
    }

    @Override
    public void entityDelete(Security security) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(security);
        transaction.commit();
        session.close();
    }

    @Override
    public Security entityReadById(int id) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Security newSecurity = session.get(Security.class, id);
        transaction.commit();
        session.close();
        return newSecurity;
    }

    @Override
    public void entityUpdate(Security security) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.update(security);
        transaction.commit();
        session.close();
    }
}
