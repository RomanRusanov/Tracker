package ru.rrusanov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.rrusanov.models.Item;
import ru.rrusanov.reactive.Observable;
import ru.rrusanov.reactive.Observer;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.TimeZone;

/**
 * Class implements Tracker that use postgres database to store data.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 26.02.19
 */
public class TrackerSQL implements ITracker, AutoCloseable, Observable {
    /**
     * The field contain instance connection to database.
     */
    private Connection connection;
    /**
     * The filed contain instance logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(TrackerSQL.class.getName());
    /**
     * Observable state object.
     */
    private Item state;
    /**
     * The default constructor. Initiate connection to the database.
     */
    public TrackerSQL() {
        this.init();
    }
    /**
     * The constructor create instance Tracker with passed connection to db.
     * @param connection to db.
     */
    public TrackerSQL(Connection connection) {
        this.connection = connection;
    }
    /**
     * The field contain version for logger.
     */
    private int version = 1;
    /**
     * The method check connection to db.
     * @return true if connection exist, otherwise false.
     */
    public boolean init() {
       try (InputStream in = TrackerSQL.class.getClassLoader().getResourceAsStream("app.properties")) {
           Properties config = new Properties();
           config.load(in);
           Class.forName(config.getProperty("driver-class-name"));
           this.connection = DriverManager.getConnection(
                   config.getProperty("url"),
                   config.getProperty("username"),
                   config.getProperty("password")
           );
       } catch (Exception e) {
           throw new IllegalStateException(e);
       }
       return this.connection != null;
    }
    /**
     * Closes this resource, relinquishing any underlying resources.
     * This method is invoked automatically on objects managed by the
     * {try}-with-resources statement.
     */
    @Override
    public void close() {
        try {
            this.connection.close();
        } catch (Exception e) {
            LOG.error("Connection to DB not closed", version);
        }
    }
    /**
     * The methods add item to the database.
     * @param item instance of item
     * @return new item
     */
    @Override
    public Item add(Item item) {
        // insert item table.
        try (PreparedStatement ps = this.connection.prepareStatement(
                "insert into item (item_id, title, description, state_id, category_id, date_create) values (?, ?, ?, ?, ?, ?);")) {
            ps.setDouble(1, Double.parseDouble(item.getId()));
            ps.setString(2, item.getName());
            ps.setString(3, item.getDescription());
            ps.setInt(4, 1);
            ps.setInt(5, 1);
            ps.setTimestamp(6, new Timestamp(item.getCreate()));
            LOG.info("To item table row added: " + ps.executeUpdate());
        } catch (SQLException e) {
            LOG.error("SQL query insert into(Added element item table)", version);
        }
        //insert comments table.
        try (PreparedStatement ps = this.connection.prepareStatement(
                "insert into comments (item_id, comments) values (?, ?);")) {
            ps.setDouble(1, Double.parseDouble(item.getId()));
            ps.setString(2, item.getComment());
            LOG.info("To comments table row added: " + ps.executeUpdate());
        } catch (SQLException e) {
            LOG.error("SQL query insert into(Added element comments table)", version);
        }
        return item;
    }
    /**
     * The method replace fields data item.
     * @param id item id to correction.
     * @param item source data to insert.
     */
    public void replace(String id, Item item) {
        // update item table
        int numReplacedRows = 0;
        try (PreparedStatement psUpdateItem = this.connection.prepareStatement(
                "update item as i set title = ?, description = ?, date_create = ? where i.item_id = ?;")) {
            psUpdateItem.setString(1, item.getName());
            psUpdateItem.setString(2, item.getDescription());
            psUpdateItem.setTimestamp(3, new Timestamp(item.getCreate()));
            psUpdateItem.setDouble(4, Double.valueOf(id));
            numReplacedRows = psUpdateItem.executeUpdate();
            LOG.info("To item table row replaced: " + numReplacedRows);
        } catch (SQLException e) {
            LOG.error("SQL query update(replace element item table)", version);
        }
        // Check exist passed id in DB.
        if (numReplacedRows != 0) {
            // update comments table
            try (PreparedStatement psUpdateComments = this.connection.prepareStatement(
                    "update comments as c set comments = ? where c.item_id = ?;")) {
                psUpdateComments.setString(1, item.getComment());
                psUpdateComments.setDouble(2, Double.parseDouble(id));
                LOG.info("To comments table row replaced: " + psUpdateComments.executeUpdate());
            } catch (SQLException e) {
                LOG.error("SQL query update(replace element comments table)", version);
            }
        }
    }
    /**
     * The method delete item from db.
     * @param item Item to delete
     */
    @Override
    public void delete(Item item) {
        // delete comments
        try (PreparedStatement ps = this.connection.prepareStatement(
                "delete from comments where item_id = ?;")) {
            ps.setDouble(1, Double.valueOf(item.getId()));
            LOG.info("Rows deleted from comments table: " + ps.executeUpdate());
        } catch (SQLException e) {
            LOG.error("SQL query delete(remove element from item table)", version);
        }
        // delete item
        try (PreparedStatement ps = this.connection.prepareStatement(
                "delete from item where item_id = ?;")) {
            ps.setDouble(1, Double.valueOf(item.getId()));
            LOG.info("Rows deleted from item table: " + ps.executeUpdate());
        } catch (SQLException e) {
            LOG.error("SQL query delete(remove element from comments table)", version);
        }
    }
    /**
     * The Method return all items.
     * @return items collection.
     */
    @Override
    public ArrayList<Item> findAll() {
        ArrayList<Item> result = new ArrayList<>();
        try (PreparedStatement ps = this.connection.prepareStatement(
                "select i.title, i.description, i.date_create, c.comments,c.item_id from comments as c inner join item i  on c.item_id = i.item_id;")) {
            result = this.sqlToItem(ps);
            result = this.sqlToComments(ps, result);
        } catch (SQLException e) {
            LOG.error("SQL query (select all element from item and comments table)", version);
        }
        return result;
    }
    /**
     * The method takes a string and looks for it in the items table by field title.
     * @param key String for find in table.
     * @return ArrayList with mach items by name field.
     */
    @Override
    public ArrayList<Item> findByName(String key) {
        ArrayList<Item> result = new ArrayList<>();
        try (PreparedStatement ps = this.connection.prepareStatement(
                "select i.title, i.description, i.date_create, c.comments,c.item_id from comments as c inner join item i  on c.item_id = i.item_id where i.title = ?;")) {
            ps.setString(1, key);
            result = this.sqlToItem(ps);
            result = this.sqlToComments(ps, result);
        } catch (SQLException e) {
            LOG.error("SQL query (select element by specific name from item and comments table)", version);
        }
        return result;
    }
    /**
     * The method take preparedStatement, ArrayList collection that before added data from item table, and add comment
     * to item from comments table.
     * @param ps PreparedStatement complete sql query.
     * @param result ArrayList<Item> collection with item without comments.
     * @return ArrayList<Item> collection items with comments.
     */
    public ArrayList<Item> sqlToComments(PreparedStatement ps, ArrayList<Item> result) {
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Iterator<Item> itemIterator = result.iterator();
                while (itemIterator.hasNext()) {
                    Item currItem = itemIterator.next();
                    if (rs.getString("item_id").equals(currItem.getId())) {
                        currItem.setComment(rs.getString("comments"));
                        this.setState(currItem);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("SQL query (passed to method preparedStatement)", version);
        }
        return result;
    }

    /**
     * The method take PreparedStatement and take result after sql query complete, after that insert data from db into
     * item that store to collection that be returned.
     * @param ps PreparedStatement complete sql query.
     * @return ArrayList<Item> collection items with data from item table without comments.
     */
    public ArrayList<Item> sqlToItem(PreparedStatement ps) {
        ArrayList<Item> result = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Item item = new Item(String.valueOf(rs.getBigDecimal("item_id")));
                item.setName(rs.getString("title"));
                item.setDescription(rs.getString("description"));
                item.setCreate(rs.getTimestamp("date_create").getTime());
                result.add(item);
            }
        } catch (Exception e) {
            LOG.error("SQL query (passed to method preparedStatement)", version);
        }
        return result;
    }
    /**
     * The method takes a string and looks for it in the table items by item_id,
     * returns the item which has that id.
     * @param id String id item to search.
     * @return math item with id.
     */
    @Override
    public Item findById(String id) {
        Item result = new Item(id);
        try (PreparedStatement ps = this.connection.prepareStatement(
                "select i.title, i.description, i.date_create, c.comments from comments as c inner join item i  on c.item_id = i.item_id where i.item_id = ?;")) {
            ps.setDouble(1, Double.valueOf(id));
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.setName(rs.getString("title"));
                    result.setDescription(rs.getString("description"));
                    result.setCreate(rs.getTimestamp("date_create").getTime());
                    result.setComment(rs.getString("comments"));
                }
            } catch (Exception e) {
                LOG.error("Result set (get data from item and comments table)", version);
            }
        } catch (SQLException e) {
            LOG.error("SQL query (select element by specific id from item and comments table)", version);
        }
        return result;
    }
    /**
     * Print items to console.
     * @param item Items to print.
     */
    @Override
    public void printToConsoleItem(ArrayList<Item> item) {
        for (Item i:item) {
            System.out.printf("---------------------------------------%n "
                            + "ID: %s%n "
                            + "name: %s%n "
                            + "date: %s%n "
                            + "description: %s%n "
                            + "comment: %s%n "
                            + "---------------------------------------%n",
                    i.getId(),
                    i.getName(),
                    this.convert(i.getCreate()),
                    i.getDescription(),
                    i.getComment()
            );
        }
    }
    /**
     * Convert value millisecond to string date and time example (31.12.1970 23:59:59.999).
     * @param millis vaule in milliseconds
     * @return string date and time "31.12.1970 23:59:59.000"
     */
    public String convert(long millis) {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("Russia/Moscow"));
        cal.setTimeInMillis(millis);
        return new SimpleDateFormat("dd.MM.yy HH:mm:ss.SSS").format(cal.getTime());
    }
    /**
     * Convert string value date and time "31.12.1970 23:59:59" to millisecond value.
     * @param userInputDate string "31.12.1970 23:59:59"
     * @return long in milliseconds
     */
    public long convert(String userInputDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss.SSS");
        Date date = new Date();
        boolean incorret = true;
        do {
            try {
                date = simpleDateFormat.parse(userInputDate);
                incorret = false;
            } catch (ParseException e) {
                System.out.println("Incorrect input!");
                userInputDate = new ConsoleInput().ask("Please enter correct(31.12.1970 23:59:59.999):");
            }
        } while (incorret);
        return date.getTime();
    }
    /**
     * Update fields (name, description, date time, comment) of new value.
     * @param item item for update
     * @param input input data
     */
    @Override
    public void fieldsUpdate(Item item, Input input) {
        item.setName(input.ask("Enter new name: "));
        item.setDescription(input.ask("Enter new description: "));
        item.setComment("Enter new comment: ");
        item.setCreate(this.convert(input.ask("Enter new date create: ")));
        this.replace(item.getId(), item);
    }
    /**
     * The method from old implementation that used Collections to store items.
     * Not need in this version.
     *
     * @param itemUpdate Item to need update.
     */
    @Override
    public void update(Item itemUpdate) { }
    /**
     * Add subscriber.
     * @param observer object that implement Observer interface.
     * @return observer.
     */
    @Override
    public Observer addObserver(Observer observer) {
        this.OBSERVERS.add(observer);
        return observer;
    }
    /**
     * Remove subscriber.
     * @param observer object that implement Observer interface.
     */
    @Override
    public void removeObserver(Observer observer) {
        this.OBSERVERS.remove(observer);
    }
    /**
     * The method call every time when state change.
     */
    @Override
    public void notifyObservers() {
        this.OBSERVERS.forEach(observer -> observer.update(this.state));
    }
    /**
     * Set state what subscribers want to receive.
     * @param item State object.
     */
    private void setState(Item item) {
        this.state = item;
        this.notifyObservers();
    }
}