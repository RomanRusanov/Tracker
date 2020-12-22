package ru.rrusanov.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Timestamp;
import java.util.List;

/**
 * The class demonstrate CRUD operations.
 */
public class HibernateRun {
    /**
     * The main method.
     * @param args args.
     */
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Item item = create(new Item("Learn Hibernate"), sf);
            System.out.println(item);
            item.setName("Learn Hibernate 5.");
            update(item, sf);
            System.out.println(item);
            Item rsl = findById(item.getId(), sf);
            System.out.println(rsl);
            delete(rsl.getId(), sf);
            List<Item> list = findAll(sf);
            for (Item it : list) {
                System.out.println(it);
            }
            Item item1 = create(new Item("Configure schema"), sf);
            item1.setDescription("This is description added after row insert in persistence storage");
            item1.setCreated(new Timestamp(1459510232000L));
            update(item1, sf);
            Item item2 = new Item("Item2");
            item2.setDescription("Desc Item2");
            item2.setCreated(new Timestamp(2559510232000L));
            create(item2, sf);
            Item item3 = new Item("Item3");
            item3.setDescription("Desc Item3");
            item3.setCreated(new Timestamp(4559510232000L));
            create(item3, sf);
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    /**
     * The method create item.
     * @param item item.
     * @param sf session factory.
     * @return item.
     */
    public static Item create(Item item, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    /**
     * The method update item.
     * @param item item.
     * @param sf session factory.
     */
    public static void update(Item item, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.update(item);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * The method delete item.
     * @param id id.
     * @param sf session factory.
     */
    public static void delete(Integer id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item item = new Item(null);
        item.setId(id);
        session.delete(item);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * The method find all item.
     * @param sf session factory.
     * @return list items.
     */
    public static List<Item> findAll(SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from ru.rrusanov.hibernate.Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * The method find item by id.
     * @param id id.
     * @param sf session factory.
     * @return item.
     */
    public static Item findById(Integer id, SessionFactory sf) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        return result;
    }
}