package ru.rrusanov;

/**
 * Class .
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 08.02.17
 */
public class StubInput  implements Input {
    /**
     * @{value} answers -  an array of strings which was obtained during initialization of the instance StubInput.
     */
    private String[] answers;
    /**
     * {@value} index - for array field StubInput.
     */
    private int index;

    /**
     * Constructor for StubInput. Write to field 'answers'
     * @param answers Array with strings
     */
    public StubInput(String[] answers) {
        this.answers = answers;
    }

    /**
     * Method return same string wich instance StubInput was initialized.
     * @param question String
     * @return String
     */
    public String ask(String question) {
        return this.answers[index++];
    }
}
