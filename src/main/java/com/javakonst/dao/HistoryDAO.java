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
        entityList.removeIf(s -> s.getSecurity().getSecid().equals(secid));
    }

    @Override
    @Nullable
    public History entityRead(String secid, List<History> entityList) {
        if (entityList==null || entityList.isEmpty()) return null;
        for (History s : entityList) {
            if (s.getSecurity().getSecid().equals(secid)) return s;
        }
        return null;
    }

    @Override
    public void entityUpdate(History entity, List<History> entityList) {
        if (entityList==null || entityList.isEmpty() || entity==null) return;
        for (int i = 0; i < entityList.size(); i++) {
            String curHistorySecid = entityList.get(i).getSecurity().getSecid();
            String newHistorySecid = entity.getSecurity().getSecid();
            if (curHistorySecid.equals(newHistorySecid)) {
                entityList.set(i, entity);
                break;
            }
        }
    }
}
