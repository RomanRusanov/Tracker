package ru.rrusanov;

import org.junit.Assert;
import org.junit.Test;
import ru.rrusanov.actions.DeleteAction;
import ru.rrusanov.actions.FindByIdAction;
import ru.rrusanov.actions.FindByName;
import ru.rrusanov.models.Item;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
     * --------------
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
     * Use mock for input.
     * Test DeleteAction.
     */
    @Test
    public void deleteActionWithMock() {
        Input input = mock(Input.class);
        Tracker tracker = new Tracker();
        tracker.add(new Item("123"));
        DeleteAction deleteAction = new DeleteAction();
        PrintStream defaultOut = System.out;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream stubOut = new PrintStream(stream);
        System.setOut(stubOut);
        when(input.ask(any(String.class))).thenReturn("123");
        deleteAction.execute(input, tracker);
        System.setOut(defaultOut);
        Assert.assertEquals("Item ID:123 success deleted.", new String(stream.toByteArray()));
    }

    /**
     * Use mock for input.
     * Test FindByNameAction.
     */
    @Test
    public void findByNameActionWithMock() {
        Input input = mock(Input.class);
        Tracker tracker = new Tracker();
        tracker.add(new Item("First", "Description", 1L, "Comment"));
        FindByName findByName = new FindByName();
        PrintStream defaultOut = System.out;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream stubOut = new PrintStream(stream);
        System.setOut(stubOut);
        when(input.ask(any(String.class))).thenReturn("First");
        findByName.execute(input, tracker);
        System.setOut(defaultOut);
        Assert.assertEquals("First", new String(stream.toByteArray()));
    }

    /**
     * Use mock for input.
     * Test FindByIdAction.
     */
    @Test
    public void findByIdActionWithMock() {
        Input input = mock(Input.class);
        Tracker tracker = new Tracker();
        tracker.add(new Item("123"));
        FindByIdAction findByIdAction = new FindByIdAction();
        PrintStream defaultOut = System.out;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        PrintStream stubOut = new PrintStream(stream);
        System.setOut(stubOut);
        when(input.ask(any(String.class))).thenReturn("123");
        findByIdAction.execute(input, tracker);
        System.setOut(defaultOut);
        Assert.assertEquals("123", new String(stream.toByteArray()));
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
        // Fill collection with random items.
        for (int i = 0; i < 8; i++) {
            tracker.add(new Item());
        }
        // Check update process.
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

        ArrayList<Item> returnArray;
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
     * --------------------
     * Verifies that the returned array contains only the items whose name passed to the method.
     */
    @Test
    public void thenFindItemByNameWhenReturnItemsInArray() {

        Tracker tracker = new Tracker();
        ArrayList<Item> resultArray;
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
    /**
     * Test convert date&time to milliseconds.
     * @{value} expect string value date and time
     */
    @Test
    public void thenArgTypeLongWhenReturnStringDateAndTime() {
        Tracker tracker = new Tracker();
        final String expect = "29.01.17 17:01:00";
        final String result = tracker.convert(1485698460000L);
        Assert.assertThat(result, is(expect));
    }
    /**
     * Test convert milliseconds to date&time.
     * @{value} expect value in milliseconds.
     */
    @Test
    public void thenArgTypeStringWhenReturnLongDataAndTimeInMillis() {
        Tracker tracker = new Tracker();
        final long expect = 1485698460000L;
        final long result = tracker.convert("29.01.17 17:01:00");
        Assert.assertThat(result, is(expect));
    }
    /**
     * Test findByCreate method.
     * @{value} expect value new item with specific create filed(date&time).
     */
    @Test
    public void thenSearchingDateAndTimeEqualsDateAndTimeInItemsArrayWhenReturnThem() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("name", "desc",  tracker.convert("01.01.70 12:00:00"), "comm"));
        final ArrayList<Item> result = tracker.findByCreate("01.01.70 12:00:00");
        Assert.assertTrue(result.get(0).getCreate() == tracker.convert("01.01.70 12:00:00"));
    }
}
