package ru.mipt.bit.platformer.model;

/** Domain interface: an object whose state advances with time. */
@FunctionalInterface
public interface Updatable {
    void update(float deltaTime);
}
