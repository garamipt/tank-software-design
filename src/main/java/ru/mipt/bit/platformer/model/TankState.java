package ru.mipt.bit.platformer.model;

/**
 * Domain interface (State pattern): how a tank moves and shoots in its current condition.
 * Implemented by {@link DamageState} subclasses.
 */
public interface TankState {
    float speedFactor();

    float fireRateFactor();

    boolean canShoot();

    TankState onHealthChanged(float healthFraction);
}
