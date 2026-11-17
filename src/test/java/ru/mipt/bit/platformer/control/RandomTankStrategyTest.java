package ru.mipt.bit.platformer.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.mipt.bit.platformer.model.Direction;

class RandomTankStrategyTest {

    @Test
    void shouldMoveWhereMovementStrategySaysWhenNeverShooting() {
        RandomTankStrategy strategy =
                new RandomTankStrategy(() -> Direction.LEFT, 0, new Random(1));

        for (int i = 0; i < 20; i++) {
            assertEquals(new TankAction.Move(Direction.LEFT), strategy.chooseAction());
        }
    }

    @Test
    void shouldAlwaysShootWhenProbabilityIsOne() {
        RandomTankStrategy strategy =
                new RandomTankStrategy(() -> Direction.LEFT, 1, new Random(1));

        for (int i = 0; i < 20; i++) {
            assertEquals(new TankAction.Shoot(), strategy.chooseAction());
        }
    }

    @Test
    void shouldSometimesShootAndSometimesMove() {
        RandomTankStrategy strategy =
                new RandomTankStrategy(() -> Direction.LEFT, 0.5, new Random(42));
        List<TankAction> actions = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            actions.add(strategy.chooseAction());
        }

        assertTrue(actions.contains(new TankAction.Shoot()));
        assertTrue(actions.contains(new TankAction.Move(Direction.LEFT)));
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, 1.1})
    void shouldRejectProbabilityOutsideUnitInterval(double probability) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new RandomTankStrategy(() -> Direction.LEFT, probability, new Random(1)));
    }
}
