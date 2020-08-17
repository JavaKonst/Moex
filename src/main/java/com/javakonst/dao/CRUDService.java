package com.javakonst.dao;

import com.javakonst.entity.Entity;

import java.util.List;

public interface CRUDService {
    List<? extends Entity> entityCreate(Entity entity, List<Entity> entityList);
    List<? extends Entity> entityDelete(Entity entity);
    List<? extends Entity> entityRead(Entity entity);
    List<? extends Entity> entityUpdate(Entity entity);
}
