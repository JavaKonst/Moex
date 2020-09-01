package com.javakonst.db;

import com.javakonst.entity.History;
import com.javakonst.entity.Security;

import java.util.List;

public interface DBService {
    int[] saveListsToDB(List<Security> sList, List<History> hList);
    String saveSecurity(Security security);
    List<Security> getAllSecurities();
    Security getSecurityBySecid(String secid);
    int deleteSecurityBySecid(String secid);
    int deleteHistoryBySecuritySecid(String secid);
    boolean updateSecurity(Security security);
    boolean updateHistory(History history);
}
