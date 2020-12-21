package ru.rrusanov.models;

import java.util.Objects;
import java.util.Random;
/**
 * Class The class includes information.
 * The id, name, description, creation date, comments.
 *
 * @author Roman Rusanov
 * @version 0.1
 * @since 10.01.17
 */
public class Item {
    /**
     * The field id String item id generate random.
     */
    private String id;
    /**
     * The field name String name of item.
     */
    private String name;
    /**
     * The field description String description for item.
     */
    private String description;
    /**
     * The field create long time of creation item.
     */
    private long create;
    /**
     * The field comment String comment for item.
     */
    private String comment;
    /**
     * The field Random constant generate random sequence for item id.
     */
    private static final Random RN = new Random();
    /**
     * Getter for id field.
     * @return String - id.
     */
    public String getId() {
        return this.id;
    }
    /**
     * Getter for name field.
     * @return String - name.
     */
    public String getName() {
        return this.name;
    }
    /**
     * Getter for description field.
     * @return String - description.
     */
    public String getDescription() {
        return this.description;
    }
    /**
     * Getter for create field.
     * @return long - create.
     */
    public long getCreate() {
        return this.create;
    }
    /**
     * Getter for comment field.
     * @return String - comment.
     */
    public String getComment() {
        return this.comment;
    }
    /**
     * Setter for name field.
     * @param name String name item
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Setter for description field.
     * @param description String description for item
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Setter for create field.
     * @param create long  date of create item
     */
    public void setCreate(long create) {
        this.create = create;
    }
    /**
     * Setter for comment field.
     * @param comment String comment for item
     */
    public void setComment(String comment) {
        this.comment = comment;
    }
    /**
     * Consructor for Item object.
     * id = random
     * other fields = null
     */
    public Item() {
        this.id = generateId();
    }
    /**
     * Consructor for Item object.
     * @param id String id item
     * other fields = null.
     */
    public Item(String id) {
        this.id = id;
        this.create = System.currentTimeMillis();
    }
    /**
     * Constructor for Item object.
     * @param name name for item
     * @param description description for item
     * @param create date and time creation item in tracker
     * @param comment comment for item
     */
    public Item(String name, String description, long create, String comment) {
        this.id = generateId();
        this.name = name;
        this.description = description;
        this.create = create;
        this.comment = comment;
    }
    /**
     * Generate random id for item.
     * @return String - id (Example 142567879783).
     */
    private String generateId() {
        return String.valueOf(RN.nextInt() + System.currentTimeMillis());
    }

    /**
     * Override equals.
     * @param o Object
     * @return boolean.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return create == item.create
                && id.equals(item.id)
                && Objects.equals(name, item.name)
                && Objects.equals(description, item.description)
                && Objects.equals(comment, item.comment);
    }

    /**
     * Override hashCode.
     * @return int hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, create, comment);
    }
}

