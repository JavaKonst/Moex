package com.javakonst;

import com.javakonst.dao.CRUDService;
import com.javakonst.dao.HistoryDAO;
import com.javakonst.dao.SecuritiesDAO;
import com.javakonst.db.DBService;
import com.javakonst.db.DBServiceDAO;
import com.javakonst.entity.History;
import com.javakonst.entity.Security;
import com.javakonst.utils.SortBy;
import com.javakonst.utils.WorkWithListsUtils;

import java.util.GregorianCalendar;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        runTestReadCreate();


    }

    private static void testCRUD(List<Security> securities, List<History> histories) {
        //тест securityCreate
        System.out.println("Tест securityCreate");
        CRUDService<Security> s = new SecuritiesDAO();
        Security securityNew = new Security();
        securityNew.setEmitent_title("ПАО");
        securityNew.setName("Какоето имя");
        securityNew.setRegnumber("regnumber");
        securityNew.setSecid("BBBB");
        s.entityCreate(securityNew,securities);
        //тест historyCreate
        System.out.println("Tест historyCreate");
        CRUDService<History> h = new HistoryDAO();
        History historyNew = new History();
        historyNew.setSecid("CCCC");
        historyNew.setClose(99.9);
        historyNew.setOpen(222.2);
        historyNew.setNumtrades(66.6);
        historyNew.setTradedate(new GregorianCalendar(2020, 10, 23).getTime());
        h.entityCreate(historyNew, histories);

        //тест securityRead
        System.out.print("1-Tест securityRead: \t");
        System.out.println("-"+s.entityRead("BBBB", securities).toString());
        //тест historyRead
        System.out.print("1-Tест historyRead: \t");
        System.out.println("-"+h.entityRead("CCCC", histories).toString());

        //тест securityUpdate
        System.out.print("2-Tест securityUpdate: \t");
        securityNew.setRegnumber("changeNumber");
        s.entityUpdate(securityNew,securities);
        System.out.println("-"+s.entityRead("BBBB", securities).toString());
        //тест historyUpdate
        System.out.print("2-Tест historyUpdate: \t");
        historyNew.setClose(1.0);
        h.entityUpdate(historyNew,histories);
        System.out.println("-"+h.entityRead("CCCC", histories).toString());

        //тест securityDelete
        System.out.print("3-Tест securityDelete: \t");
        s.entityDelete("BBBB",securities);
        System.out.println((s.entityRead("BBBB", securities)==null)? "-Security удален" : s.entityRead("BBBB", securities).toString());
        //тест historyDelete
        System.out.print("3-Tест historyDelete: \t");
        h.entityDelete("CCCC",histories);
        System.out.println((h.entityRead("CCCC", histories)==null)? "-History удален" : h.entityRead("CCCC", histories).toString());

    }

    private static void testReadXML(List<Security> securities, List<History> histories) {
        System.out.println("Найдено ценных бумаг " + securities.size() + " записей.");
//        securities.stream().forEachOrdered(e -> System.out.println(e.getSecid()));

        System.out.println("Найдено истории торгов " + histories.size() + " записей.");
//        histories.stream().forEachOrdered(e -> System.out.println(e.getNumtrades()));
    }

    private static void runTestReadCreate() {
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


        /*Проверка работы с БД*/
        System.out.println("\n\n\n----------Проверка работы БД:");
        //Сохранение списков ценных бумаг и их истории в БД
        System.out.print("\n\n\nЭтап 1 (сохранение в базу): ");
        DBService dbService = new DBServiceDAO();
        int[] a = dbService.saveListsToDB(securityList, historyList);
        System.out.println("-OK- s=" + a[0] + " h=" + a[1]);
        //Выгрузка списка всех бумаг
        System.out.println("\n\n\nЭтап 2 (чтение базы): ");
        List<Security> allSecurities = dbService.getAllSecurities();
        allSecurities.forEach(System.out::println);
        //Нахождение бумаги по полю secid
        System.out.println("\n\n\nЭтап 3 (чтение бумаги из базы): ");
        Security securityBySecid = dbService.getSecurityBySecid(secid);
        System.out.println(securityBySecid.toString());
        //Удаление бумаги со связанной сней историей
        System.out.println("\n\n\nЭтап 4 (удаление бумаги из базы): ");
        dbService.deleteSecurity(secid);
        Security securityBySecid2 = dbService.getSecurityBySecid(secid);
        System.out.println(securityBySecid2 == null ? "бумага удалена" : securityBySecid2.toString());
        //Сохранение бумаги
        //securityBySecid.setHistory(null); //без истории
        System.out.println("\n\n\nЭтап 5 (сохранение бумаги в базу): ");
        System.out.println(dbService.saveSecurity(securityBySecid));
        Security securityBySecid3 = dbService.getSecurityBySecid(secid);
        System.out.println(securityBySecid3 == null ? "не удалось сохранить" : securityBySecid3.toString());
    }
}
