package ru.mipt.bit.platformer.model;

/**
 * Port (driven, output): notifications about objects added to and removed from the level. Adapter:
 * {@link ru.mipt.bit.platformer.graphics.GameGraphics}.
 */
public interface LevelListener {
    void onObjectAdded(LevelObject object);

    void onObjectRemoved(LevelObject object);
}
