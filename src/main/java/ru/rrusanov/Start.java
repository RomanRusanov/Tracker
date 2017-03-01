package ru.rrusanov;

/**
 * Class Run UI with Tracker.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 09.02.17
 */
public class Start {
    /**
     * PVSM method.
     * @param args arguments
     */
    public static void main(String[] args) {
        UI ui = new UI();
        ui.mainMenuToConsole(new ConsoleInput());

    }
}
