package com.javakonst.xmlparsers;

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

public class XMLProcStAX implements XMLProcessor {
    private XMLStreamReader streamXMLData;
    private boolean isStop;

    @Override
    public <T> List<T> dataFromXML(String filePath, T entity) {
        List<T> entityList = new ArrayList<>();
        isStop = false;

        try {
            streamXMLData = XMLInputFactory.newInstance().createXMLStreamReader(filePath, new FileInputStream(filePath));
            while (streamXMLData.hasNext() && !isStop) {
                streamXMLData.next();
                if (streamXMLData.isStartElement()) handleEventStartElement(entityList, entity);
                if (streamXMLData.isEndElement()) handleEventEndElement();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entityList;
    }

    @SneakyThrows
    private <T> void handleEventStartElement(List<T> entityList, T entity) {
        if (streamXMLData.getLocalName().equals("row")) {
            if (entity instanceof Security) {
                Security newSecurity = new Security();
                newSecurity.setSecid(streamXMLData.getAttributeValue(0));
                newSecurity.setName(streamXMLData.getAttributeValue(2));
                newSecurity.setEmitent_title(streamXMLData.getAttributeValue(9));
                newSecurity.setRegnumber(streamXMLData.getAttributeValue(21));
                entityList.add((T) newSecurity);
            } else if (entity instanceof History) {
                String value;
                History newHistory = new History();

                //TODO: сделать проверку значения поля на пустоту ""/null
//                value = streamXMLData.getAttributeValue(1);
//                newHistory.setTradedate(new SimpleDateFormat("yyyy-MM-dd").parse(value));
                String[] dateString = streamXMLData.getAttributeValue(1).split("-");
                int year = Integer.parseInt(dateString[0]);
                int month = Integer.parseInt(dateString[1]);
                int day = Integer.parseInt(dateString[2]);
                Calendar date = new GregorianCalendar(year, month, day);
                newHistory.setTradedate(date.getTime());

                value = streamXMLData.getAttributeValue(3);
                Security security = new Security();
                security.setSecid(value);
                newHistory.setSecurity(security);

                value = streamXMLData.getAttributeValue(4);
                newHistory.setNumtrades(Double.parseDouble(value.isEmpty() ? "0.0" : value));

                value = streamXMLData.getAttributeValue(6);
                newHistory.setOpen(Double.parseDouble(value.isEmpty() ? "0.0" : value));

                value = streamXMLData.getAttributeValue(11);
                newHistory.setClose(Double.parseDouble(value.isEmpty() ? "0.0" : value));

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
