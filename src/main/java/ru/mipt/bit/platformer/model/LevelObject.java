package ru.mipt.bit.platformer.model;

public interface LevelObject extends GameObject, Occupant, Updatable {
    void receiveHit(int damage);

    boolean isDestroyed();
}
