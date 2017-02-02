package ru.rrusanov;

import ru.rrusanov.models.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.util.Arrays.copyOf;

/**
 * Class store items, can added new items, find item by id.
 * Update item properties, delete item, search all items in tracker,
 * search item by name.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 10.01.17
 */
public class Tracker {
    /**
     * @{value} int - indexItems count all instance items in tracker
     * @{value} Item[] - items store all instance in tracker
     */
    private Item[] items = new Item[10];
    /**
     * @{value} indexItems int index for array elements of items
     */
    private int indexItems = 0;
    /**
     * The methods add item to the items array, index increment for every elements.
     * @param item instance of item
     * @return new item
     */
    public Item add(Item item) {
        this.items[indexItems++] = item;
        return item;
    }
    /**
     * The method takes a string and looks for it in the items array by field id,
     * returns the item which has this string.
     * @param id String id item to search
     * @return math item with id
     */
    public Item findById(String id) {
        Item result = null;
        for (Item item : items) {
            if (item != null && item.getId().equals(id)) {
                result = item;
                break;
            }
        }
        return result;
    }
    /**
     * The method takes a string and looks for it in the items array by field name,
     * returns the array with items has this string.
     * @param key String for find in array
     * @return array Item[] with mach key string
     */
    public Item[] findByName(String key) {

        Item[] resultItemArray = new Item[items.length];
        int indexResultArray = 0;
        for (Item item : items) {
            if (item != null && item.getName() != null && item.getName().equals(key)) {
                resultItemArray[indexResultArray] = item;
                indexResultArray++;
            }
        }
        return copyOf(resultItemArray, indexResultArray);
    }
    /**
     * The method takes an item to update the id field it finds in the items array.
     * @param itemUpdate Item to need update
     */
    public void update(Item itemUpdate) {
        for (int i = 0; i < items.length - 1; i++) {
            if (items[i] != null && itemUpdate.getId().equals(items[i].getId())) {
                items[i] = itemUpdate;
                break;
            }
        }
    }
    /**
     * The method takes an item to delete, the id field it finds in the items array,
     * finded item are assigned null.
     * @param itemDelete Item to delete
     */
    public void delete(Item itemDelete) {
        for (int i = 0; i < items.length - 1; i++) {
            if (items[i] != null && itemDelete.getId().equals(items[i].getId())) {
                items[i] = null;
                break;
            }
        }
    }
    /**
     * The Method return array all items, exluding null items.
     * @return Item[] - items array
     */
    public Item[] findAll() {

        Item[] resultItemArray = new Item[items.length];
        int indexResultArray = 0;
        for (Item item : items) {
            if (item != null) {
                resultItemArray[indexResultArray] = item;
                indexResultArray++;
            }
        }
        return copyOf(resultItemArray, indexResultArray);
    }

    /**
     * printed item to console.
     * @param item item for print
     */
    public void printToConsoleItem(Item item) {
        System.out.printf("%n---------------------------------------%n "
                          + "ID: %s%n "
                          + "name: %s%n "
                          + "description: %s%n "
                          + "create time: %s%n "
                          + "comment: %s%n"
                          + "---------------------------------------%n", item.getId(),
                                                                       item.getName(),
                                                                       item.getDescription(),
                                                                       convert(item.getCreate()),
                                                                       item.getComment());
    }
    /**
     * print to console array items.
     * @param item array items
     */
    public void printToConsoleItem(Item[] item) {
        for (Item i:item) {
            System.out.printf("%n---------------------------------------%n "
                            + "ID: %s%n "
                            + "name: %s%n "
                            + "description: %s%n "
                            + "create time: %s%n "
                            + "comment: %s%n"
                            + "---------------------------------------%n", i.getId(),
                                                                         i.getName(),
                                                                         i.getDescription(),
                                                                         convert(i.getCreate()),
                                                                         i.getComment());
        }
    }
    /**
     * update filds (name, description, date time, comment) of new value.
     * @param item item for update
     */
    public void fieldsUpdate(Item item) {
        Input consoleInput = new ConsoleInput();
        item.setName(consoleInput.ask("Enter new name "));
        item.setDescription(consoleInput.ask("Enter new description "));
        item.setCreate(convert(consoleInput.ask("Enter new date and time(31.12.1970 23:59:59) ")));
        item.setComment(consoleInput.ask("Enter new comment "));
    }
    /**
     * search in tracker items with math date and time.
     * @param userInputDate user selected date and time to search
     * @return array item match value
     */
    public Item[] findByCreate(String userInputDate) {
        Item[] result = new Item[items.length];
        int countFindItem = 0;
        for (Item item : items) {
            if (item != null && item.getCreate() != 0L && item.getCreate() == convert(userInputDate)) {
                result[countFindItem] = item;
                countFindItem++;
                //printToConsoleItem(item);
            }
        }
        return copyOf(result, countFindItem);
    }
    /**
     * convert value millisecond to string date and time example (31.12.1970 23:59:59).
     * @param millis vaule in milliseconds
     * @return string date and time "31.12.1970 23:59:59"
     */
    public String convert(long millis) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("Russia/Moscow"));
        cal.setTimeInMillis(millis);
        final String timeString = new SimpleDateFormat("dd.MM.yy HH:mm:ss").format(cal.getTime());
        return timeString;
    }
    /**
     * convert string value date and time "31.12.1970 23:59:59" to millisecond value.
     * @param userInputDate string "31.12.1970 23:59:59"
     * @return long in milliseconds
     */
    public long convert(String userInputDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        Date date = new Date();

        try {
            date = simpleDateFormat.parse(userInputDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }
}