package com.javakonst.db;

import com.javakonst.entity.Entity;
import com.javakonst.utils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ListToDBPostgreSQL implements ListToDB {

    public void uploadListToDB(List<Entity> entityList) {
        Session session = HibernateUtils.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        for (Entity entity : entityList) {
            session.save(entity);
            transaction.commit();
        }

        session.close();
    }



}
