package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

/**
 * Domain interface: read-only view of an object's position and rotation that the graphics adapter
 * draws.
 */
public interface GameObject {

    GridPoint2 getCoordinates();

    float getRotation();

    default GridPoint2 getDestination() {
        return getCoordinates();
    }

    default float getMovementProgress() {
        return 1f;
    }
}
