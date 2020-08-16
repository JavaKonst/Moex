package com.javakonst.core;

import com.javakonst.entity.Entity;
import com.javakonst.entity.Security;
import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

public class XMLProcessorDOM implements XMLProcessor {
    private Document docDOM;
    
    @Override
    @SneakyThrows
    public List<? extends Entity> dataFromXml(Entity entity, String file) {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            docDOM = documentBuilder.parse(file);
            parseDataToEntities();
            
            return null;
    }
    
    public void parseDataToEntities() {
        List<Security> securities = new ArrayList<>();
        
        Element root = docDOM.getDocumentElement();
        NodeList rows = root.getElementsByTagName("rows");
        
//        for (int i = 0; i < rows.getLength(); i++) {
//            rows.item(i).getChildNodes()
//        }
    }
    

    
}
