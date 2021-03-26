package ru.rrusanov.reactive;

import java.util.ArrayList;
import java.util.List;

/**
 * Interface that object contain changed state and notify all subscribers.
 */
public interface Observable {
    /**
     * Contain all subscribers.
     */
    List<Observer> OBSERVERS = new ArrayList<>();

    /**
     * Add subscriber.
     * @param observer object that implement Observer interface.
     * @return observer.
     */
    Observer addObserver(Observer observer);

    /**
     * Remove subscriber.
     * @param observer object that implement Observer interface.
     */
    void removeObserver(Observer observer);

    /**
     * The method call every time when state change.
     */
    void notifyObservers();
}
