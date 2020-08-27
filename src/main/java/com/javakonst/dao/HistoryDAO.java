package com.javakonst.dao;

import com.javakonst.entity.History;
import com.sun.istack.Nullable;

import java.util.List;

public class HistoryDAO implements CRUDService<History> {
    @Override
    public void entityCreate(History entity, List<History> entityList) {
        entityList.add(entity);
    }

    @Override
    public void entityDelete(String secid, List<History> entityList) {
        entityList.removeIf(s -> s.getSecid().equals(secid));
    }

    @Override
    @Nullable
    public History entityRead(String secid, List<History> entityList) {
        if (entityList.isEmpty()) throw new RuntimeException("-> Error: input List is Empty.");
        for (History s : entityList) {
            if (s.getSecid().equals(secid)) return s;
        }
        return null;
    }

    @Override
    public void entityUpdate(History entity, List<History> entityList) {
        for (int i = 0; i < entityList.size(); i++) {
            if (entityList.get(i).equals(entity.getSecid())) {
                entityList.set(i, entity);
                break;
            }
        }
    }
}
