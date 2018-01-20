/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 11.01.17
 *
 * ConsoleInput.java Implements Input. Get data from user console input.
 * Methods:
 *  String ask(String question) The input from console to string value.
 *
 * Input.java The interface describes the input.
 *
 * MenuTracker.java Menu for user interaction.
 * Methods:
 *  void fillActions() Initialize Menu actions, add actions to list.
 *  void show() The show menu with actions list.
 * Inner class:
 *  AddItem implements UserActions. Add item to tracker.
 *  ShowItems implements UserActions. Show items that tracker contains.
 *  EditItem implements UserActions. Edit fields of the item.
 *  DeleteItem implements UserActions. Delete item from the tracker.
 *  SearchItem implements UserActions. Search item from the tracker use id filed.
 *
 * StartUI.java Run UI with Menu.
 * Methods:
 *  void init() Add instance the tracker, print menu.
 *  static void main(String[] args) Add instance the input, invoke init().
 *
 * StubInput  implements Input. Class need for test console input.
 * Methods:
 *  String ask(String question) Return one string value, that been takes(String[]) at initialize, at each invoke make the index of array +1.
 *
 * Tracker.java Contain items.
 * Methods:
 *  Item add(Item) The methods add item to the items collection.
 *  Item findById(String id) The method takes a string and looks for it in the items collection by field name,
 *      returns the other collection with items has this string.
 *  void update(Item itemUpdate) The method takes an item to update the id field it finds in the items collection.
 *  void delete(Item itemDelete) The method takes an item to delete, the id field it finds in the items collection,
 *      founded item are remove from collection.
 *  ArrayList<item> findAll() The Method return all items.
 *  ArrayList<item> findByName(String key) The method takes a string and looks for it in the items array by field name,
 *      returns the array with items has this string.
 *  void printToConsoleItem(ArrayList<Item> item) Printed item(s) to console.
 *  void fieldsUpdate(Item item, Input input) Update fields (name, description, date time, comment) of new value.
 *  ArrayList<Item> findByCreate(String userInputDate) Search in tracker items with math date and time.
 *  String convert(long millis) Convert value millisecond to string date and time example (31.12.1970 23:59:59).
 *  long convert(String userInputDate) Convert string value date and time "31.12.1970 23:59:59" to millisecond value.
 *
 * UI.java not worked old(first try) implementation Menu functionality.
 *
 * UserAction.java This interface define witch implementation be at each menu action.
 */
package ru.rrusanov;