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
        Session session = HibernateConf.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        for (int i = 0; i < sList.size(); i++) {
            session.save(sList.get(i));
            if (i % 10 == 0) {
                session.flush();
                session.clear();
            }
        }
        int countSavedSecurities = sList.size();

        for (int i = 0; i < hList.size(); i++) {
            String secid = hList.get(i).getSecurity().getSecid();
            Security security = null;
            for (Security s : sList) {
                if (s.getSecid().equals(secid)) security = s;
            }
            if (security != null) {
                History history = hList.get(i);
                history.setSecurity(security);
                session.save(history);
                if (i % 10 == 0) {
                    session.flush();
                    session.clear();
                }
            }
        }
        transaction.commit();
        session.close();
        int countSavedHistory = hList.size();

        int[] a = new int[2];
        a[0] = countSavedSecurities;
        a[1] = countSavedHistory;

        return a;
    }

    @Override
    public String saveSecurity(Security s) {
        Session session = HibernateConf.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String underSecid = (String) session.save(s);
        transaction.commit();
        session.close();
        return underSecid;
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
        transaction.commit();
        session.close();
        //TODO: сделать запрос в moex на нужную ценную бумагу
        if (resultList.isEmpty()) return null;
        else {
            return resultList.get(0);
        }
    }
}
