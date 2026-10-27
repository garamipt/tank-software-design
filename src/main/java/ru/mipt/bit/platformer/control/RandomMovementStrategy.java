package ru.mipt.bit.platformer.control;

import java.util.Random;
import ru.mipt.bit.platformer.model.Direction;

public class RandomMovementStrategy implements MovementStrategy {
    private static final Direction[] DIRECTIONS = Direction.values();

    private final Random random;

    public RandomMovementStrategy(Random random) {
        this.random = random;
    }

    @Override
    public Direction chooseDirection() {
        return DIRECTIONS[random.nextInt(DIRECTIONS.length)];
    }
}
