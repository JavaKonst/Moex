package com.javakonst;

import com.javakonst.dao.HistoryDAO;
import com.javakonst.dao.SecuritiesDAO;
import com.javakonst.entity.History;
import com.javakonst.entity.Security;
import com.javakonst.utils.ListUtils;

import java.util.List;

public class ServiceAPI {
    private List<Security> listSecuritires;
    private List<History> listHistory;

    public ServiceAPI(String filepath_securities, String filepath_history) {
        ListUtils listUtils = new ListUtils();
        listSecuritires = listUtils.getListSecuritires(filepath_securities);
        listHistory = listUtils.getListHistory(filepath_history);
    }

    public List<Security> listCreateSecurity(Security security) {
        SecuritiesDAO securitiesDAO = new SecuritiesDAO();
        securitiesDAO.entityCreate(security, listSecuritires);
        return listSecuritires;
    }

    public List<History> listCreateHistory(History history) {
        HistoryDAO historyDAO = new HistoryDAO();
        historyDAO.entityCreate(history, listHistory);
        return listHistory;
    }

}
