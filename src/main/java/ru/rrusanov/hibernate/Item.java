package ru.rrusanov.hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * The model class item.
 */
@Entity
@Table(name = "items")
public class Item {
    /**
     * The id field.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * The name field.
     */
    private String name;
    /**
     * The description field.
     */
    private String description;
    /**
     * The created field.
     */
    private Timestamp created;

    /**
     * Default constructor.
     */
    public Item() {
    }

    /**
     * Constructor with name param.
     * @param name name.
     */
    public Item(String name) {
        this.name = name;
    }

    /**
     * The getter.
     * @return Integer.
     */
    public Integer getId() {
        return id;
    }

    /**
     * The setter.
     * @param id Integer.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * The getter.
     * @return String.
     */
    public String getName() {
        return name;
    }

    /**
     * The setter.
     * @param name String.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The getter.
     * @return String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * The setter.
     * @param description String.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * The getter.
     * @return Timestamp.
     */
    public Timestamp getCreated() {
        return created;
    }

    /**
     * The setter.
     * @param created Timestamp.
     */
    public void setCreated(Timestamp created) {
        this.created = created;
    }

    /**
     * Override equals method.
     * @param o Object
     * @return boolean
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
        return Objects.equals(id, item.id)
                && Objects.equals(name, item.name);
    }

    /**
     * Override hashCode.
     * @return hash int.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    /**
     * Override toString.
     * @return String.
     */
    @Override
    public String toString() {
        return "{"
                + "id='" + id + '\''
                + ", name='" + name + '\''
                + '}';
    }
}