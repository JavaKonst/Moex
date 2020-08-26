package com.javakonst.db;

import com.javakonst.entity.History;
import com.javakonst.entity.Security;

import java.util.List;

public interface DBService {
    void saveListsToDB(List<Security> sList, List<History> hList);

    void saveSecurity(Security s);

    void deleteSecurity(String secid);

    List<Security> getAllSecurities();

    Security getSecurityBySecid(String secid);
}
