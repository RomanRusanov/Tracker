package ru.rrusanov;

/**
 * Class need for check console input.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 08.02.17
 */
public class StubInput  implements Input {
    /**
     * answers -  an array of strings which was obtained during initialization of the instance StubInput.
     */
    private String[] answers;
    /**
     * {@value} index - for array field StubInput.
     */
    private int index;

    /**
     * Constructor for StubInput. Write String[] to field 'answers'.
     * @param answers Array with type string.
     */
    public StubInput(String[] answers) {
        this.answers = answers;
    }
    /**
     * Method return same string wich instance StubInput was initialized.
     * @param question String
     * @return Stringin
     */
    public String ask(String question) {
        return this.answers[index++];
    }

    /**
     * @param question String.
     * @param range    The possible range the array of values.
     * @return Input int.
     */
    public int ask(String question, int[] range) {
        int key = Integer.valueOf(this.ask(question));
        boolean exist = false;
        for (int value: range) {
            if (value == key) {
                exist = true;
                break;
            }
        }
        if (exist) {
            return key;
        } else {
            throw new MenuOutException("Out menu range. ");
        }
    }
}
