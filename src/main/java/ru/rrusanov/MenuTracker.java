package ru.rrusanov;
import ru.rrusanov.models.Item;

import java.util.ArrayList;
/**
 * Menu for user interaction.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 23.04.17
 */
public class MenuTracker {
    /**
     * Base class Tracker.
     */
    private Tracker tracker;
    /**
     * Input interface get data.
     */
    private Input input;
    /**
     * Contain all actions for tracker items.
     */
    private ArrayList<UserActions> actions = new ArrayList<>();
    /**
     * Constructor encapsulate tracker and input.
     * @param tracker Base class Tracker.
     * @param input Input interface get data.
     */
    public MenuTracker(Tracker tracker, Input input) {
        this.tracker = tracker;
        this.input = input;
    }
    /**
     * Getter return int value of menu range.
     * @return int value.
     */
    public ArrayList<Integer> getActionsRange() {
        ArrayList<Integer> actionRange = new ArrayList<>();
        for (int i = 0; i < actions.size(); i++) {
            actionRange.add(i);
        }
        return actionRange;
    }

    /**
     * Initialize Menu actions, add actions to list.
     */
    public void fillActions() {
        this.actions.add(new AddItem("0. Add new item."));
        this.actions.add(new ShowItems("1. Show all items."));
        this.actions.add(new EditItem("2. Edit item."));
        this.actions.add(new DeleteItem("3. Delete item."));
        this.actions.add(new SearchItem("4. Search item."));
    }

    /**
     * Adding action to array of actions for tracker menu.
     * @param action action to be added to array of action
     */
    public void addActions(UserActions action) {
        this.actions.add(action);
    }

    /**
     * User choose action.
     * @param key unique value of action.
     */
    public void select(int key) {
        this.actions.get(key).execute(input, tracker);
    }

    /**
     * Show menu with actions list.
     */
    public void show() {
        this.actions.forEach((nameAction) -> System.out.print(nameAction.info()));
    }

    /**
     * Inner class add item to tracker.
     */
    private class AddItem extends BaseActions {

        /**
         * Constructor get parameter key, name.
         * @param name title action for menu
         */
        AddItem(String name) {
            super(name);
        }

        /**
         * Define what action should be made. Add item to tracker.
         * @param input Input from user
         * @param tracker Main container.
         */
        public void execute(Input input, Tracker tracker) {
            String name = input.ask("Enter the task name for new item:");
            String desc = input.ask("Enter the description for new item:");
            String comm = input.ask("Enter the comment for new item:");
            tracker.add(new Item(name, desc, System.currentTimeMillis(), comm));
        }
    }

    /**
     * Inner class show items that tracker contains.
     */
    private class ShowItems extends BaseActions {

        /**
         * Constructor get parameter key, name.
         * @param name name(title action for menu).
         */
        ShowItems(String name) {
            super(name);
        }

        /**
         * Define what action should be made. Show items that tracker contains.
         * @param input Input from user
         * @param tracker Main container.
         */
        public void execute(Input input, Tracker tracker) {
            tracker.printToConsoleItem(tracker.findAll());
        }
    }

    /**
     * Inner class edit fields of the item.
     */
    private class EditItem extends BaseActions {

        /**
         * Constructor get parameter key, name.
         *
         * @param name name(title action for menu).
         */
        EditItem(String name) {
            super(name);
        }

        /**
         * Define what action should be made. Edit fields of the item.
         *
         * @param input   Input from user
         * @param tracker Main container.
         */
        public void execute(Input input, Tracker tracker) {
            String itemEditID = input.ask("Enter ID item to edit:");
            Item editItem = tracker.findById(itemEditID);
            if (editItem != null) {
                tracker.fieldsUpdate(editItem, input);
            } else {
                System.out.println("Item ID:" + itemEditID + " not found, please enter correct ID. ");
            }
        }
    }
    /**
     * Inner class delete item from the tracker.
     */
    private class DeleteItem extends BaseActions {

        /**
         * Constructor get parameter key, name.
         * @param name name(title action for menu).
         */
        DeleteItem(String name) {
            super(name);
        }

        /**
         * Define what action should be made. Delete item from the tracker.
         * @param input Input from user
         * @param tracker Main container.
         */
        public void execute(Input input, Tracker tracker) {
            String itemDeleteID = input.ask("Enter ID item to delete:");
            Item deleteItem = tracker.findById(itemDeleteID);
            if (deleteItem != null) {
                tracker.delete(deleteItem);
                System.out.println("Item ID:" + itemDeleteID + " success deleted.");
            } else {
                System.out.println("Item ID:" + itemDeleteID + " not found, please enter correct ID. ");
            }
        }
    }

    /**
     * Inner class search item from the tracker use id filed.
     */
    private class SearchItem extends BaseActions {
        /**
         * Constructor get parameter key, name.
         * @param name name(title action for menu).
         */
        SearchItem(String name) {
            super(name);
        }

        /**
         * Define what action should be made. Search item from the tracker use id filed.
         * @param input Input from user
         * @param tracker Main container.
         */
        public void execute(Input input, Tracker tracker) {
            ArrayList<Item> searchItem = tracker.findByName(input.ask("Enter name the item to search:"));
            tracker.printToConsoleItem(searchItem);
        }
    }
}
