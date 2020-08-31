package com.javakonst.db;

import com.javakonst.entity.History;
import com.javakonst.entity.Security;

import java.util.List;

public interface DBService {
    int[] saveListsToDB(List<Security> sList, List<History> hList);

    String saveSecurity(Security s);

    int deleteSecurity(String secid);

    List<Security> getAllSecurities();

    Security getSecurityBySecid(String secid);
}
