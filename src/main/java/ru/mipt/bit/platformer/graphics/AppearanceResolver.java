package ru.mipt.bit.platformer.graphics;

import ru.mipt.bit.platformer.model.GameObject;

@FunctionalInterface
public interface AppearanceResolver {
    Appearance appearanceOf(GameObject object);
}
