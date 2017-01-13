package ru.rrusanov;

import org.junit.Assert;
import org.junit.Test;
import ru.rrusanov.models.Item;
import static org.hamcrest.core.Is.is;
/***
 * Class test all Tracker methods.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 12.01.17
 */
public class TrackerTest {
    /**
     * Checks the instantiated after constructor.
     */
    @Test
    public void thenItemCreateWhenReturnItemLink() {

        Tracker tracker = new Tracker();

        Assert.assertNotNull(tracker.add(new Item()));
    }
    /**
     * If the instance by this id exists in the array of the tracker to return it.
     */
    @Test
    public void thenItemIdFindWhenThisItemReturn() {

        Tracker tracker = new Tracker();
        final String expectId = "5050";
        for (int i = 0; i < 8; i++) {
            tracker.add(new Item());
        }

        tracker.add(new Item(expectId));

        Assert.assertNotNull(tracker.findById(expectId));
    }
    /**
     * If an update of a pattern instance he changed the link.
     */
    @Test
    public void thenItemUpdateWhenInItemNewLink() {

        Tracker tracker = new Tracker();
        Item beforeUpdate;
        Item afterUpdate;
        final String expectId = "5050";

        for (int i = 0; i < 8; i++) {
            tracker.add(new Item());
        }

        tracker.add(new Item(expectId));
        beforeUpdate = tracker.findById(expectId);
        tracker.update(new Item(expectId));
        afterUpdate = tracker.findById(expectId);

        Assert.assertNotEquals(afterUpdate, is(beforeUpdate));
    }
    /**
     * If the instance to delete is found, its reference is null.
     */
    @Test
    public void thenItemToDeleteFindWhenItemNull() {

        Tracker tracker = new Tracker();
        final String deleteId = "5050";
        Item beforeDelete;
        Item afterDelete;

        for (int i = 0; i < 8; i++) {
            tracker.add(new Item());
        }

        tracker.add(new Item(deleteId));
        beforeDelete = tracker.findById(deleteId);
        tracker.delete(tracker.findById(deleteId));
        afterDelete = tracker.findById(deleteId);

        Assert.assertNull(afterDelete);
        Assert.assertNotEquals(afterDelete, beforeDelete);
    }
    /**
     * Returns all items of an array excluding null.
     */
    @Test
    public void thenFindAllWhenReturnArrayWithOutNullItems() {

        Item[] returnArray;
        Tracker tracker = new Tracker();

        for (int i = 0; i < 7; i++) {
            tracker.add(new Item());
        }

        returnArray = tracker.findAll();

        for (Item item : returnArray) {
            Assert.assertNotNull(item);
        }
    }
    /**
     * Verifies that the returned array contains only the items whose name passed to the method.
     */
    @Test
    public void thenFindItemByNameWhenReturnItemsInArray() {

        Tracker tracker = new Tracker();
        Item[] resultArray;
        String keyNameToFind = "Name1";

        tracker.add(new Item(keyNameToFind, "Desc1", 100L, "Commen1"));
        // Random items filling
        for (int i = 0; i < 5; i++) {
            tracker.add(new Item());
        }

        tracker.add(new Item(keyNameToFind, "Desc2", 200L, "Commen2"));

        resultArray = tracker.findByName(keyNameToFind);
        for (Item item : resultArray) {
            Assert.assertThat(item.getName(), is(keyNameToFind));
        }
    }
}
