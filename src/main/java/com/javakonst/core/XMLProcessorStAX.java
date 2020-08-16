package com.javakonst.core;

import com.javakonst.entity.Entity;
import com.javakonst.entity.History;
import com.javakonst.entity.Security;
import lombok.SneakyThrows;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XMLProcessorStAX implements XMLProcessor {
    private XMLStreamReader streamXMLData;
    private boolean isStop = false;
    private List<Entity> entityList = new ArrayList<>();
    
    @SneakyThrows
    @Override
    public List<? extends Entity> dataFromXml(Entity entity, String file) {
        streamXMLData = XMLInputFactory.newInstance().createXMLStreamReader(file, new FileInputStream(file));
        while (streamXMLData.hasNext() && !isStop) {
            streamXMLData.next();
            if (streamXMLData.isStartElement()) handleEventStartElement(entity);
            if (streamXMLData.isEndElement()) handleEventEndElement();
        }
        System.out.println("-> Разбор файла " + file + " завершен. Найдено " + entityList.size() + " записей.");
        streamXMLData.close();
        return entityList;
    }
    
    @SneakyThrows
    private void handleEventStartElement(Entity entity) {
        if (streamXMLData.getLocalName().equals("row")) {
            if (entity instanceof Security) {
                Security security = new Security();
                security.setSecid(streamXMLData.getAttributeValue(0));
                security.setName(streamXMLData.getAttributeValue(2));
                security.setEmitent_title(streamXMLData.getAttributeValue(9));
                security.setRegnumber(streamXMLData.getAttributeValue(21));
                entityList.add(security);
            } else if (entity instanceof History){
                History history = new History();
                history.setTradedate(new SimpleDateFormat("yyyy-MM-dd").parse(streamXMLData.getAttributeValue(1)));
                history.setSecid(streamXMLData.getAttributeValue(3));
                history.setNumtrades(Double.parseDouble(streamXMLData.getAttributeValue(4)));
                history.setOpen(Double.parseDouble(streamXMLData.getAttributeValue(6)));
                history.setClose(Double.parseDouble(streamXMLData.getAttributeValue(11)));
            } else {
                System.out.println("-> ERROR: entity didnt recognize");
            }
            
        }
    }
    
    private void handleEventEndElement() {
        if (streamXMLData.getLocalName().equals("rows")) {
            isStop = true;
        }
    }
}
