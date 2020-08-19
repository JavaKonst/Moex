package com.javakonst.core;

import com.javakonst.entity.*;
import lombok.SneakyThrows;

import javax.xml.stream.*;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class XMLProcStAX<T extends Entity> implements XMLProcessor {
    private XMLStreamReader streamXMLData;
    private boolean isStop;
    private T newEntity;
    private List<T> entityList;

    public XMLProcStAX(T entity){
        newEntity = entity;
    }

    @Override
    @SneakyThrows
    public List<T> dataFromXml(String file) {
        if (file.isEmpty()) throw new RuntimeException("Input arguments error from method dataFromXml().");
        entityList = new ArrayList<>();
        isStop = false;
        streamXMLData = XMLInputFactory.newInstance().createXMLStreamReader(file, new FileInputStream(file));
        while (streamXMLData.hasNext() && !isStop) {
            streamXMLData.next();
            if (streamXMLData.isStartElement()) handleEventStartElement();
            if (streamXMLData.isEndElement()) handleEventEndElement();
        }
        return entityList;
    }

    @SneakyThrows
    private void handleEventStartElement() {
        if (streamXMLData.getLocalName().equals("row")) {
            if (newEntity instanceof Security) {
                Security newSecurity = new Security();
                newSecurity.setSecid(streamXMLData.getAttributeValue(0));
                newSecurity.setName(streamXMLData.getAttributeValue(2));
                newSecurity.setEmitent_title(streamXMLData.getAttributeValue(9));
                newSecurity.setRegnumber(streamXMLData.getAttributeValue(21));
                entityList.add((T) newSecurity);
            } else if (newEntity instanceof History){
                String value;
                History newHistory = new History();

                //TODO: сделать проверку значения поля на пустоту ""/null
                value = streamXMLData.getAttributeValue(1);
                newHistory.setTradedate(new SimpleDateFormat("yyyy-MM-dd").parse(value));

                value = streamXMLData.getAttributeValue(3);
                newHistory.setSecid(value);

                value = streamXMLData.getAttributeValue(4);
                newHistory.setNumtrades(Double.parseDouble(value.isEmpty() ? "0.0": value));

                value = streamXMLData.getAttributeValue(6);
                newHistory.setOpen(Double.parseDouble(value.isEmpty() ? "0.0": value));

                value = streamXMLData.getAttributeValue(11);
                newHistory.setClose(Double.parseDouble(value.isEmpty() ? "0.0": value));

                entityList.add((T) newHistory);
            }
        }
    }

    private void handleEventEndElement() {
        if (streamXMLData.getLocalName().equals("rows")) {
            isStop = true;
        }
    }
}
