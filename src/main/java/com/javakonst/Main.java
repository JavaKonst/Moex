package com.javakonst;

import com.javakonst.core.XMLProcessorStAX;
import com.javakonst.dao.CRUDService;
import com.javakonst.dao.SecuritiesDAO;
import com.javakonst.entity.History;
import com.javakonst.entity.Security;

import java.util.List;

public class Main {
    private static String file_securities = "securitiesShort.xml";
    private static String file_history = "history.xml";
    
    public static void main(String[] args) {
        XMLProcessorStAX xmlProcessor = new XMLProcessorStAX();
    
        List<Security> securities = (List<Security>) xmlProcessor.dataFromXml(new Security(), file_securities);
//        securities.stream().forEachOrdered(e -> System.out.println(e.getSecid()));
        
        List<History> histories = (List<History>) xmlProcessor.dataFromXml(new History(), file_history);
//        histories.stream().forEachOrdered(e -> System.out.println(e.getNumtrades()));

        //тест securityCreate
        System.out.println("Tест securityCreate");
        CRUDService s = new SecuritiesDAO();
        Security securityNew = new Security();
        securityNew.setEmitent_title("ПАО");
        securityNew.setName("Какоето имя");
        securityNew.setRegnumber("regnumber");
        securityNew.setSecid("BBBB");
        s.entityCreate(securityNew,securities);

        //тест securityRead
        System.out.println("Tест securityRead");
        System.out.println("-"+s.entityRead("BBBB", securities).toString());

        //тест securityUpdate
        System.out.println("Tест securityUpdate");
        securityNew.setRegnumber("changeNumber");
        s.entityUpdate(securityNew,securities);
        System.out.println("-"+s.entityRead("BBBB", securities).toString());

        //тест securityDelete
        System.out.println("Tест securityDelete");
        s.entityDelete("BBBB",securities);
        System.out.println((s.entityRead("BBBB", securities)==null)? "-Security удален" : s.entityRead("BBBB", securities).toString());

    }
}
