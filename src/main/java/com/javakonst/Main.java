package com.javakonst;

import com.javakonst.db.DBService;
import com.javakonst.db.DBServiceDAO;
import com.javakonst.entity.History;
import com.javakonst.entity.Security;
import com.javakonst.utils.ListUtils;
import com.javakonst.utils.SortBy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    private static final String file_securities = "securitiesShort.xml";
    private static final String file_history = "history.xml";

    public static void main(String[] args) {
        //CRUD со списком
        testListCRUD();

        //CRUD с БД
        testDbCRUD();

        //Печать таблицы
        testPrintTable();
    }

    private static void testPrintTable() {
        ListUtils listUtils = new ListUtils();
        String filter = "EMITENT_TITLE=ПАО";
        listUtils.printTable(file_securities, file_history,SortBy.NAME,filter);
    }
    private static void testDbCRUD() {
        ServiceAPI serviceAPI = new ServiceAPI(file_securities, file_history);

        serviceAPI.dbCreateDB();
        List<Security> securityList = serviceAPI.dbGetAllSecurity();
        System.out.println(securityList.size());
        System.out.println("---");
        Security aqua = serviceAPI.dbGetSecurityBySecid("AQUA");
        System.out.println(aqua.toString());
        System.out.println("---");

        Security security = new Security();
        security.setSecid("CCCC");
        security.setEmitent_title("SomeTitle");
        security.setName("Привет");
        security.setRegnumber("111111");
        History history = new History();
        history.setSecurity(security);
        history.setClose(22.22);
        history.setOpen(11.11);
        history.setNumtrades(111.111);
        history.setTradedate(new Date());
        List<History> l = new ArrayList<>();
        l.add(history);
        security.setHistoryList(l);

        serviceAPI.dbSaveSecurity(security);
        Security cccc = serviceAPI.dbGetSecurityBySecid("CCCC");
        System.out.println(cccc.toString());
        System.out.println("---");

        history.setOpen(99.99);
        serviceAPI.dbUpdateHistory(history);
        Security cccc1 = serviceAPI.dbGetSecurityBySecid("CCCC");
        System.out.println(cccc1.getHistoryList().get(0).toString());
        System.out.println("---");

        security.setRegnumber("999999");
        serviceAPI.dbUpdateSecurity(security);
        Security cccc2 = serviceAPI.dbGetSecurityBySecid("CCCC");
        System.out.println(cccc2.toString());
        System.out.println("---");

        serviceAPI.dbDeleteHistoryBySecid("AQUA");
        Security aqua1 = serviceAPI.dbGetSecurityBySecid("AQUA");
        System.out.println(aqua1.toString());
        System.out.println("---");
        serviceAPI.dbDeleteSecurityBySecid("CCCC");
        List<Security> securityList1 = serviceAPI.dbGetAllSecurity();
        securityList1.forEach(System.out::println);
    }
    private static void testListCRUD() {
        ServiceAPI serviceAPI = new ServiceAPI(file_securities, file_history);
        List<History> historyList = serviceAPI.listGetAllHistory();
        System.out.println(historyList.size());
        List<Security> securityList = serviceAPI.listGetAllSecuritires();
        System.out.println(securityList.size());

        Security security = new Security();
        security.setSecid("CCCC");
        security.setEmitent_title("123456");
        security.setName("Привет");
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

        List<History> historyList1 = serviceAPI.listSaveHistory(history);
        System.out.println(historyList1.size());
        List<Security> securityList1 = serviceAPI.listSaveSecurity(security);
        System.out.println(securityList1.size());

        Security security1 = serviceAPI.listGetSecurityBySecid("CCCC");
        System.out.println(security1.toString());
        History history1 = serviceAPI.listGetHistoryBySecid("CCCC");
        System.out.println(history1.toString());

        security.setRegnumber("123456");
        serviceAPI.listUpdateSecurity(security);
        Security security2 = serviceAPI.listGetSecurityBySecid("CCCC");
        System.out.println(security2.toString());
        history.setClose(99.99);
        serviceAPI.listUpdateHistory(history);
        History history2 = serviceAPI.listGetHistoryBySecid("CCCC");
        System.out.println(history2.toString());

        serviceAPI.listDeleteSecurityBySecid("CCCC");
        List<Security> securityList2 = serviceAPI.listGetAllSecuritires();
        securityList2.forEach(System.out::println);
        serviceAPI.listDeleteHistoryBySecid("CCCC");
        List<History> historyList2 = serviceAPI.listGetAllHistory();
        historyList2.forEach(System.out::println);
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
