package com.javakonst;

import com.javakonst.core.XMLProcessor;
import com.javakonst.core.XMLProcessorStAX;
import com.javakonst.entity.Security;

public class Main {
    private static String file_securities = "securities.xml";
    
    public static void main(String[] args) {
        XMLProcessorStAX xmlProcessor = new XMLProcessorStAX();
        
        xmlProcessor.dataFromXml(new Security(), file_securities);
    }
}
