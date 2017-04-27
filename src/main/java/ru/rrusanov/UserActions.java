package ru.rrusanov;

/**
 * This interface define witch implementation be at each menu action.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 23.04.17
 */
public interface UserActions {
    /**
     * This key unique for each actions.
     * @return unique value in arrayActions.
     */
    int key();

    /**
     * Define what action should be made.
     * @param input Input from user
     * @param tracker Main container.
     */
    void execute(Input input, Tracker tracker);

    /**
     * Print to console comment for this action.
     * @return String to console comment for action.
     */
    String info();
}
