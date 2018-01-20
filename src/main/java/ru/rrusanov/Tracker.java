package ru.rrusanov;

import ru.rrusanov.models.Item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

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
     * @{value} ArrayList - items store all instance in tracker
     */
    private ArrayList<Item> items = new ArrayList<>();
    /**
     * The methods add item to the items collection.
     * @param item instance of item
     * @return new item
     */
    public Item add(Item item) {
        this.items.add(item);
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
     * The method takes a string and looks for it in the items collection by field name,
     * returns the other collection with items has this string.
     * @param key String for find in array
     * @return ArrayList with mach items by name field.
     */
    public ArrayList<Item> findByName(String key) {

        ArrayList<Item> resultItemArray = new ArrayList<>();
        for (Item item : items) {
            if (item != null && item.getName() != null && item.getName().equals(key)) {
                resultItemArray.add(item);
            }
        }
        return resultItemArray;
    }
    /**
     * The method takes an item to update the id field it finds in the items collection.
     * @param itemUpdate Item to need update.
     */
    public void update(Item itemUpdate) {
        for (Item item:items) {
            if (itemUpdate.getId().equals(item.getId())) {
                this.items.set(this.items.indexOf(item), itemUpdate);
                break;
            }
        }
    }
    /**
     * The method takes an item to delete, the id field it finds in the items collection,
     * founded item are remove from collection.
     * @param itemDelete Item to delete
     */
    public void delete(Item itemDelete) {
        this.items.remove(this.items.indexOf(itemDelete));
    }
    /**
     * The Method return all items.
     * @return items collection.
     */
    public ArrayList<Item> findAll() {
        return items;
    }

    /**
     * Print items to console.
     * @param item Items to print.
     */
    public void printToConsoleItem(ArrayList<Item> item) {
        for (Item i:item) {
            System.out.printf("---------------------------------------%n "
                            + "ID: %s%n "
                            + "name: %s%n "
                            + "description: %s%n "
                            + "create time: %s%n "
                            + "comment: %s%n"
                            + "---------------------------------------%n",
                    i.getId(),
                    i.getName(),
                    i.getDescription(),
                    convert(i.getCreate()),
                    i.getComment());
        }
    }
    /**
     * Update fields (name, description, date time, comment) of new value.
     * @param item item for update
     * @param input input data
     */
    public void fieldsUpdate(Item item, Input input) {
        String name = input.ask("Enter new name(or press Enter to leave old value):");
        if (!"".equals(name)) {
            item.setName(name);
        }

        String desc = input.ask("Enter new description(or press Enter to leave old value):");
        if (!"".equals(desc)) {
            item.setDescription(desc);
        }
        String create = input.ask("Enter new date and time(31.12.1970 23:59:59)(or press Enter to leave old value):");
        if (!"".equals(create)) {
            item.setCreate(convert(create));
        }

        String comm = input.ask("Enter new comment(or press Enter to leave old value):");
        if (!"".equals(comm)) {
            item.setComment(comm);
        }
    }
    /**
     * Search in tracker items with math date and time.
     * @param userInputDate user selected date and time to search
     * @return items match by create date.
     */
    public ArrayList<Item> findByCreate(String userInputDate) {
        ArrayList<Item> result = new ArrayList<>();
        for (Item item : items) {
            if (item != null && item.getCreate() != 0L && item.getCreate() == convert(userInputDate)) {
                result.add(item);
            }
        }
        return result;
    }
    /**
     * Convert value millisecond to string date and time example (31.12.1970 23:59:59).
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
     * Convert string value date and time "31.12.1970 23:59:59" to millisecond value.
     * @param userInputDate string "31.12.1970 23:59:59"
     * @return long in milliseconds
     */
    public long convert(String userInputDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss");
        Date date = new Date();
        boolean incorret = true;
        do {
            try {
                date = simpleDateFormat.parse(userInputDate);
                incorret = false;
            } catch (ParseException e) {
                System.out.println("Incorrect input!");
                userInputDate = new ConsoleInput().ask("Please enter correct(31.12.1970 23:59:59):");
            }
        } while (incorret);
        return date.getTime();
    }
}