package ru.rrusanov.actions;

import ru.rrusanov.ITracker;
import ru.rrusanov.Input;
import ru.rrusanov.UserActions;
import ru.rrusanov.models.Item;

import java.util.ArrayList;

/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 23.11.2020
 * email roman9628@gmail.com
 * The class describe action find by name.
 */
public class FindByName implements UserActions {

    /**
     * Define what action should be made.
     *
     * @param input   Input from user
     * @param tracker Main container.
     */
    @Override
    public void execute(Input input, ITracker tracker) {
        String name = input.ask("Enter name item to find:");
        ArrayList<Item> items = tracker.findByName(name);
        if (items.size() > 0) {
            items.forEach((item) -> System.out.print(item.getName()));
        } else {
            System.out.print("Item name:" + name + " not found, please enter correct name.");
        }
    }

    /**
     * Print to console comment for this action.
     *
     * @return String to console comment for action.
     */
    @Override
    public String info() {
        return "FindByName";
    }
}