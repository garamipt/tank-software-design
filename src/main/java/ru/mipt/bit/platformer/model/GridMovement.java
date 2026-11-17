package ru.mipt.bit.platformer.model;

import static com.badlogic.gdx.math.MathUtils.clamp;

import com.badlogic.gdx.math.GridPoint2;

public class GridMovement {
    private static final float MOVEMENT_STARTED = 0f;
    private static final float MOVEMENT_COMPLETED = 1f;

    private final GridPoint2 coordinates;
    private final GridPoint2 destination;
    private final float secondsPerCell;
    private float progress = MOVEMENT_COMPLETED;

    public GridMovement(GridPoint2 start, float secondsPerCell) {
        this.coordinates = new GridPoint2(start);
        this.destination = new GridPoint2(start);
        this.secondsPerCell = secondsPerCell;
    }

    public boolean isMoving() {
        return progress < MOVEMENT_COMPLETED;
    }

    public void startTowards(GridPoint2 cell) {
        destination.set(cell);
        progress = MOVEMENT_STARTED;
    }

    public void update(float deltaTime) {
        progress =
                clamp(progress + deltaTime / secondsPerCell, MOVEMENT_STARTED, MOVEMENT_COMPLETED);
        if (!isMoving()) {
            coordinates.set(destination);
        }
    }

    public boolean occupies(GridPoint2 point) {
        return coordinates.equals(point) || destination.equals(point);
    }

    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    public GridPoint2 getDestination() {
        return new GridPoint2(destination);
    }

    public float getProgress() {
        return progress;
    }
}
