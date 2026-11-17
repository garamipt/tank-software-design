package ru.mipt.bit.platformer.model;

public interface LevelListener {
    void onObjectAdded(LevelObject object);

    void onObjectRemoved(LevelObject object);
}
