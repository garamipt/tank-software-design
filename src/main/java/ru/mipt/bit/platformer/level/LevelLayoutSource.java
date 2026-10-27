package ru.mipt.bit.platformer.level;

import ru.mipt.bit.platformer.model.LevelLayout;

@FunctionalInterface
public interface LevelLayoutSource {
    LevelLayout createLayout(int width, int height);
}
