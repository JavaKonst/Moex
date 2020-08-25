package com.javakonst.db;

import com.javakonst.entity.History;
import com.javakonst.entity.Security;
import com.javakonst.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class CRUDServiceDBImpl implements CRUDServiceDB {

    public void createSecurity(Security s) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(s);
        transaction.commit();
        session.close();
    }

    public List<Security> getListSecurity() {
        List<Security> securityList = new ArrayList<>();
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT * FROM secyrities;");
        List<Security> resultList = query.getResultList();

        session.close();

        return resultList;
    }

    public List<History> getHistoryBySecurity(Security s) {
        return null;
    }

}
