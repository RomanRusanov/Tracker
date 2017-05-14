package ru.rrusanov;

/**
 * Class Run UI with Menu.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 09.02.17
 */
public class StartUI {
    /**
     * Input interface get data.
     */
    private Input input;
    /**
     * Tracker instance contain all items.
     */
    private Tracker tracker;
    /**
     * Possible range of menu tracker.
     */
    private int[] range;
    /**
     * Constructor encapsulate tracker and input.
     * @param input Input interface get data.
     * @param tracker Tracker inatance contain items.
     */
    public StartUI(Input input, Tracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }

    /**
     * Initialize Menu.
     */
    public void init() {
        MenuTracker menu = new MenuTracker(this.tracker, this.input);
        menu.fillActions();
        range = menu.getActionsRange();
        do {
            menu.show();
            menu.select(this.input.ask("Select", range));
        } while (!"y".equals(this.input.ask("Exit?(y)")));
    }
    /**
     * main method.
     * @param args arguments
     */
    public static void main(String[] args) {
        Input input = new ValidateInput();
        new StartUI(input, new Tracker()).init();

    }
}