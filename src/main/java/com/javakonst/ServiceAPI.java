package com.javakonst;

import com.javakonst.dao.HistoryDAO;
import com.javakonst.dao.SecuritiesDAO;
import com.javakonst.db.DBService;
import com.javakonst.db.DBServiceDAO;
import com.javakonst.entity.History;
import com.javakonst.entity.Security;
import com.javakonst.utils.ListUtils;

import java.util.List;

public class ServiceAPI {
    private final List<Security> listSecuritires;
    private final List<History> listHistory;
    
    public ServiceAPI(String filepath_securities, String filepath_history) {
        super();
        ListUtils listUtils = new ListUtils();
        listSecuritires = listUtils.getListSecuritires(filepath_securities);
        listHistory = listUtils.getListHistory(filepath_history);
    }
    
    public List<Security> listGetAllSecuritires() {
        return listSecuritires;
    }
    
    public Security listGetSecurityBySecid(String secid) {
        SecuritiesDAO securitiesDAO = new SecuritiesDAO();
        return securitiesDAO.entityRead(secid, listSecuritires);
    }
    
    public List<Security> listSaveSecurity(Security security) {
        SecuritiesDAO securitiesDAO = new SecuritiesDAO();
        securitiesDAO.entityCreate(security, listSecuritires);
        return listSecuritires;
    }
    
    public List<Security> listUpdateSecurity(Security security) {
        SecuritiesDAO securitiesDAO = new SecuritiesDAO();
        securitiesDAO.entityUpdate(security, listSecuritires);
        return listSecuritires;
    }
    
    public List<Security> listDeleteSecurityBySecid(String secid) {
        SecuritiesDAO securitiesDAO = new SecuritiesDAO();
        securitiesDAO.entityDelete(secid, listSecuritires);
        return listSecuritires;
    }
    
    public List<History> listGetAllHistory() {
        return listHistory;
    }
    
    public History listGetHistoryBySecid(String secid) {
        HistoryDAO historyDAO = new HistoryDAO();
        return historyDAO.entityRead(secid, listHistory);
    }
    
    public List<History> listSaveHistory(History history) {
        HistoryDAO historyDAO = new HistoryDAO();
        historyDAO.entityCreate(history, listHistory);
        return listHistory;
    }
    
    public List<History> listUpdateHistory(History history) {
        HistoryDAO historyDAO = new HistoryDAO();
        historyDAO.entityUpdate(history, listHistory);
        return listHistory;
    }
    
    public List<History> listDeleteHistoryBySecid(String secid) {
        HistoryDAO historyDAO = new HistoryDAO();
        historyDAO.entityDelete(secid, listHistory);
        return listHistory;
    }
    
    public void dbCreateDB() {
        DBService dbService = new DBServiceDAO();
        dbService.saveListsToDB(listSecuritires, listHistory);
    }
    
    public String dbSaveSecurity(Security security) {
        DBService dbService = new DBServiceDAO();
        return dbService.saveSecurity(security);
    }
    
    public List<Security> dbGetAllSecurity() {
        DBService dbService = new DBServiceDAO();
        return dbService.getAllSecurities();
    }
    
    public Security dbGetSecurityBySecid(String secid) {
        DBService dbService = new DBServiceDAO();
        return dbService.getSecurityBySecid(secid);
    }
    
    public int dbDeleteSecurityBySecid(String secid) {
        DBService dbService = new DBServiceDAO();
        return dbService.deleteSecurityBySecid(secid);
    }
    
    public int dbDeleteHistoryBySecid(String secid) {
        DBService dbService = new DBServiceDAO();
        return dbService.deleteHistoryBySecuritySecid(secid);
    }
    
    public void dbUpdateSecurity(Security security) {
        DBService dbService = new DBServiceDAO();
        dbService.updateSecurity(security);
    }
    
    public void dbUpdateHistory(History history) {
        DBService dbService = new DBServiceDAO();
        dbService.updateHistory(history);
    }
}
