package ru.rrusanov;

/**
 * Class Check input valid.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 10.05.17
 */
public class ValidateInput extends ConsoleInput {
    /**
     * @param question String.
     * @param range The possible range the array of values.
     * @return Input int.
     */
    public int ask(String question, int[] range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = super.ask(question, range);
                invalid = false;
            } catch (MenuOutException moe) {
                System.out.println("Please select key from menu range. ");
            } catch (NumberFormatException nfe) {
                System.out.println("Please enter valid data, again! ");
            }
        } while (invalid);
        return value;
    }
}
