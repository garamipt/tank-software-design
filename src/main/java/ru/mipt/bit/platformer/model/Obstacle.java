package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class Obstacle implements GameObject {
    private final GridPoint2 coordinates;

    public Obstacle(GridPoint2 coordinates) {
        this.coordinates = new GridPoint2(coordinates);
    }

    @Override 
    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    @Override
    public float getRotation() {
        return 0f;
    }

    public boolean occupies(GridPoint2 point) {
        return coordinates.equals(point);
    }
}
