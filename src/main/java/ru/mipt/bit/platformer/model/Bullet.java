package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import java.util.Optional;

public class Bullet implements LevelObject {
    private final GridMovement movement;
    private final Direction direction;
    private final int damage;
    private final World world;
    private boolean destroyed;

    public Bullet(
            GridPoint2 shooterPosition, Direction direction, BulletSettings settings, World world) {
        this.movement = new GridMovement(shooterPosition, settings.secondsPerCell());
        this.movement.startTowards(direction.stepFrom(shooterPosition));
        this.direction = direction;
        this.damage = settings.damage();
        this.world = world;
    }

    @Override
    public void update(float deltaTime) {
        movement.update(deltaTime);
        if (movement.isMoving()) {
            return;
        }
        GridPoint2 reachedCell = movement.getCoordinates();
        if (collidesAt(reachedCell)) {
            destroyed = true;
            return;
        }
        movement.startTowards(direction.stepFrom(reachedCell));
    }

    private boolean collidesAt(GridPoint2 cell) {
        if (!world.contains(cell)) {
            return true;
        }
        Optional<LevelObject> target = firstOtherObjectAt(cell);
        target.ifPresent(object -> object.receiveHit(damage));
        return target.isPresent();
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
