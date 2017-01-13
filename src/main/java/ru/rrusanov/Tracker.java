package ru.rrusanov;

import ru.rrusanov.models.Item;
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
}