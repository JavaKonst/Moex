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
    public int[] saveListsToDB(List<Security> sList, List<History> hList) {

        //TODO: сделать сохранение пачкой
        for (Security s : sList) {
            Session session = HibernateConf.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(s);
            transaction.commit();
            session.close();
        }

        int countSavedSecurities = sList.size();

        for (History h : hList) {
            Session session = HibernateConf.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(h);
            transaction.commit();
            session.close();
        }

        int countSavedHistory = hList.size();

        int[] a = new int[2];
        a[0] = countSavedSecurities;
        a[1] = countSavedHistory;

        return a;
    }

    @Override
    public int saveSecurity(Security s) {
        Session session = HibernateConf.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        int underId = (int) session.save(s);
        transaction.commit();
        session.close();
        return underId;
    }

    @Override
    public int deleteSecurity(String secid) {
        if (secid.trim().isEmpty()) return 0;
        Security currentSecurity = getSecurityBySecid(secid);
        if (currentSecurity != null) {
            Session session = HibernateConf.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.delete(currentSecurity);
            transaction.commit();
            session.close();
            return 1;
        }
        return 0;
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
