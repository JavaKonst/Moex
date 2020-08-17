package com.javakonst.dao;

import com.javakonst.entity.Entity;

import java.util.List;

public interface CRUDService {
    List<? extends Entity> entityCreate(Entity entity, List<? extends Entity> entityList);
    List<? extends Entity> entityDelete(String secid, List<? extends Entity> entityList);
    Entity entityRead(String secid, List<? extends Entity> entityList);
    List<? extends Entity> entityUpdate(Entity entity, List<? extends Entity> entityList);
}
