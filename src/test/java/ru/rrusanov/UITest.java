package ru.rrusanov;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import ru.rrusanov.models.Item;

import static org.hamcrest.core.Is.is;

/**
 * Class .
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 09.02.17
 */
public class UITest {
    /**
     * New line separator.
     */
    private static final String NEWLINE = System.getProperty("line.separator");

    /**
     * Capture console output.
     */
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    /**
     * Test contain item must be deleted.
     */

    @Test
    public void thenSetDeletedItemWhenReturnThem() {
        UI ui = new UI();
        ui.setDeletedItem("122334");
        final String result = ui.getDeletedItem();
        final String expect = "122334";
        Assert.assertThat(result, is(expect));
    }

    /**
     * Test contain string that user input from console or input from stub input.
     */
    @Test
    public void whenSetUserChooseWhenReturnThem() {
        UI ui = new UI();
        ui.setUserChoose("1");
        final String result = ui.getUserChoose();
        final String expect = "1";
        Assert.assertThat(result, is(expect));
    }

    /**
     * Test contain reference to item to action(find, delete, update...).
     */
    @Test
    public void thenSetItemToActionWhenReturnThem() {
        UI ui = new UI();
        final Item expect = new Item();
        ui.setItemToAction(expect);
        final Item result = ui.getItemToAction();
        Assert.assertSame(result, expect);
    }

    /**
     * Test contains.
     */
    @Test
    public void thenSetItemsArrayToActionWhenReturnThem() {
        UI ui = new UI();
        final Item[] expect = {new Item(), new Item(), new Item()};
        ui.setItemsArrayToAction(expect);
        final Item[] result = ui.getItemsArrayToAction();
        Assert.assertSame(result, expect);
    }
    /**
     * Test print to console menu for user.
     */
    @Test
    public void thenPrintMenuWhenReturnThem() {
        UI ui = new UI();
        ui.mainMenuToConsole(new StubInput(new String[] {"x"}));
        final String expect = NEWLINE + "Main menu"
                + NEWLINE + "---------"
                + NEWLINE + "1 - add new item"
                + NEWLINE + "2 - find item by ID field"
                + NEWLINE + "3 - find item by name field"
                + NEWLINE + "4 - update exist item"
                + NEWLINE + "5 - delete item"
                + NEWLINE + "6 - print all item"
                + NEWLINE + "x - exit" + NEWLINE;
        final String result = systemOutRule.getLog();
        Assert.assertThat(result, is(expect));
        systemOutRule.clearLog();
    }
}