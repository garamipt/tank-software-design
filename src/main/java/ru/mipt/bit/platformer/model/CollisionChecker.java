package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

/** Domain interface: answers whether a tank may enter a cell. Implemented by {@link GameWorld}. */
@FunctionalInterface
public interface CollisionChecker {
    boolean isFree(GridPoint2 point);
}
