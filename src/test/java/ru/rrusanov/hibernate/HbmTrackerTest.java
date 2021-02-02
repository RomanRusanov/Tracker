package ru.rrusanov.hibernate;

import org.junit.Test;

import java.sql.Timestamp;
import java.util.List;
import static org.junit.Assert.assertEquals;
/**
 * The class test HbmTracker.java use hsqldb tests create db in memory
 * only when test executes before each test. Test use different
 * xml: src/test/resources/hibernate.cfg.xml
 * and dependency: org.hsqldb:hsqldb:2.5.1
 */
public class HbmTrackerTest {
    /**
     * Test add method.
     */
    @Test
    public void whenItemAddedThenItExist() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item("Name");
        tracker.add(item);
        assertEquals(List.of(item), tracker.findAll());
    }

    /**
     * Test replace method.
     */
    @Test
    public void whenItemReplacedThenPresentNewDataInItem() {
        HbmTracker tracker = new HbmTracker();
        Item itemOld = Item.of(
                "oldName",
                "oldDescription",
                new Timestamp(0L)
        );
        tracker.add(itemOld);
        Item itemNew = Item.of(
                "newName",
                "newDescription",
                new Timestamp(1000L)
        );
        itemNew.setId(1);
        tracker.replace("1", itemNew);
        assertEquals(List.of(itemNew), tracker.findAll());
    }

    /**
     * Test delete method.
     */
    @Test
    public void whenItemDeleteThenItNotExist() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item("Name");
        tracker.add(item);
        tracker.delete("1");
        assertEquals(0, tracker.findAll().size());
    }

    /**
     * Test method findAll.
     */
    @Test
    public void whenCallFindAllThenReturnAllItemsInDB() {
        HbmTracker tracker = new HbmTracker();
        Item item1 = Item.of(
                "Name1",
                "Description1",
                new Timestamp(0L)
        );
        tracker.add(item1);
        Item item2 = Item.of(
                "Name1",
                "Description2",
                new Timestamp(1000L)
        );
        tracker.add(item2);
        assertEquals(List.of(item1, item2), tracker.findAll());
    }

    /**
     * Test findByName method.
     */
    @Test
    public void whenPassNameThenReturnItemWithThisName() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item("Name");
        item.setId(1);
        tracker.add(item);
        assertEquals(List.of(item), tracker.findByName("Name"));
    }

    /**
     * Test findById method.
     */
    @Test
    public void whenPassIdThenReturnItemWithThisId() {
        HbmTracker tracker = new HbmTracker();
        Item item = new Item("Name");
        item.setId(1);
        tracker.add(item);
        assertEquals(item, tracker.findById("1"));
    }
}