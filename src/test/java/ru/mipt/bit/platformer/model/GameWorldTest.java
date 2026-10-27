package ru.mipt.bit.platformer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;
import org.junit.jupiter.api.Test;

public class GameWorldTest {
    private static final float SECONDS_PER_CELL = 0.4f;

    @Test
    public void shouldUpdateEveryObject() {
        Tank first = new Tank(new GridPoint2(1, 1), Direction.RIGHT, SECONDS_PER_CELL);
        Tank second = new Tank(new GridPoint2(5, 5), Direction.UP, SECONDS_PER_CELL);
        GameWorld world = new GameWorld(List.of(first, second));

        first.move(Direction.RIGHT, point -> true);
        second.move(Direction.UP, point -> true);
        world.update(1f);

        assertEquals(new GridPoint2(2, 1), first.getCoordinates());
        assertEquals(new GridPoint2(5, 6), second.getCoordinates());
    }
}
