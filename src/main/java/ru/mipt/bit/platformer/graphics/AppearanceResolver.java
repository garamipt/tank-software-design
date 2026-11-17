package ru.mipt.bit.platformer.graphics;

import ru.mipt.bit.platformer.model.GameObject;

/** Infrastructure interface: picks a texture and tint for a domain object. */
@FunctionalInterface
public interface AppearanceResolver {
    Appearance appearanceOf(GameObject object);
}
