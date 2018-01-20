package ru.rrusanov;

import ru.rrusanov.models.Item;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;


/**
 * Test check out to console Menu.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 26.04.17
 */
public class MenuTrackerTest {
    /**
     * Test check menu range.
     */
    @Test
    public void thenMenuHaveFiveItemsWhenMethodGetActionsRangeReturnSameValue() {
        MenuTracker menuTracker = new MenuTracker(new Tracker(), new StubInput(new String[] {"y"}));
        menuTracker.fillActions();

        final int expect = 5;
        final int result = menuTracker.getActionsRange().size();
        assertThat(result, is(expect));
    }

    /**
     * Test Main menu.
     */
    @Test
    public void thenInvokeShowMenuWhenMenuPrintToConsole() {
        final String END_LINE = System.getProperty("line.separator");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);

        MenuTracker menuTracker = new MenuTracker(new Tracker(), new StubInput(new String[] {"y"}));
        menuTracker.fillActions();
        menuTracker.show();

        final String result = byteArrayOutputStream.toString();
        final String expect = "0. Add new item." + END_LINE
                + "1. Show all items." + END_LINE
                + "2. Edit item." + END_LINE
                + "3. Delete item." + END_LINE
                + "4. Search item." + END_LINE;
        assertThat(result, is(expect));
    }
    /**
     * Test Inner class AddItem.
     */
    @Test
    public void thenItemAddedWhenTrackerNotEmpty() {
        Tracker tracker = new Tracker();
        MenuTracker menuTracker = new MenuTracker(tracker, new StubInput(new String[] {"Test Name!", "1", "1"}));
        menuTracker.fillActions();
        menuTracker.select(0);
        ArrayList<Item> result = tracker.findByName("Test Name!");

        String resultName = result.get(0).getName();
        String expectName = "Test Name!";
        assertThat(resultName, is(expectName));
    }
    /**
     * Test Inner class EditItem.
     */
    @Test
    public void thenItemEditedWhenItemsFieldsChange() {
        Tracker tracker = new Tracker();
        MenuTracker menuTrackerAddAction = new MenuTracker(tracker, new StubInput(new String[] {"Added Name!", "1", "1"}));
        menuTrackerAddAction.fillActions();
        menuTrackerAddAction.select(0);

        ArrayList<Item> itemToFind = tracker.findByName("Added Name!");
        String idItemToEdit = itemToFind.get(0).getId();
        MenuTracker menuTrackerEditAction = new MenuTracker(tracker, new StubInput(new String[] {idItemToEdit, "Edited Name!", "", "", ""}));
        menuTrackerEditAction.fillActions();
        menuTrackerEditAction.select(2);
        ArrayList<Item> result = tracker.findByName("Edited Name!");

        String resultName = result.get(0).getName();
        String expectName = "Edited Name!";
        assertThat(resultName, is(expectName));
    }
    /**
     * Test Inner class EditItem if ID enter incorrect.
     */
    @Test
    public void thenItemToEditEnterIncorrectWhenPrintMessage() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("Correct"));
        final String END_LINE = System.getProperty("line.separator");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);

        MenuTracker menuTracker = new MenuTracker(new Tracker(), new StubInput(new String[]{"Wrong"}));
        menuTracker.fillActions();
        menuTracker.select(2);

        final String expect = byteArrayOutputStream.toString();
        final String result = "Item ID:Wrong not found, please enter correct ID. " + END_LINE;
        assertThat(result, is(expect));
    }
    /**
     * Test Inner class SearchItem.
     */
    @Test
    public void thenNameToSearchWhenReturnItemIfExist() {
        Tracker tracker = new Tracker();
        MenuTracker menuTrackerAddAction = new MenuTracker(tracker, new StubInput(new String[] {"Find Name!", "1", "1"}));
        menuTrackerAddAction.fillActions();
        menuTrackerAddAction.select(0);

        ArrayList<Item> itemToFind = tracker.findByName("Find Name!");
        MenuTracker menuTrackerFindAction = new MenuTracker(tracker, new StubInput(new String[] {"Find Name"}));
        menuTrackerFindAction.fillActions();
        menuTrackerFindAction.select(4);
        String result = itemToFind.get(0).getId();

        assertNotNull(result);
    }
    /**
     * Test Inner class DeleteItem.
     */
    @Test
    public void thenItemDeleteWhenItemNotExist() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("Must be deleted"));

        Item itemBeforeDelete = tracker.findById("Must be deleted");
        MenuTracker menuTrackerFindAction = new MenuTracker(tracker, new StubInput(new String[] {"Must be deleted"}));
        menuTrackerFindAction.fillActions();
        menuTrackerFindAction.select(3);
        Item itemAfterDelete = tracker.findById("Must be deleted");

        assertNotSame(itemAfterDelete, itemBeforeDelete);

    }
    /**
     * Test Inner class DeleteItem if ID enter incorrect.
     */
    @Test
    public void thenItemToDeleteEnterIncorrectWhenPrintMessage() {
        Tracker tracker = new Tracker();
        tracker.add(new Item("Correct"));
        final String END_LINE = System.getProperty("line.separator");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        System.setOut(printStream);

        MenuTracker menuTracker = new MenuTracker(new Tracker(), new StubInput(new String[] {"Wrong"}));
        menuTracker.fillActions();
        menuTracker.select(3);

        final String expect = byteArrayOutputStream.toString();
        final String result = "Item ID:Wrong not found, please enter correct ID. " + END_LINE;
        assertThat(result, is(expect));
    }
}