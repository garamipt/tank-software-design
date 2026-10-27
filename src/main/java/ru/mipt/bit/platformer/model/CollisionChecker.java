package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

@FunctionalInterface
public interface CollisionChecker {
    boolean isFree(GridPoint2 point);
}
