package ru.rrusanov.actions;

import ru.rrusanov.ITracker;
import ru.rrusanov.Input;
import ru.rrusanov.UserActions;
import ru.rrusanov.models.Item;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 23.11.2020
 * email roman9628@gmail.com
 * The class .
 */
public class DeleteAction implements UserActions {

    /**
     * Define what action should be made.
     *
     * @param input   Input from user
     * @param tracker Main container.
     */
    @Override
    public void execute(Input input, ITracker tracker) {
        String itemDeleteID = input.ask("Enter ID item to delete:");
        Item deleteItem = tracker.findById(itemDeleteID);
        if (deleteItem != null) {
            tracker.delete(deleteItem);
            System.out.print("Item ID:" + itemDeleteID + " success deleted.");
        } else {
            System.out.print("Item ID:" + itemDeleteID + " not found, please enter correct ID. ");
        }
    }

    /**
     * Print to console comment for this action.
     *
     * @return String to console comment for action.
     */
    @Override
    public String info() {
        return "Delete action";
    }
}