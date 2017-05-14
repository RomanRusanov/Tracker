package ru.rrusanov;
import ru.rrusanov.models.Item;
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
    private UserActions[] actions = new UserActions[5];

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
    public int[] getActionsRange() {
        int length = this.actions.length;
        int[] range = new int[length];
        for (int i = 0; i < length; i++) {
            range[i] = i;
        }
        return range;
    }
    /**
     * Initialize Menu actions[].
     */
    public void fillActions() {
        this.actions[0] = new AddItem();
        this.actions[1] = new ShowItems();
        this.actions[2] = new EditItem();
        this.actions[3] = new DeleteItem();
        this.actions[4] = new SearchItem();
    }

    /**
     * User choose action.
     * @param key unique value of action.
     */
    public void select(int key) {
        this.actions[key].execute(this.input, this.tracker);
    }

    /**
     * Show menu with actions list.
     */
    public void show() {
        for (UserActions actions: this.actions) {
            if (actions != null) {
                System.out.print(actions.info());
            }
        }
    }

    /**
     * Inner class add item to tracker.
     */
    private class AddItem implements UserActions {
        /**
         * This key unique for each actions.
         * @return unique value in arrayActions.
         */
        public int key() {
            return 0;
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

        /**
         * Print to console comment for this action.
         * @return String to console comment for action.
         */
        public String info() {
            return String.format("%s. %s%n", this.key(), "Add the new item.");
        }
    }
    /**
     * Inner class show items that tracker contains.
     */
    private static class ShowItems implements UserActions {
        /**
         * This key unique for each actions.
         * @return unique value in arrayActions.
         */
        public int key() {
            return 1;
        }

        /**
         * Define what action should be made. Show items that tracker contains.
         * @param input Input from user
         * @param tracker Main container.
         */
        public void execute(Input input, Tracker tracker) {
            tracker.printToConsoleItem(tracker.findAll());
        }

        /**
         * Print to console comment for this action.
         * @return String to console comment for action.
         */
        public String info() {
            return String.format("%s. %s%n", this.key(), "Show all items.");
        }
    }
    /**
     * Inner class edit fields of the item.
     */
    private class EditItem implements UserActions {
        /**
         * This key unique for each actions.
         * @return unique value in arrayActions.
         */
        public int key() {
            return 2;
        }

        /**
         * Define what action should be made. Edit fields of the item.
         * @param input Input from user
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

        /**
         * Print to console comment for this action.
         * @return String to console comment for action.
         */
        public String info() {
            return String.format("%s. %s%n", this.key(), "Edit item.");
        }
    }
    /**
     * Inner class delete item from the tracker.
     */
    private class DeleteItem implements UserActions {
        /**
         * This key unique for each actions.
         * @return unique value in arrayActions.
         */
        public int key() {
            return 3;
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

        /**
         * Print to console comment for this action.
         * @return String to console comment for action.
         */
        public String info() {
            return String.format("%s. %s%n", this.key(), "Delete item.");
        }
    }
    /**
     * Inner class search item from the tracker use id filed.
     */
    private class SearchItem implements UserActions {
        /**
         * This key unique for each actions.
         * @return unique value in arrayActions.
         */
        public int key() {
            return 4;
        }

        /**
         * Define what action should be made. Search item from the tracker use id filed.
         * @param input Input from user
         * @param tracker Main container.
         */
        public void execute(Input input, Tracker tracker) {
            Item[] searchItem = tracker.findByName(input.ask("Enter name the item to search:"));
            tracker.printToConsoleItem(searchItem);
        }

        /**
         * Print to console comment for this action.
         * @return String to console comment for action.
         */
        public String info() {
            return String.format("%s. %s%n", this.key(), "Search item.");
        }
    }
}
