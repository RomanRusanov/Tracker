/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 11.01.17
 *
 * Class Tracker contain items.
 * The Methods:
 *  Item add(Item) The methods add item to the items array, index increment for every elements.
 *  Item findById(String id) The method takes a string and looks for it in the items array by field id, returns the item which has this string.
 *  void update(Item itemUpdate) The method takes an item to update the id field it finds in the items array.
 *  void delete(Item itemDelete) The method takes an item to delete, the id field it finds in the items array, finded item are assigned null.
 *  Item[] findAll() The Method return array all items, exluding null items.
 *  Item[] findByName(String key) The method takes a string and looks for it in the items array by field name, returns the array with items has this string.
 *
 * Input.java The interface describes the input.
 *
 */
package ru.rrusanov;