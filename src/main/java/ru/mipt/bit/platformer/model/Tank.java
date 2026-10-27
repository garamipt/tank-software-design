package ru.mipt.bit.platformer.model;

import static com.badlogic.gdx.math.MathUtils.clamp;

import com.badlogic.gdx.math.GridPoint2;

public class Tank implements GameObject, Updatable {
    private static final float MOVEMENT_STARTED = 0f;
    private static final float MOVEMENT_COMPLETED = 1f;

    private final GridPoint2 coordinates;
    private final GridPoint2 destination;
    private final float secondsPerCell;

    private Direction direction;
    private float movementProgress = MOVEMENT_COMPLETED;

    public Tank(GridPoint2 initialCoordinates, Direction initialDirection, float secondsPerCell) {
        this.coordinates = new GridPoint2(initialCoordinates);
        this.destination = new GridPoint2(initialCoordinates);
        this.direction = initialDirection;
        this.secondsPerCell = secondsPerCell;
    }

    public void move(Direction direction, CollisionChecker collisionChecker) {
        if (isMoving()) {
            return;
        }
        GridPoint2 next = direction.stepFrom(coordinates);
        if (collisionChecker.isFree(next)) {
            destination.set(next);
            movementProgress = MOVEMENT_STARTED;
        }
        this.direction = direction;
    }

    @Override
    public void update(float deltaTime) {
        movementProgress =
                clamp(
                        movementProgress + deltaTime / secondsPerCell,
                        MOVEMENT_STARTED,
                        MOVEMENT_COMPLETED);
        if (!isMoving()) {
            coordinates.set(destination);
        }
    }

    private boolean isMoving() {
        return movementProgress < MOVEMENT_COMPLETED;
    }

    @Override
    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }

    @Override
    public GridPoint2 getDestination() {
        return new GridPoint2(destination);
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public float getRotation() {
        return direction.getRotation();
    }
}
