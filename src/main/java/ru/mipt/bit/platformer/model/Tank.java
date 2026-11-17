package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class Tank implements LevelObject, Damageable {
    private final GridMovement movement;
    private final TankSettings settings;

    private Direction direction;
    private int health;
    private float reloadRemaining;
    private TankState state;

    public Tank(GridPoint2 initialCoordinates, Direction initialDirection, TankSettings settings) {
        this.movement = new GridMovement(initialCoordinates, settings.secondsPerCell());
        this.settings = settings;
        this.direction = initialDirection;
        this.health = settings.health();
        this.state = DamageState.forHealthFraction(getHealthFraction());
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
        if (isDestroyed() || !state.canShoot() || isReloading()) {
            return;
        }
        reloadRemaining = settings.reloadSeconds();
        world.add(new Bullet(movement.getDestination(), direction, settings.bullet(), world));
    }

    @Override
    public void update(float deltaTime) {
        movement.update(deltaTime * state.speedFactor());
        reloadRemaining = Math.max(0f, reloadRemaining - deltaTime * state.fireRateFactor());
    }

    @Override
    public void receiveHit(int damage) {
        health -= damage;
        state = state.onHealthChanged(getHealthFraction());
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

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public int getMaxHealth() {
        return settings.health();
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

    public TankState getState() {
        return state;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public float getRotation() {
        return direction.getRotation();
    }
}
