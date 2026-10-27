package ru.mipt.bit.platformer.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.Direction;

class RandomMovementStrategyTest {

    @Test
    void shouldEventuallyChooseEveryDirection() {
        RandomMovementStrategy strategy = new RandomMovementStrategy(new Random(1));
        Set<Direction> chosen = EnumSet.noneOf(Direction.class);

        for (int i = 0; i < 100; i++) {
            chosen.add(strategy.chooseDirection());
        }

        assertEquals(EnumSet.allOf(Direction.class), chosen);
    }

    @Test
    void shouldBeReproducibleWithSameSeed() {
        assertEquals(directions(new Random(42)), directions(new Random(42)));
    }

    private static List<Direction> directions(Random random) {
        RandomMovementStrategy strategy = new RandomMovementStrategy(random);
        List<Direction> directions = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            directions.add(strategy.chooseDirection());
        }
        return directions;
    }
}
