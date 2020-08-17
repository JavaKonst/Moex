package com.javakonst.dao;

import com.javakonst.entity.Entity;
import com.javakonst.entity.Security;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SecuritiesDAO implements CRUDService {
    private Security security = new Security();
    private List<Security> securityList = new ArrayList<>();
    
    @Override
    public List<? extends Entity> entityCreate(Entity entity, List<Entity> entityList) {
        if (entity instanceof Security) security = (Security) entity;
            else throw new RuntimeException("-> Error: incoming entity is not Security.class");
        validate (security);
        entityList.add(security);
        return entityList;
    }
    
    private void validate(Security security) {
        String pattern = "^[ а-яА-Я0-9]+$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(security.getName());
        if (!m.matches()) throw new RuntimeException("->Error: name of security incorrect. Name mast include cirilic, digit, whitespace.");
    }
    
    @Override
    public List<? extends Entity> entityDelete(Entity entity) {
        return null;
    }
    
    @Override
    public List<? extends Entity> entityRead(Entity entity) {
        return null;
    }
    
    @Override
    public List<? extends Entity> entityUpdate(Entity entity) {
        return null;
    }
}
