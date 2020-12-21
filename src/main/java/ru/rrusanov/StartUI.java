package ru.rrusanov;

/**
 * Class Run UI with Menu.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 26.02.19
 */
public class StartUI {
    /**
     * Input interface get data.
     */
    private Input input;
    /**
     * Tracker instance contain all items.
     */
    private ITracker tracker;
    /**
     * Constructor encapsulate tracker and input.
     * @param input Input interface get data.
     * @param tracker Tracker instance contain items.
     */
    public StartUI(Input input, ITracker tracker) {
        this.input = input;
        this.tracker = tracker;
    }
    /**
     * Initialize Menu.
     */
    public void init() {
        MenuTracker menu = new MenuTracker(this.tracker, this.input);
        menu.fillActions();
        do {
            menu.show();
            menu.select(this.input.ask("Select", menu.getActionsRange()));
        } while (!"y".equals(this.input.ask("Exit?(y)")));
    }
    /**
     * main method.
     * @param args arguments
     */
    public static void main(String[] args) {
        Input input = new ValidateInput();
        try (TrackerSQL trackerSQL = new TrackerSQL()) {
            new StartUI(input, trackerSQL).init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}