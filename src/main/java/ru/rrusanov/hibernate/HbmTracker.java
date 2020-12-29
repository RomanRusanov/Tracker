package ru.rrusanov.hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * @author Roman Rusanov
 * @version 0.1
 * @since 24.12.2020
 * email roman9628@gmail.com
 * The class describe Tracker CRUD operation with items.
 */
public class HbmTracker implements Store, AutoCloseable {
    /**
     * Registry for hibernate configuration.
     */
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    /**
     * Session factory for hibernate interaction.
     */
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    /**
     * The methods add item to the items collection.
     *
     * @param item instance of item
     * @return new item
     */
    @Override
    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    /**
     * The method takes an item to update the id field it finds in the items collection.
     * In that schema implementations passed id param must exist in DB.
     * ID use auto generated integer value in DB.
     * @param id   id item.
     * @param item Item to need update.
     * @return if item replace return true, otherwise false.
     */
    @Override
    public boolean replace(String id, Item item) {
        Item itemToUpdate = findById(id);
        itemToUpdate.setId(Integer.parseInt(id));
        itemToUpdate.setName(item.getName());
        itemToUpdate.setDescription(item.getDescription());
        itemToUpdate.setCreated(item.getCreated());
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(itemToUpdate);
        session.getTransaction().commit();
        session.close();
        Item itemReplaced = findById(id);
        return itemReplaced.getName().equals(item.getName())
                && itemReplaced.getDescription().equals(item.getDescription())
                && itemReplaced.getCreated().equals(item.getCreated());
    }

    /**
     * The method takes an item to delete, the id field it finds in the items collection,
     * founded item are remove from collection.
     * @param id id item.
     * @return if item with passed id delete and not find in DB return true,
     * otherwise false.
     */
    @Override
    public boolean delete(String id) {
        boolean result = false;
        Integer itemId = Integer.parseInt(id);
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = new Item(null);
        item.setId(itemId);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
        Item itemExist = findById(id);
        if (itemExist == null) {
            result = true;
        }
        return result;
    }

    /**
     * The Method return all items.
     * @return items collection.
     */
    @Override
    public List<Item> findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.rrusanov.hibernate.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * The method takes a string and looks for it in the items collection by field name,
     * returns the other collection with items has this string.
     * @param name String for find in DB
     * @return ArrayList with mach items by name field.
     */
    @Override
    public List<Item> findByName(String name) {
        List all = findAll();
        List<Item> result = new ArrayList<>();
        Iterator<Item> it = all.iterator();
        while (it.hasNext()) {
            Item current = it.next();
            if (current.getName().equals(name)) {
                result.add(current);
            }
        }
        return result;
    }

    /**
     * The method takes a string and looks for it in the items array by field id,
     * returns the item which has this string.
     * @param id String id item to search
     * @return math item with id
     */
    @Override
    public Item findById(String id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, Integer.parseInt(id));
        session.getTransaction().commit();
        session.close();
        return result;
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}