package com.javakonst.dao;

import com.javakonst.entity.Entity;

import java.util.List;

public interface CRUDService<T extends Entity> {

    void entityCreate(T entity, List<T> entityList);

    void entityDelete(String secid, List<T> entityList);

    T entityRead(String secid, List<T> entityList);

    void entityUpdate(T entity, List<T> entityList);
}
