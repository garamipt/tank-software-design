package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

/** Domain interface: an object that occupies cells of the level. */
@FunctionalInterface
public interface Occupant {
    boolean occupies(GridPoint2 point);
}
