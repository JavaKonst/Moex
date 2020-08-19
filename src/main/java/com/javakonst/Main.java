package com.javakonst;

import com.javakonst.core.*;
import com.javakonst.dao.*;
import com.javakonst.entity.*;

import java.util.Date;
import java.util.List;

public class Main {
    private static String file_securities = "securitiesShort.xml";
    private static String file_history = "history.xml";
    
    public static void main(String[] args) {
        XMLProcessor xmlPS = new XMLProcStAX(new Security());
        XMLProcessor xmlPH = new XMLProcStAX(new History());

        List<Security> securities = xmlPS.dataFromXml(file_securities);
        List<History> histories = xmlPH.dataFromXml(file_history);

//        testReadXML(securities, histories);
//        testCRUD (securities, histories);

    }

    private static void testCRUD(List<Security> securities, List<History> histories) {
        //тест securityCreate
        System.out.println("Tест securityCreate");
        CRUDService<Security> s = new SecuritiesDAO();
        Security securityNew = new Security();
        securityNew.setEmitent_title("ПАО");
        securityNew.setName("Какоето имя");
        securityNew.setRegnumber("regnumber");
        securityNew.setSecid("BBBB");
        s.entityCreate(securityNew,securities);
        //тест historyCreate
        System.out.println("Tест historyCreate");
        CRUDService<History> h = new HistoryDAO();
        History historyNew = new History();
        historyNew.setSecid("CCCC");
        historyNew.setClose(99.9);
        historyNew.setOpen(222.2);
        historyNew.setNumtrades(66.6);
        historyNew.setTradedate(new Date());
        h.entityCreate(historyNew,histories);

        //тест securityRead
        System.out.print("1-Tест securityRead: \t");
        System.out.println("-"+s.entityRead("BBBB", securities).toString());
        //тест historyRead
        System.out.print("1-Tест historyRead: \t");
        System.out.println("-"+h.entityRead("CCCC", histories).toString());

        //тест securityUpdate
        System.out.print("2-Tест securityUpdate: \t");
        securityNew.setRegnumber("changeNumber");
        s.entityUpdate(securityNew,securities);
        System.out.println("-"+s.entityRead("BBBB", securities).toString());
        //тест historyUpdate
        System.out.print("2-Tест historyUpdate: \t");
        historyNew.setClose(1.0);
        h.entityUpdate(historyNew,histories);
        System.out.println("-"+h.entityRead("CCCC", histories).toString());

        //тест securityDelete
        System.out.print("3-Tест securityDelete: \t");
        s.entityDelete("BBBB",securities);
        System.out.println((s.entityRead("BBBB", securities)==null)? "-Security удален" : s.entityRead("BBBB", securities).toString());
        //тест historyDelete
        System.out.print("3-Tест historyDelete: \t");
        h.entityDelete("CCCC",histories);
        System.out.println((h.entityRead("CCCC", histories)==null)? "-History удален" : h.entityRead("CCCC", histories).toString());

    }

    private static void testReadXML(List<Security> securities, List<History> histories) {
        System.out.println("Найдено ценных бумаг "+securities.size()+" записей.");
//        securities.stream().forEachOrdered(e -> System.out.println(e.getSecid()));

        System.out.println("Найдено истории торгов "+histories.size()+" записей.");
//        histories.stream().forEachOrdered(e -> System.out.println(e.getNumtrades()));
    }
}
