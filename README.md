# Тестовая задача №1

#### Дано:
- xml-файлы с выгрузкой: security_*.xml (общая информация по всем ценным бумагам),  
  history_*.xml (история торгов ценных бумаг на конкретную дату);
- руководство пользования API Московской биржи (https://fs.moex.com/files/6523).

#### Условия:
- поле для связи "SECID";
- ценная бумага может существовать без истории, история без ценной бумаги - нет.

#### Задачи:
- реализовать импорт объектов из приложенных файлов (какие данные сохранять - на свое усмотрение);
- предоставить API для основных операций (CRUD);
- реализовать валидацию поля "NAME" при сохранении ценной бумаги: только кириллица, цифры, пробел;
- реализовать отдельным методом табличный вывод полей (SECID, REGNUMBER, NAME, EMITENT_TITLE, TRADEDATE, NUMTRADES, OPEN, CLOSE),  должна быть возможность фильтрации по полям EMITENT_TITLE и TRADEDATE, также предусмотреть сортировку по столбцам;

#### Доп.задачи:
- реализовать хранение и работу с данными в БД;
- другие задачи не реализованы.

# Реализация
Для написания программы был выбран стек: Gradle, Hibernate, PostgreSQL.Также используются плагины Lombok, JavaTextUtilities (табличный вывод). Входные xml-файлы были получены по следующим запросам:
- https://iss.moex.com/iss/engines/stock/markets/shares/securities.xml - получили список всех ценных бумаг;
- https://iss.moex.com/iss/history/engines/stock/markets/shares/securities.xml?date=2020-08-12 - история торгов за 12.авг.2020.

Для разбора xml-файлов использовался API StAX (см. com.javakonst.xmlparsers). Для хранения данных было реализовано два способа: два списка List\<Security\>, List\<History\>; база данных с таблицами securities и history. Работа с объектами (списки и таблицы) выполняется через один класс ServiceAPI. Для табличного вывода реализован метод printTable класса ListsUtils (с возможностью сортировки и фильтрации).

# Инструкция
Для начала работы нужно подготовить два xml-файла (перечень ценных бумаг и история торгов за нужную дату). 

Для табличного вывода вызывается метод printTable (String filepath_security, String filepath_history, SortBy sortField, String filter). Класс перечисления SortBy содержит основные столбцы для сортировки: SECID, REGNUMBER, NAME, EMITENT_TITLE, TRADEDATE, NUMTRADES, OPEN, CLOSE. Параметр filter определяет фильтрацию по выбранному полю, в данном случае или по полю  EMITENT_TITLE, или по полю TRADEDATE. Шаблон выглядит следующим образом: «поле=значение». Например, «EMITENT_TITLE=ПАО». Если фильтрация не требуется, то можно значение параметра оставить null.

Для работы с данными необходим экземпляр класса  ServiceAPI со строковыми параметрами путей к xml-файлам:
- public ServiceAPI(String filepath_security, String filepath_history)

Для работы с данными как со списками используются следующие методы класса ServiceAPI:
- public List\<Security\> listGetAllSecuritires() //получение списка всех ценных бумаг
- public Security listGetSecurityBySecid(String secid) //получение ценной бумаги по полю SECID
- public List\<Security\> listSaveSecurity(Security security) //сохранение ценной бумаги (с валидацией  поля NAME) и выдача общего списка
- public List\<Security\> listUpdateSecurity(Security security)	//изменение ценной бумаги и выдача общего списка
- public List\<Security\> listDeleteSecurityBySecid(String secid)	//удаление ценной бумаги по полю SECID и выдача общего списка
- public List\<History\> listGetAllHistory()		//получение списка истории торгов
- public History listGetHistoryBySecid(String secid)	//получение истории торгов по полю SECID ценой бумаги
- public List\<History\> listSaveHistory(History history)	//сохранение истории и выдача общего списка
- public List\<History\> listUpdateHistory(History history)	//изменение истории и выдача общего списка
- public List\<History\> listDeleteHistoryBySecid(String secid)	//удаление истории по полю SECID ценной бумаги и выдача общего списка

Для загрузки полученных данных в базу данных и последующее их использование применяются следующие методы класса ServiceAPI:
- public void dbCreateDB()	//создание таблиц securities и history и загрузка данных в БД из xml-файлов
- public String dbSaveSecurity(Security security)	//сохранение (с валидацией по полю NAME) ценной бумаги в базу
- public List\<Security\> dbGetAllSecurity()		//получение списка ценных бумаг из базы
- public Security dbGetSecurityBySecid(String secid)	//получение ценной бумаги по полю SECID из базы
- public int dbDeleteSecurityBySecid(String secid)		//удаление ценной бумаги по полю SECID из базы, история также удаляется
- public int dbDeleteHistoryBySecid(String secid)		//удаление истории по полю SECID ценной бумаги, ценная бумага не удаляется
- public void dbUpdateSecurity(Security security)	//изменение данных ценной бумаги в базе
- public void dbUpdateHistory(History history)	//изменение истории ценной бумаги в базе
