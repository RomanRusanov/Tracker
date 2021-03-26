package ru.rrusanov.reactive;

import ru.rrusanov.models.Item;

/**
 * The interface what implements subscribers.
 */
public interface Observer {
    /**
     * Call each time when state update.
     * @param item State object.
     */
    void update(Item item);
}
