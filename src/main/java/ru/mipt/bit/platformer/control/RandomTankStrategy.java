package ru.mipt.bit.platformer.control;

import java.util.Random;

/** Implements port {@link TankStrategy}: random moves with occasional shots. */
public class RandomTankStrategy implements TankStrategy {
    private final MovementStrategy movementStrategy;
    private final double shootProbability;
    private final Random random;

    public RandomTankStrategy(
            MovementStrategy movementStrategy, double shootProbability, Random random) {
        if (shootProbability < 0 || shootProbability > 1) {
            throw new IllegalArgumentException(
                    "Shoot probability must be between 0 and 1: " + shootProbability);
        }
        this.movementStrategy = movementStrategy;
        this.shootProbability = shootProbability;
        this.random = random;
    }

    @Override
    public TankAction chooseAction() {
        if (random.nextDouble() < shootProbability) {
            return new TankAction.Shoot();
        }
        return new TankAction.Move(movementStrategy.chooseDirection());
    }
}
