package ru.rrusanov.reactive;

import ru.rrusanov.models.Item;

/**
 * @author Roman Rusanov
 * @since 26.03.2021
 * email roman9628@gmail.com
 */
public class Subscriber implements Observer {
    /**
     * Call each time when state update.
     * @param item State object.
     */
    @Override
    public void update(Item item) {
        System.out.println("Subscriber receive new state: " + item);
    }
}