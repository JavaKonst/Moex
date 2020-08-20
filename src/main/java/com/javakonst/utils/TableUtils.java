package com.javakonst.utils;

import com.javakonst.dao.CRUDService;
import com.javakonst.dao.HistoryDAO;
import com.javakonst.entity.History;
import com.javakonst.entity.Security;
import com.javakonst.xmlparsers.XMLProcStAX;
import com.javakonst.xmlparsers.XMLProcessor;
import dnl.utils.text.table.TextTable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TableUtils {
    private final String[] columnNames = {"SECID ", "REGNUMBER ", "NAME ", "EMITENT_TITLE ", "TRADEDATE ", "NUMTRADES ", "OPEN ", "CLOSE "};
    private List<Security> securityList;
    private List<History> historyList;

    public List<Security> getListSecuritires(String pathSec) {
        if (pathSec == null || pathSec.isEmpty()) throw new RuntimeException("->Error: Incorrect path of xml-file.");
        XMLProcessor xmlPS2 = new XMLProcStAX();
        return securityList = xmlPS2.dataFromXML(pathSec, new Security());
    }

    public List<History> getListHistory(String pathHis) {
        if (pathHis == null || pathHis.isEmpty()) throw new RuntimeException("->Error: Incorrect path of xml-file.");
        XMLProcessor xmlPH2 = new XMLProcStAX();
        return historyList = xmlPH2.dataFromXML(pathHis, new History());
    }

    public void printTable(String pathSec, String pathHis, SortBy sort, String filter) {
        if (securityList == null) getListSecuritires(pathSec);
        if (historyList == null) getListHistory(pathHis);
        String field = "";
        String fieldvalue = "";

        if (filter != null && !filter.isEmpty()) {
            String[] inputfilter = filter.split("=");
            if (inputfilter.length != 2) throw new RuntimeException("-> Error: Incorrect filter");
            field = inputfilter[0].toUpperCase().trim();
            fieldvalue = inputfilter[1].toLowerCase();
        }

        List<Object[]> x = new ArrayList<>();
        CRUDService<History> historyDAO = new HistoryDAO();
        for (Security security : securityList) {
            Object[] newRow = new Object[8];
            String secid = security.getSecid();
            newRow[0] = secid;
            newRow[1] = security.getRegnumber();
            newRow[2] = security.getName();
            newRow[3] = security.getEmitent_title();

            if (historyDAO.entityRead(secid, historyList) != null) {
                Date date = historyDAO.entityRead(secid, historyList).getTradedate();
                newRow[4] = new SimpleDateFormat("yyyy.MM.dd").format(date);
                newRow[5] = historyDAO.entityRead(secid, historyList).getNumtrades();
                newRow[6] = historyDAO.entityRead(secid, historyList).getOpen();
                newRow[7] = historyDAO.entityRead(secid, historyList).getClose();
            } else {
                newRow[4] = "-";
                newRow[5] = "-";
                newRow[6] = "-";
                newRow[7] = "-";
            }

            if (field.isEmpty()) x.add(newRow);
            else {
                if (field.equals("EMITENT_TITLE") && newRow[3].toString().toLowerCase().contains(fieldvalue))
                    x.add(newRow);
                if (field.equals("TRADEDATE") && newRow[4].toString().toLowerCase().contains(fieldvalue)) x.add(newRow);
            }
        }

        Object[][] data = new Object[x.size()][8];
        for (int i = 0; i < x.size(); i++) {
            data[i] = x.get(i);
        }

        TextTable tt = new TextTable(columnNames, data);
        if (sort != null) tt.setSort(sort.getValue());
        tt.printTable();
    }

}
