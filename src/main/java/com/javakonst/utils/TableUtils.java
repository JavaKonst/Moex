package com.javakonst.utils;

import com.javakonst.dao.CRUDService;
import com.javakonst.dao.HistoryDAO;
import com.javakonst.entity.History;
import com.javakonst.entity.Security;
import com.javakonst.xmlparsers.XMLProcStAX_v2;
import com.javakonst.xmlparsers.XMLProcessor_v2;
import dnl.utils.text.table.TextTable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TableUtils {
    private List<Security> securityList;
    private List<History> historyList;

    public List<Security> getListSecuritires(String pathSec) {
        XMLProcessor_v2 xmlPS2 = new XMLProcStAX_v2();
        return securityList = xmlPS2.dataFromXML(pathSec, new Security());
    }

    public List<History> getListHistory(String pathHis) {
        XMLProcessor_v2 xmlPH2 = new XMLProcStAX_v2();
        return historyList = xmlPH2.dataFromXML(pathHis, new History());
    }

    public void printTable(String pathSec, String pathHis, SortBy sort, String filter) {
        if (securityList == null) getListSecuritires(pathSec);
        if (historyList == null) getListHistory(pathHis);

        String[] columnNames = {"SECID ", "REGNUMBER ", "NAME ", "EMITENT_TITLE ", "TRADEDATE ", "NUMTRADES ", "OPEN ", "CLOSE "};

        //TODO: сделать возможность сортировки и фильтрации

        Object[][] data = new Object[securityList.size()][8];
        CRUDService<History> historyDAO = new HistoryDAO();
        int i = 0;
        for (Security security : securityList) {
            Object[] newRow = new Object[8];
            String secid = security.getSecid();
            newRow[0] = secid;
            newRow[1] = security.getRegnumber();
            newRow[2] = security.getName();
            newRow[3] = security.getEmitent_title();

            if (historyDAO.entityRead(secid, historyList) != null) {
                Date date = historyDAO.entityRead(secid, historyList).getTradedate();
                newRow[4] = new SimpleDateFormat("yyyy-MM-dd").format(date);
                newRow[5] = historyDAO.entityRead(secid, historyList).getNumtrades();
                newRow[6] = historyDAO.entityRead(secid, historyList).getOpen();
                newRow[7] = historyDAO.entityRead(secid, historyList).getClose();
            } else {
                newRow[4] = "-";
                newRow[5] = "-";
                newRow[6] = "-";
                newRow[7] = "-";
            }
            data[i] = newRow;
            i++;
        }

        TextTable tt = new TextTable(columnNames, data);
        tt.setSort(sort.getValue());
        tt.printTable();
    }

}
