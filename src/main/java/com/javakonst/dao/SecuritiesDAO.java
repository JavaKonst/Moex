package com.javakonst.dao;

import com.javakonst.entity.Security;
import com.sun.istack.internal.Nullable;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecuritiesDAO implements CRUDService<Security> {

    @Override
    public void entityCreate(Security entity, List<Security> entityList) {
        validate(entity);
        entityList.add(entity);
    }

    @Override
    public void entityDelete(String secid, List<Security> entityList) {
        entityList.removeIf(s -> s.getSecid().equals(secid));
    }

    @Override
    @Nullable
    public Security entityRead(String secid, List<Security> entityList) {
        if (entityList.isEmpty()) throw new RuntimeException("-> Error: input List is Empty.");
        for (Security s : entityList) {
            if (s.getSecid().equals(secid)) return s;
        }
        return null;
    }

    @Override
    public void entityUpdate(Security entity, List<Security> entityList) {
        for (int i = 0; i < entityList.size(); i++) {
            if (entityList.get(i).getSecid().equals(entity.getSecid())) {
                entityList.set(i, entity);
                break;
            }
        }
    }

    private void validate(Security security) {
        String pattern = "^[ а-яА-Я0-9]+$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(security.getName());
        if (!m.matches())
            throw new RuntimeException("->Error: name of security incorrect. Name mast include cirilic, digit, whitespace.");
    }

}
