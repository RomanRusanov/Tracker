package ru.rrusanov;

/**
 * Class define with methods .
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 08.06.17
 */
abstract class BaseActions implements UserActions {
    /**
     * Constructor get parameter key(unique id action in array of actions), name(title action for menu).
     * @param name name(title action for menu
     */
    BaseActions(String name) {
        this.name = name;
    }
    /**
     * name(title action for menu).
     */
    private String name;
    /**
     * Getter for name field.
     * @return  name title action for menu
     */
    public String getName() {
        return this.name;
    }

    /**
     * Print to console comment for action.
     * @return String to console id and comment for action.
     */
    public String info() {
        return String.format("%s%n", this.name);
    }
}
