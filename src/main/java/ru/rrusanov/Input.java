package ru.rrusanov;

/**
 * The interface describes the input.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 24.01.17
 */
public interface Input {
    /**
     *
     * @param question String
     * @return String
     */
    String ask(String question);
    /**
     * @param question String.
     * @param range The possible range the array of values.
     * @return Input int.
     */
    int ask(String question, int[] range);
}
