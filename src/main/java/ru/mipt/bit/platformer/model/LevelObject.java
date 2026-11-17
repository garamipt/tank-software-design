package ru.mipt.bit.platformer.model;

/** Domain interface: anything that lives in the level, occupies cells and takes hits. */
public interface LevelObject extends GameObject, Occupant, Updatable {
    void receiveHit(int damage);

    boolean isDestroyed();
}
