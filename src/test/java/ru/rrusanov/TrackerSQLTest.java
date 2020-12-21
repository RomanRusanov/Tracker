package ru.rrusanov;

import org.junit.Test;
import ru.rrusanov.models.Item;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Class test Tracker.java.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 26.02.19
 */
public class TrackerSQLTest {
    /**
     * The method get connection to db.
     * @return connection.
     */
    public Connection init() {
        try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    /**
     * Test default constructor and method init(). That create connection to db.
     */
    @Test
    public void whenConnectionCreateThenReturnTrue() {
        TrackerSQL sql = new TrackerSQL();
        assertThat(sql.init(), is(true));
    }
    /**
     * Test if item create then it exist. add() Method behavior check.
     * @throws java.sql.SQLException ConnectionRollback may throw.
     */
    @Test
    public void whenItemCreateThenItExist() throws SQLException {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            String titleItem = "Тема заявки в тесте";
            String descriptionItem = "Описание заявки в тесте";
            String comment = "Коментарий 1 к заявке в тесте";
            Long dateCreate = System.currentTimeMillis();
            Item expect = new Item(titleItem, descriptionItem, dateCreate, comment);
            trackerSQL.add(expect);
            Item result = trackerSQL.findByName("Тема заявки в тесте").get(0);
            assertThat(result, is(expect));
        }
    }
    /**
     * Test if items exist in db. findAll Method behavior check.
     */
    @Test
    public void whenItemsExistThenReturnItemsId() {
        TrackerSQL trackerSQL = new TrackerSQL();
        ArrayList<Item> allItems = trackerSQL.findAll();
        assertEquals(allItems.get(0).getId(), "1");
        assertEquals(allItems.get(1).getId(), "2");
    }
    /**
     * Test if item with specific name exist in db when return id. findByName() Method behavior check.
     */
    @Test
    public void whenItemPresentThenReturnItemId() {
        TrackerSQL trackerSQL = new TrackerSQL();
        ArrayList<Item> itemByName = trackerSQL.findByName("заявка2");
        assertEquals(itemByName.get(0).getId(), "2");

    }
    /**
     * Test if item with specific item_id exist in db when return true. findById() Method behavior check.
     * @throws java.sql.SQLException ConnectionRollback may throw.
     */
    @Test
    public void whenItemWithIdExistThenReturnTrue() throws SQLException {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(this.init()))) {
            Item expect = new Item("5555");
            expect.setName("Тема заявки в тесте");
            expect.setDescription("Описание заявки в тесте");
            expect.setComment("Коментарий 1 к заявке в тесте");
            expect.setCreate(System.currentTimeMillis());
            trackerSQL.add(expect);
            assertThat(trackerSQL.findById("5555"), is(expect));
        }
    }
    /**
     * Test if passed item_id and item, when item whit what id in bd update data from passed item.
     * @throws java.sql.SQLException ConnectionRollback may throw.
     */
    @Test
    public void whenPassedIdAndItemThenThatItemIdUpdatedInBD() throws SQLException {
        try (TrackerSQL trackerSQL = new TrackerSQL(ConnectionRollback.create(init()))) {
            Item existItem = new Item("7777");
            existItem.setName("Тема заявки в тесте");
            existItem.setDescription("Описание заявки в тесте");
            existItem.setComment("Коментарий 1 к заявке в тесте");
            existItem.setCreate(System.currentTimeMillis());
            Item newItem = new Item("9999");
            newItem.setName("Обновленная тема");
            newItem.setDescription("Обновленное описанин в заявке");
            newItem.setComment("Обновленный комментарий");
            trackerSQL.add(existItem);
            trackerSQL.replace("7777", newItem);
            assertThat(newItem.getName(), is(trackerSQL.findById("7777").getName()));
            assertThat(newItem.getDescription(), is(trackerSQL.findById("7777").getDescription()));
            assertThat(newItem.getComment(), is(trackerSQL.findById("7777").getComment()));
        }
    }
}