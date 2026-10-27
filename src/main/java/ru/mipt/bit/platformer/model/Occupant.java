package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

@FunctionalInterface
public interface Occupant {
    boolean occupies(GridPoint2 point);
}
