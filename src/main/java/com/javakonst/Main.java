package com.javakonst;

import com.javakonst.dao.CRUDService;
import com.javakonst.dao.HistoryDAO;
import com.javakonst.dao.SecuritiesDAO;
import com.javakonst.db.DBService;
import com.javakonst.db.DBServiceDAO;
import com.javakonst.entity.History;
import com.javakonst.entity.Security;
import com.javakonst.utils.ListUtils;
import com.javakonst.utils.SortBy;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Main {
    private static final String file_securities = "securitiesShort.xml";
    private static final String file_history = "history.xml";

    public static void main(String[] args) {
//        //CRUD со списком
//        testCRUDlist();

//        //CRUD с БД
//        testDownloadData();
//        testRead();
//        testSave();
//        testDelete();
//        testUpdate();
    }

    private static void testCRUDlist() {
        //Получение списков из файлов (ценные бумаги и история торгов)
        ListUtils listUtils = new ListUtils();
        List<Security> securities = listUtils.getListSecuritires(file_securities);
        List<History> histories = listUtils.getListHistory(file_history);

        CRUDService<Security> securitiesDAO = new SecuritiesDAO();
        //Тест securityCreate
        Security securityNew = new Security();
        securityNew.setEmitent_title("ПАО");
        securityNew.setName("Какоето имя");
        securityNew.setRegnumber("regnumber");
        securityNew.setSecid("BBBB");
        securitiesDAO.entityCreate(securityNew, securities);
        //тест historyCreate
        CRUDService<History> historyDAO = new HistoryDAO();
        History historyNew = new History();
        historyNew.setSecurity(securityNew);
        historyNew.setClose(99.9);
        historyNew.setOpen(222.2);
        historyNew.setNumtrades(66.6);
        historyNew.setTradedate(new GregorianCalendar(2020, 10, 23).getTime());
        historyDAO.entityCreate(historyNew, histories);

        //тест securityRead
        System.out.print("1-Tест securityRead: \t");
        System.out.println("-" + securitiesDAO.entityRead("BBBB", securities).toString());
        //тест historyRead
        System.out.print("1-Tест historyRead: \t");
        System.out.println("-" + historyDAO.entityRead("CCCC", histories).toString());

        //тест securityUpdate
        System.out.print("2-Tест securityUpdate: \t");
        securityNew.setRegnumber("changeNumber");
        securitiesDAO.entityUpdate(securityNew, securities);
        System.out.println("-" + securitiesDAO.entityRead("BBBB", securities).toString());
        //тест historyUpdate
        System.out.print("2-Tест historyUpdate: \t");
        historyNew.setClose(1.0);
        historyDAO.entityUpdate(historyNew, histories);
        System.out.println("-" + historyDAO.entityRead("CCCC", histories).toString());

        //тест securityDelete
        System.out.print("3-Tест securityDelete: \t");
        securitiesDAO.entityDelete("BBBB", securities);
        System.out.println((securitiesDAO.entityRead("BBBB", securities) == null) ? "-Security удален" : securitiesDAO.entityRead("BBBB", securities).toString());
        //тест historyDelete
        System.out.print("3-Tест historyDelete: \t");
        historyDAO.entityDelete("CCCC", histories);
        System.out.println((historyDAO.entityRead("CCCC", histories) == null) ? "-History удален" : historyDAO.entityRead("CCCC", histories).toString());
    }

    private static void testReadXML(List<Security> securities, List<History> histories) {
        System.out.println("Найдено ценных бумаг " + securities.size() + " записей.");
//        securities.stream().forEachOrdered(e -> System.out.println(e.getSecid()));

        System.out.println("Найдено истории торгов " + histories.size() + " записей.");
//        histories.stream().forEachOrdered(e -> System.out.println(e.getNumtrades()));
    }


    private static void testDownloadData() {
        String secid = "AQUA";
        String filter = "tradedate=2010";
        SortBy sort = SortBy.OPEN;

        //Создание инструмента
        ListUtils listUtils = new ListUtils();

        //Получение списков из файлов (ценные бумаги и история торгов)
        List<Security> securityList = listUtils.getListSecuritires(file_securities);
        List<History> historyList = listUtils.getListHistory(file_history);

//        testReadXML(securityList, historyList);
//        testCRUD(securityList, historyList);

        //Вывод таблицы с заданными, техзаданием, столбцами с возможностью сортировки по любым столбцам,
        //а также фильтр по столбцам EMITENT_TITLE и TRADEDATE
        listUtils.printTable(file_securities, file_history, sort, filter);

        //Загрузка в БД
        DBService dbService = new DBServiceDAO();
        int[] a = dbService.saveListsToDB(securityList, historyList);
        System.out.println("В БД загружено " + a[0] + " бумаг и " + a[1] + " записей историй бумаг.");
    }

    private static void testDelete() {
        DBService dbService = new DBServiceDAO();

        String secid1 = "CCCC";
        int i = dbService.deleteSecurityBySecid(secid1);
        System.out.println("Удалено " + i + " бумаг(а)(и) " + secid1);

        String secid2 = "BBBB";
        int j = dbService.deleteHistoryBySecuritySecid(secid2);
        System.out.println("Удалено " + j + " историй бумаги " + secid2);
    }

    private static void testSave() {
        DBService dbService = new DBServiceDAO();

        Security security = new Security();
        security.setSecid("CCCC");
        security.setEmitent_title("123456");
        security.setName("SomeName");
        security.setRegnumber("789456");
        History history = new History();
        history.setSecurity(security);
        history.setClose(11.15);
        history.setOpen(20.15);
        history.setNumtrades(111.111);
        history.setTradedate(new Date());
        List<History> l = new ArrayList<>();
        l.add(history);
        security.setHistoryList(l);

        String underSecid = dbService.saveSecurity(security);
        System.out.println("Бумага с историей сохранена под secid=" + underSecid);
    }

    private static void testRead() {
        DBService dbService = new DBServiceDAO();

        List<Security> allSecurities = dbService.getAllSecurities();
        System.out.println("БД содержит информацию о " + allSecurities.size() + " бумаг.");

        String secid = "ABRD";
        Security securityBySecid = dbService.getSecurityBySecid(secid);
        System.out.println("Прочитана бумага: ");
        System.out.println(securityBySecid.toString());
    }

    private static void testUpdate() {
        DBService dbService = new DBServiceDAO();
        String secid = "MSRS";

        Security security = dbService.getSecurityBySecid(secid);
        security.setRegnumber("100");

        boolean isUpdate = dbService.updateSecurity(security);

        if (isUpdate) {
            System.out.println("Произведено обновление данных бумаги " + secid + ": ");
            System.out.println(dbService.getSecurityBySecid(secid));
        } else System.out.println("Не удалось обновить данные бумаги " + secid);

        if (security.getHistoryList().isEmpty()) System.out.println("По данной бумаге нет исторических записей");
        else {
            History history = security.getHistoryList().get(0);
            history.setClose(0.1);

            isUpdate = dbService.updateHistory(history);

            if (isUpdate) {
                System.out.println("Произведено обновление истории бумаги " + secid + ": ");
                System.out.println(dbService.getSecurityBySecid(secid).getHistoryList().get(0).toString());
            } else System.out.println("Не удалось обновить историю бумаги " + secid);
        }


    }
}
