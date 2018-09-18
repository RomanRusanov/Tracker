/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 12.01.17
 *
 * Tests for Tracker class.
 * The Methods:
 *  thenItemCreateWhenReturnItemLink() Checks the instantiated after constructor.
 *  thenItemIdFindWhenThisItemReturn() If the instance by this id exists in the array of the tracker to return it.
 *  thenItemUpdateWhenInItemNewLink() If an update of a pattern instance he changed the link.
 *  thenItemToDeleteFindWhenItemNull() If the instance to delete is found, its reference is null.
 *  thenFindAllWhenReturnArrayWithOutNullItems() Returns all items of an array excluding null.
 *  thenFindItemByNameWhenReturnItemsInArray() Verifies that the returned array contains only the items whose name passed to the method.
 *
 * Test for Item class.
 * The Methods:
 *  String getId() Getter for id field.
 *  String getName() Getter for name field.
 *  String getDescription() Getter for description field.
 *  long getCreate() Getter for create field.
 *  String getComment() Getter for comment field.
 *  void setId(String id) Setter for id field.
 *  void setName(String name) Setter for name field.
 *  void setDescription(String description) Setter for description field.
 *  void setCreate(long create) Setter for create field.
 *  void setComment(String comment) Setter for comment field.
 *  Item() Consructor for Item object.
 *  Item(String id) Consructor for Item object.
 *  Item(String name, String description, long create, String comment) Consructor for Item object.
 *  String generateId() Generate random id for item.
 *
 * Test for MenuTracker.java class.
 *  void whenActionAddedThenItExistInTracker() Test method addAction.
 *
 */
package ru.rrusanov;
