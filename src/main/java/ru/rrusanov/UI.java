package ru.rrusanov;
import ru.rrusanov.models.Item;
/**
 * UI for user interaction.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 24.01.17
 */
public class UI {
    /**
     * Use to define what item menu user has selected.
     */
    private String userChoose = "-1";
    /**
     * reference to item(user selected use console.ask()) that be updated or deleted.
     */
    private Item itemToAction;
    /**
     * reference to item array(user selected use console.ask()) that be updated or deleted.
     */
    private Item[] itemsArrayToAction;
    /**
     * To notify user what the item was removed from tracker.
     */
    private String deletedItem;
    /**
     * Setter.
     * @param deletedItem Item what be removed.
     */
    public void setDeletedItem(String deletedItem) {
        this.deletedItem = deletedItem;
    }
    /**
     * Getter.
     * @return eletedItem Item what be removed.
     */
    public String getDeletedItem() {
        return this.deletedItem;
    }
    /**
     * Getter.
     * @return  Get what char user selected in menu list.
     */
    public String getUserChoose() {
        return this.userChoose;
    }
    /**
     * Setter.
     * @param userChoose Set what char user selected in menu list.
     */
    public void setUserChoose(String userChoose) {
        this.userChoose = userChoose;
    }
    /**
     * Getter.
     * @return Get reference to item what user selected use console.
     */
    public Item getItemToAction() {
        return this.itemToAction;
    }
    /**
     * Setter.
     * @param itemToAction Set reference to item what user selected use console.
     */
    public void setItemToAction(Item itemToAction) {
        this.itemToAction = itemToAction;
    }
    /**
     * Getter.
     * @return Get reference to array item what user selected use console.
     */
    public Item[] getItemsArrayToAction() {
        return this.itemsArrayToAction;
    }
    /**
     * Setter.
     * @param itemsArrayToAction Set reference to array item what user selected use console.
     */
    public void setItemsArrayToAction(Item[] itemsArrayToAction) {
        this.itemsArrayToAction = itemsArrayToAction;
    }
    /**
     * Print base menu.
     */
    public void printMenu() {
        System.out.printf("%nMain menu%n"
                + "---------%n"
                + "1 - add new item%n"
                + "2 - find item by ID field%n"
                + "3 - find item by name field%n"
                + "4 - update exist item%n"
                + "5 - delete item%n"
                + "6 - print all item%n"
                + "x - exit%n");
    }
    /**
     * The interface for dialogue with the user.
     * @param input console or stub input
     */
    public void mainMenuToConsole(Input input) {
        /**
         * instance tracker for items
         */
        Tracker tracker = new Tracker();
        /**
         * instance interact with user, get from user string in console.
         */
        // Cycle for main menu(to exit user must enter 'x' char at the main menu)
        while (!this.getUserChoose().equals("x")) {
            this.printMenu();
            this.setUserChoose(input.ask("Please choose action, and enter selected action item"));
            // User choose add new user
            if (this.getUserChoose().equals("1")) {
                System.out.printf("%nadd new item%n------------");
                tracker.add(new Item(input.ask("Enter name for new item "),
                        input.ask("Enter description "),
                        System.currentTimeMillis(),
                        input.ask("Enter comments ")));
            // User choose search item use ID item.
            } else if (this.getUserChoose().equals("2")) {
                System.out.printf("%nfind item by ID field%n---------------------");
                tracker.printToConsoleItem(tracker.findById(input.ask("Enter ID to find ")));
            // User choose search item use name item.
            } else if (this.getUserChoose().equals("3")) {
                System.out.printf("%nfind item by Name field%n-----------------------");
                tracker.printToConsoleItem(tracker.findByName(input.ask("Enter name to find ")));
            // User choose update specific item(s).
            } else if (this.getUserChoose().equals("4")) {
                System.out.printf("%nupdate exist item(s)%n"
                        + "---------------------%n"
                        + "1 - by ID%n"
                        + "2 - by name%n"
                        + "3 - by create time%n");
                this.setUserChoose(input.ask("Please choose action, and enter selected action item"));
                // User choose update specific item(s) for search use ID field.
                if (this.getUserChoose().equals("1")) {
                    this.setItemToAction(tracker.findById(input.ask("Enter exist ID to search in items ")));
                    tracker.fieldsUpdate(this.getItemToAction());
                    tracker.update(this.getItemToAction());
                    System.out.printf("%nItem ID: %s  successfully updated!", this.getItemToAction().getId());
                    tracker.printToConsoleItem(this.getItemToAction());
                // User choose update specific item(s) for use name field.
                } else if (this.getUserChoose().equals("2")) {
                    this.setItemsArrayToAction(tracker.findByName(input.ask("Enter exist name to search in items ")));
                    for (Item item : this.getItemsArrayToAction()) {
                        tracker.fieldsUpdate(item);
                        System.out.printf("%nItem ID: %s  successfully updated!", item.getId());
                        tracker.printToConsoleItem(item);
                    }
                }
                // User choose update specific date and time for search item(s).
                if (this.getUserChoose().equals("3")) {
                    this.setItemsArrayToAction(tracker.findByCreate(input.ask("Enter date and time exist item(s) (31.12.70 23:59:59) ")));
                    for (Item item : this.getItemsArrayToAction()) {
                        tracker.fieldsUpdate(item);
                        System.out.printf("%nItem ID: %s  successfully updated!", item.getId());
                        tracker.printToConsoleItem(item);
                    }
                }
            // User choose delete specific item(s).
            } else if (this.getUserChoose().equals("5")) {
                System.out.printf("%ndelete exist item(s)%n"
                        + "---------------------%n"
                        + "1 - by ID%n"
                        + "2 - by name%n"
                        + "3 - by create time%n");
                this.setUserChoose(input.ask("Please choose action, and enter selected action item"));
                // User choose delete specific item(s) for search use ID field.
                if (this.getUserChoose().equals("1")) {
                    this.setItemToAction(tracker.findById(input.ask("Enter exist ID to delete in items ")));
                    this.setDeletedItem(this.getItemToAction().getId());
                    tracker.delete(this.getItemToAction());
                    System.out.printf("%nItem ID: %s  successfully deleted!%n", this.getDeletedItem());
                // User choose delete specific item(s) for search use ID field.
                } else if (this.getUserChoose().equals("2")) {
                    this.setItemsArrayToAction(tracker.findByName(input.ask("Enter exist name to delete item(s) ")));
                    for (Item item : this.getItemsArrayToAction()) {
                        this.setDeletedItem(item.getName());
                        tracker.delete(item);
                        System.out.printf("%nItem name: %s  successfully deleted!%n", this.getDeletedItem());
                    }
                }
                // User choose update specific date and time for search item(s).
                if (this.getUserChoose().equals("3")) {
                    this.setItemsArrayToAction(tracker.findByCreate(input.ask("Enter date and time exist item(s) for deleting (31.12.70 23:59:59) ")));
                    for (Item item : this.getItemsArrayToAction()) {
                        this.setDeletedItem(tracker.convert(item.getCreate()));
                        tracker.delete(item);
                        System.out.printf("%nItem created: %s  successfully deleted!%n", this.getDeletedItem());
                    }
                }
            // User choose print all items to console.
            } else if (this.getUserChoose().equals("6")) {
                for (Item item : tracker.findAll()) {
                    tracker.printToConsoleItem(item);
                }
            }
        }
    }
}
