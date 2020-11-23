package ru.rrusanov.actions;


import ru.rrusanov.Input;
import ru.rrusanov.Tracker;
import ru.rrusanov.UserActions;
import ru.rrusanov.models.Item;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 23.11.2020
 * email roman9628@gmail.com
 * The class describe user action find item by id.
 */
public class FindByIdAction implements UserActions {

    /**
     * Define what action should be made.
     *
     * @param input   Input from user
     * @param tracker Main container.
     */
    @Override
    public void execute(Input input, Tracker tracker) {
        String id = input.ask("Enter ID item to find:");
        Item item = tracker.findById(id);
        if (item != null) {
            System.out.print(item.getId());
        } else {
            System.out.print("Item name:" + id + " not found, please enter correct ID.");
        }
    }

    /**
     * Print to console comment for this action.
     *
     * @return String to console comment for action.
     */
    @Override
    public String info() {
        return "FindById";
    }
}