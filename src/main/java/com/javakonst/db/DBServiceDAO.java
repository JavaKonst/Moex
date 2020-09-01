package com.javakonst.db;

import com.javakonst.entity.History;
import com.javakonst.entity.Security;
import com.javakonst.utils.HibernateConf;
import com.sun.istack.Nullable;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DBServiceDAO implements DBService {

    @Override
    public int[] saveListsToDB(List<Security> sList, List<History> hList) {
        int[] countOfSavedData = {0, 0};
        int countSavedSecurities = 0;
        int countSavedHistory = 0;

        Session session = HibernateConf.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        if (sList != null && !sList.isEmpty()) {
            for (int i = 0; i < sList.size(); i++) {
                session.save(sList.get(i));
                if (i % 10 == 0) {
                    session.flush();
                    session.clear();
                }
            }
            countSavedSecurities = sList.size();
        }

        if (hList != null && !hList.isEmpty() && sList != null && !sList.isEmpty()) {
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
            countSavedHistory = hList.size();
        }

        transaction.commit();
        session.close();

        countOfSavedData[0] = countSavedSecurities;
        countOfSavedData[1] = countSavedHistory;

        return countOfSavedData;
    }

    @Override
    @Nullable
    public String saveSecurity(Security security) {
        if (security == null || !validate(security)) return null;
        Session session = HibernateConf.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String underSecid = (String) session.save(security);
        transaction.commit();
        session.close();
        return underSecid;
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
    @Nullable
    public Security getSecurityBySecid(String secid) {
        if (secid == null || secid.isEmpty()) return null;
        Session session = HibernateConf.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Security WHERE secid = :a");
        query.setParameter("a", secid);
        List<Security> resultList = query.getResultList();
        transaction.commit();
        session.close();
        if (resultList.isEmpty()) return null;
            else return resultList.get(0);
    }

    @Override
    public int deleteSecurityBySecid(String secid) {
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
    public int deleteHistoryBySecuritySecid(String secid) {
        if (secid == null || secid.isEmpty()) return 0;
        Session session = HibernateConf.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("DELETE History WHERE secid = :a");
        query.setParameter("a", secid);
        int deletedHustory = query.executeUpdate();

        transaction.commit();
        session.close();

        return deletedHustory;
    }

    @Override
    public boolean updateSecurity(Security security) {
        if (security == null || !validate(security)) return false;
        Session session = HibernateConf.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.update(security);
        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean updateHistory(History history) {
        if (history == null || history.getSecurity() == null) return false;
        Session session = HibernateConf.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        session.update(history);
        transaction.commit();
        session.close();

        return true;
    }

    private boolean validate(Security security) {
        String pattern = "^[ а-яА-Я0-9]+$"; //Поле NAME должно содержать символы кириллицы/цифры/пробелы
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(security.getName());
        return m.matches();
    }
}
