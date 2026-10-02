package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

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
