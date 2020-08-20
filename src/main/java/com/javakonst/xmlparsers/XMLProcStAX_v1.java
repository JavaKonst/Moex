package com.javakonst.xmlparsers;

import com.javakonst.entity.Entity;
import com.javakonst.entity.History;
import com.javakonst.entity.Security;
import lombok.SneakyThrows;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class XMLProcStAX_v1<T extends Entity> implements XMLProcessor_v1 {
    private XMLStreamReader streamXMLData;
    private boolean isStop;
    private T newEntity;
    private List<T> entityList;

    public XMLProcStAX_v1(T entity){
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
            } else if (newEntity instanceof History) {
                String value;
                History newHistory = new History();

                //TODO: сделать проверку значения поля на пустоту ""/null
                value = streamXMLData.getAttributeValue(1);
                String[] dateString = value.split("-");
                int year = Integer.parseInt(dateString[0]);
                int month = Integer.parseInt(dateString[1]);
                int day = Integer.parseInt(dateString[2]);
                Calendar date = new GregorianCalendar(year, month, day);
                newHistory.setTradedate(date.getTime());

                value = streamXMLData.getAttributeValue(3);
                newHistory.setSecid(value);

                value = streamXMLData.getAttributeValue(4);
                newHistory.setNumtrades(Double.parseDouble(value.isEmpty() ? "0.0" : value));

                value = streamXMLData.getAttributeValue(6);
                newHistory.setOpen(Double.parseDouble(value.isEmpty() ? "0.0" : value));

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
