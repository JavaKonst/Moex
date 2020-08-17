package com.javakonst.dao;

import com.javakonst.entity.Entity;
import com.javakonst.entity.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecuritiesDAO implements CRUDService {
    private List<Security> securityList;

    //TODO: сделать проверку перед кастом List<Security> во всех методах

    @Override
    public List<? extends Entity> entityCreate(Entity entity, List<? extends Entity> entityList) {
        Security security = new Security();
        securityList = (List<Security>) entityList;
        if (entity instanceof Security) security = (Security) entity;
            else throw new RuntimeException("-> Error: incoming entity is not Security.class");
        validate (security);
        securityList.add(security);
        return entityList;
    }

    @Override
    public List<? extends Entity> entityDelete(String secid, List<? extends Entity> entityList) {
        securityList = (List<Security>) entityList;
        securityList.removeIf(s -> s.getSecid().equals(secid));
        return securityList;
    }

    @Override
    public Entity entityRead(String secid, List<? extends Entity> entityList) {
        securityList = (List<Security>) entityList;
        for (Security s : securityList) {
            if(s.getSecid().equals(secid)) return s;
        }
        return null;
    }

    @Override
    public List<? extends Entity> entityUpdate(Entity entity, List<? extends Entity> entityList) {
        securityList = (List<Security>) entityList;
        for (int i = 0; i < securityList.size(); i++) {
            if(securityList.get(i).equals(entity.getSecid())) {
                securityList.set(i, (Security) entity);
                return securityList;
            }
        }
        return null;
    }

    private void validate(Security security) {
        String pattern = "^[ а-яА-Я0-9]+$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(security.getName());
        if (!m.matches()) throw new RuntimeException("->Error: name of security incorrect. Name mast include cirilic, digit, whitespace.");
    }
    
}
