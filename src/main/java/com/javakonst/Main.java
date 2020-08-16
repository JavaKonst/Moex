package com.javakonst;

import com.javakonst.core.XMLProcessor;
import com.javakonst.core.XMLProcessorStAX;
import com.javakonst.entity.Entity;
import com.javakonst.entity.History;
import com.javakonst.entity.Security;

import java.util.List;

public class Main {
    private static String file_securities = "securitiesShort.xml";
    private static String file_history = "history.xml";
    
    public static void main(String[] args) {
        XMLProcessorStAX xmlProcessor = new XMLProcessorStAX();
    
        List<Security> securities = (List<Security>) xmlProcessor.dataFromXml(new Security(), file_securities);
        securities.stream().forEachOrdered(e -> System.out.println(e.getSecid()));
        
        List<History> histories = (List<History>) xmlProcessor.dataFromXml(new History(), file_history);
        histories.stream().forEachOrdered(e -> System.out.println(e.getNumtrades()));
    
    }
}
