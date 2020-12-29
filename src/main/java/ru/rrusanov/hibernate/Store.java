package ru.rrusanov.hibernate;

import java.util.List;
/**
 * Interface describes store for tracker behavior.
 */
public interface Store {
    /**
     * The methods add item to the items collection.
     * @param item instance of item
     * @return new item
     */
    Item add(Item item);
    /**
     * The method takes an item to update the id field it finds in the items collection.
     * @param item Item to need update.
     * @param id id item.
     * @return if replace true, otherwise false.
     */
    boolean replace(String id, Item item);
    /**
     * The method takes an item to delete, the id field it finds in the items collection,
     * founded item are remove from collection.
     * @param id id item.
     * @return if delete true, otherwise false.
     */
    boolean delete(String id);
    /**
     * The Method return all items.
     * @return items collection.
     */
    List<Item> findAll();
    /**
     * The method takes a string and looks for it in the items collection by field name,
     * returns the other collection with items has this string.
     * @param key String for find in array
     * @return ArrayList with mach items by name field.
     */
    List<Item> findByName(String key);
    /**
     * The method takes a string and looks for it in the items array by field id,
     * returns the item which has this string.
     * @param id String id item to search
     * @return math item with id
     */
    Item findById(String id);
}