package com.javakonst.db;

import com.javakonst.entity.History;
import com.javakonst.entity.Security;
import com.javakonst.utils.HibernateConf;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class DBServiceDAO implements DBService {

    @Override
    public void saveListsToDB(List<Security> sList, List<History> hList) {
        Session session = HibernateConf.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        for (Security s : sList) {
            session.save(s);
            transaction.commit();
        }

        for (History h : hList) {
            session.save(h);
            transaction.commit();
        }

        session.close();
    }

    @Override
    public void saveSecurity(Security s) {
        Session session = HibernateConf.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(s);
        transaction.commit();
        session.close();
    }

    @Override
    public void deleteSecurity(String secid) {
        if (secid.trim().isEmpty()) return;
        Security currentSecurity = getSecurityBySecid(secid);
        if (currentSecurity != null) {
            Session session = HibernateConf.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(currentSecurity);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public List<Security> getAllSecurities() {
        Session session = HibernateConf.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Security");
        List<Security> securityList = query.getResultList();
        transaction.commit();
        session.close();
        return securityList;
    }

    @Override
    public Security getSecurityBySecid(String secid) {
        Session session = HibernateConf.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Security WHERE secid = :a");
        query.setParameter("a", secid);
        List<Security> resultList = query.getResultList();
        //TODO: сделать запрос в moex на нужную ценную бумагу
        if (resultList.isEmpty()) return null;
        else {
            return resultList.get(0);
        }
    }
}
