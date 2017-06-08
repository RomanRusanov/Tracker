package ru.rrusanov;

/**
 * Possibly data entry error.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 10.05.17
 */
public class MenuOutException extends RuntimeException {
    /**
     * Construcor return to parent string with exception.
     * @param msg String.
     */
    public MenuOutException(String msg) {
        super(msg);
    }
}
