package com.javakonst;

import com.javakonst.db.DBService;
import com.javakonst.db.DBServiceDAO;
import com.javakonst.entity.History;
import com.javakonst.entity.Security;
import com.javakonst.utils.SortBy;
import com.javakonst.utils.WorkWithListsUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
//        testRead();
//        testSave();
//        testDelete();
        testUpdate();
    }

//    private static void testCRUD(List<Security> securities, List<History> histories) {
//        //тест securityCreate
//        System.out.println("Tест securityCreate");
//        CRUDService<Security> s = new SecuritiesDAO();
//        Security securityNew = new Security();
//        securityNew.setEmitent_title("ПАО");
//        securityNew.setName("Какоето имя");
//        securityNew.setRegnumber("regnumber");
//        securityNew.setSecid("BBBB");
//        s.entityCreate(securityNew,securities);
//        //тест historyCreate
//        System.out.println("Tест historyCreate");
//        CRUDService<History> h = new HistoryDAO();
//        History historyNew = new History();
//        historyNew.setSecid("CCCC");
//        historyNew.setClose(99.9);
//        historyNew.setOpen(222.2);
//        historyNew.setNumtrades(66.6);
//        historyNew.setTradedate(new GregorianCalendar(2020, 10, 23).getTime());
//        h.entityCreate(historyNew, histories);
//
//        //тест securityRead
//        System.out.print("1-Tест securityRead: \t");
//        System.out.println("-"+s.entityRead("BBBB", securities).toString());
//        //тест historyRead
//        System.out.print("1-Tест historyRead: \t");
//        System.out.println("-"+h.entityRead("CCCC", histories).toString());
//
//        //тест securityUpdate
//        System.out.print("2-Tест securityUpdate: \t");
//        securityNew.setRegnumber("changeNumber");
//        s.entityUpdate(securityNew,securities);
//        System.out.println("-"+s.entityRead("BBBB", securities).toString());
//        //тест historyUpdate
//        System.out.print("2-Tест historyUpdate: \t");
//        historyNew.setClose(1.0);
//        h.entityUpdate(historyNew,histories);
//        System.out.println("-"+h.entityRead("CCCC", histories).toString());
//
//        //тест securityDelete
//        System.out.print("3-Tест securityDelete: \t");
//        s.entityDelete("BBBB",securities);
//        System.out.println((s.entityRead("BBBB", securities)==null)? "-Security удален" : s.entityRead("BBBB", securities).toString());
//        //тест historyDelete
//        System.out.print("3-Tест historyDelete: \t");
//        h.entityDelete("CCCC",histories);
//        System.out.println((h.entityRead("CCCC", histories)==null)? "-History удален" : h.entityRead("CCCC", histories).toString());
//
//    }

    private static void testReadXML(List<Security> securities, List<History> histories) {
        System.out.println("Найдено ценных бумаг " + securities.size() + " записей.");
//        securities.stream().forEachOrdered(e -> System.out.println(e.getSecid()));

        System.out.println("Найдено истории торгов " + histories.size() + " записей.");
//        histories.stream().forEachOrdered(e -> System.out.println(e.getNumtrades()));
    }

    private static void testDownloadData() {
        String file_securities = "securitiesShort.xml";
        String file_history = "history.xml";
        String secid = "AQUA";
        String filter = "tradedate=2010";
        SortBy sort = SortBy.OPEN;

        //Создание инструмента
        WorkWithListsUtils workWithListsUtils = new WorkWithListsUtils();

        //Получение списков из файлов (ценные бумаги и история торгов)
        List<Security> securityList = workWithListsUtils.getListSecuritires(file_securities);
        List<History> historyList = workWithListsUtils.getListHistory(file_history);

//        testReadXML(securityList, historyList);
//        testCRUD(securityList, historyList);

        //Вывод таблицы с заданными, техзаданием, столбцами с возможностью сортировки по любым столбцам,
        //а также фильтр по столбцам EMITENT_TITLE и TRADEDATE
        workWithListsUtils.printTable(file_securities, file_history, sort, filter);

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
