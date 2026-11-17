package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import java.util.Optional;

public class Bullet implements LevelObject {
    private final GridMovement movement;
    private final Direction direction;
    private final int damage;
    private final World world;
    private boolean destroyed;

    public Bullet(GridPoint2 position, Direction direction, BulletSettings settings, World world) {
        this.movement = new GridMovement(position, settings.secondsPerCell());
        this.direction = direction;
        this.damage = settings.damage();
        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        if (collides()) {
            return;
        }
        if (!movement.isMoving()) {
            movement.startTowards(direction.stepFrom(movement.getDestination()));
            if (collides()) {
                return;
            }
        }
        movement.update(deltaTime);
    }

    private boolean collides() {
        if (!world.contains(movement.getCoordinates())
                || !world.contains(movement.getDestination())) {
            destroyed = true;
            return true;
        }
        Optional<LevelObject> target = findTarget();
        if (target.isEmpty()) {
            return false;
        }
        target.get().receiveHit(damage);
        destroyed = true;
        return true;
    }

    private Optional<LevelObject> findTarget() {
        return firstOtherObjectAt(movement.getCoordinates())
                .or(() -> firstOtherObjectAt(movement.getDestination()));
    }

    private Optional<LevelObject> firstOtherObjectAt(GridPoint2 point) {
        return world.objectsAt(point).stream().filter(object -> object != this).findFirst();
    }

    @Override
    public void receiveHit(int damage) {
        destroyed = true;
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    @Override
    public boolean occupies(GridPoint2 point) {
        return movement.occupies(point);
    }

    @Override
    public GridPoint2 getCoordinates() {
        return movement.getCoordinates();
    }

    @Override
    public GridPoint2 getDestination() {
        return movement.getDestination();
    }

    @Override
    public float getMovementProgress() {
        return movement.getProgress();
    }

    @Override
    public float getRotation() {
        return direction.getRotation();
    }
}
