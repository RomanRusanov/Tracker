package ru.rrusanov;

import ru.rrusanov.models.Item;

import java.util.ArrayList;

/**
 * Interface describes tracker behavior.
 */
public interface ITracker {
    /**
     * The methods add item to the items collection.
     * @param item instance of item
     * @return new item
     */
    Item add(Item item);
    /**
     * The method takes an item to update the id field it finds in the items collection.
     * @param itemUpdate Item to need update.
     */
    void update(Item itemUpdate);
    /**
     * The method takes an item to delete, the id field it finds in the items collection,
     * founded item are remove from collection.
     * @param item Item to delete
     */
    void delete(Item item);
    /**
     * The Method return all items.
     * @return items collection.
     */
    ArrayList<Item> findAll();
    /**
     * The method takes a string and looks for it in the items collection by field name,
     * returns the other collection with items has this string.
     * @param key String for find in array
     * @return ArrayList with mach items by name field.
     */
    ArrayList<Item> findByName(String key);
    /**
     * The method takes a string and looks for it in the items array by field id,
     * returns the item which has this string.
     * @param id String id item to search
     * @return math item with id
     */
    Item findById(String id);
    /**
     * Print items to console.
     * @param item Items to print.
     */
    void printToConsoleItem(ArrayList<Item> item);
    /**
     * Update fields (name, description, date time, comment) of new value.
     * @param item item for update
     * @param input input data
     */
    void fieldsUpdate(Item item, Input input);
}
