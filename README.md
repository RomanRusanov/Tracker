[![Build Status](https://travis-ci.org/RomanRusanov/Tracker.svg?branch=master)](https://travis-ci.org/RomanRusanov/Tracker)
[![codecov](https://codecov.io/gh/RomanRusanov/Tracker/branch/master/graph/badge.svg)](https://codecov.io/gh/RomanRusanov/Tracker)
# Tracker SQL 

В этом задании мы будет модернизировать наш проект из задания   Tracker.

Первоначально давайте подключим к нашему текущему модулю модуль chapter_002.

Для этого в файле 

chapter_00X/pom.xml

добавим зависимость:

<dependency>
    <groupId>ru.job4j</groupId>
    <artifactId>chapter_001</artifactId>
    <version>2.0</version>
</dependency>

Важно X - это имя вашего текущего модуля. Оно должно быть 8. Но может и отличаться.

Проверьте, что проект подгрузил зависимости.



Теперь давайте сделаем интерфейс описывающий поведения класса Tracker.

package ru.job4j.tracker;

public interface ITracker {
    Item add(Item item);
    void replace(String id, Item item);
    void delete(String id);
    Item[] findAll();
    Item[] findByName(String key);
    Item findById(String id);
}
Эти изменения нужно производить в модуле chapter_002. 

Дальше давайте его везде пропишем.



И везде по коду пропишем использование интерфейса.



После этого запустим тесты и проверим, что у нас все работает.

Теперь настало время используя ООП заменить наше хранилище с памяти на SQL.

Здесь мы можем видеть всю прелесть ООП. Мы делаем незначительные изменения в коде и это добавляет большую гибкость.

Чтобы в модуле chapter_00x был виден интерфейс ITracker модуль chapter_002 нужно пересобрать (выполнить обработку):
mvn install

Теперь работает только в модуле chapter_00x.

Создадим класс TrackerSQL. Он будет расширять два интерфейса ITracker и AutoCloseable. 

package ru.job4j.tracker;

public class TrackerSQL implements ITracker, AutoCloseable {
 @Override
 public Item add(Item item) {
 return null;
 }

 @Override
 public void replace(String id, Item item) {

 }

 @Override
 public void delete(String id) {

 }

 @Override
 public Item[] findAll() {
 return new Item[0];
 }

 @Override
 public Item[] findByName(String key) {
 return new Item[0];
 }

 @Override
 public Item findById(String id) {
 return null;
 }
}
Этот класс будет подключаться к базе данных, создавать в ней записи, редактировать, читать и удалять.

Для того, чтобы мы могли подключаться к разным базам нам нужно вынести настройки в файл:

app.properties

Он должен заходится в папке resource.



Содержимое файла представлено ниже:

url=jdbc:postgresql://127.0.0.1:5432/tracker
username=postgres
password=password
driver-class-name=org.postgresql.Driver

Для считывания файла используйте ClassLoader.

package ru.job4j.tracker;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class TrackerSQL implements ITracker, AutoCloseable {

 private Connection connection;

 public boolean init() {
 try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
 Properties config = new Properties();
 config.load(in);
 Class.forName(config.getProperty("driver-class-name"));
 this.connection = DriverManager.getConnection(
 config.getProperty("url"),
 config.getProperty("username"),
 config.getProperty("password")
 );
 } catch (Exception e) {
 throw new IllegalStateException(e);
 }
 return this.connection != null;
 }


Предусмотреть возможность, что структуры в базе еще нет. И вам нужно ее создать при старте.

Все ресурсы необходимо закрывай через try-with-resources

В классе трекер появляется новое поле private Connection connection. Его нужно закрывать через AutoCloseable.
Например

try (Tracker tracker = new Tracker(config)) {
     // todo actions.
}

И давайте напишем примитивный тест.

package ru.job4j.tracker;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TrackerSQLTest {
    @Test
    public void checkConnection() {
        TrackerSQL sql = new TrackerSQL();
        assertThat(sql.init(), is(true));
    }
}

Ваша задача реализовать методы интефейса ITracker. Храниться и извлекать все данные нужно из базы данных.
***
С помошь данного репозитория происходит обмен кодом с наставником с проекта [Job4J.ru](http://job4j.ru/)
