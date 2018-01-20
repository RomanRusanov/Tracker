package ru.rrusanov;
import java.util.List;
import java.util.Scanner;
/**
 * A class implements an input from console.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 24.01.17
 */
public class ConsoleInput implements Input {
    /**
     * @{value} for capture system in.
     */
    private Scanner scanner = new Scanner(System.in);
    /**
     * input from console to string value.
     * @param question print message for user
     * @return string what user enter from console
     */
    public String ask(String question) {
        System.out.printf("%s ", question);
        return scanner.nextLine();
    }

    /**
     * @param question String.
     * @param range    The possible range the array of values.
     * @return Input int.
     */
    public int ask(String question, List<Integer> range) {
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