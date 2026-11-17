package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class Tank implements LevelObject {
    private final GridMovement movement;
    private final TankSettings settings;

    private Direction direction;
    private int health;
    private float reloadRemaining;

    public Tank(GridPoint2 initialCoordinates, Direction initialDirection, TankSettings settings) {
        this.movement = new GridMovement(initialCoordinates, settings.secondsPerCell());
        this.settings = settings;
        this.direction = initialDirection;
        this.health = settings.health();
    }

    public void move(Direction direction, CollisionChecker collisionChecker) {
        if (isDestroyed() || movement.isMoving()) {
            return;
        }
        GridPoint2 next = direction.stepFrom(movement.getCoordinates());
        if (collisionChecker.isFree(next)) {
            movement.startTowards(next);
        }
        this.direction = direction;
    }

    public void shoot(World world) {
        if (isDestroyed() || isReloading()) {
            return;
        }
        reloadRemaining = settings.reloadSeconds();
        GridPoint2 bulletPosition = direction.stepFrom(movement.getDestination());
        world.add(new Bullet(bulletPosition, direction, settings.bullet(), world));
    }

    @Override
    public void update(float deltaTime) {
        movement.update(deltaTime);
        reloadRemaining = Math.max(0f, reloadRemaining - deltaTime);
    }

    @Override
    public void receiveHit(int damage) {
        health -= damage;
    }

    @Override
    public boolean isDestroyed() {
        return health <= 0;
    }

    @Override
    public boolean occupies(GridPoint2 point) {
        return movement.occupies(point);
    }

    private boolean isReloading() {
        return reloadRemaining > 0f;
    }

    public int getHealth() {
        return health;
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

    public Direction getDirection() {
        return direction;
    }

    @Override
    public float getRotation() {
        return direction.getRotation();
    }
}
