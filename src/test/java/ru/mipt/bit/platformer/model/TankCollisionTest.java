package ru.mipt.bit.platformer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;
import org.junit.jupiter.api.Test;

class TankCollisionTest {
    private static final float SECONDS_PER_CELL = 0.4f;

    @Test
    void shouldNotEnterCellAnotherTankIsLeaving() {
        Tank leaving = new Tank(new GridPoint2(2, 1), Direction.RIGHT, SECONDS_PER_CELL);
        Tank follower = new Tank(new GridPoint2(1, 1), Direction.RIGHT, SECONDS_PER_CELL);
        Level level = new Level(10, 8, List.of(leaving, follower));

        leaving.move(Direction.RIGHT, level);
        follower.move(Direction.RIGHT, level);

        assertEquals(new GridPoint2(1, 1), follower.getDestination());
    }

    @Test
    void shouldNotEnterCellAnotherTankIsHeadingTo() {
        Tank first = new Tank(new GridPoint2(1, 1), Direction.RIGHT, SECONDS_PER_CELL);
        Tank second = new Tank(new GridPoint2(3, 1), Direction.LEFT, SECONDS_PER_CELL);
        Level level = new Level(10, 8, List.of(first, second));

        first.move(Direction.RIGHT, level);
        second.move(Direction.LEFT, level);

        assertEquals(new GridPoint2(2, 1), first.getDestination());
        assertEquals(new GridPoint2(3, 1), second.getDestination());
    }

    @Test
    void shouldEnterCellAfterAnotherTankLeftIt() {
        Tank leaving = new Tank(new GridPoint2(2, 1), Direction.RIGHT, SECONDS_PER_CELL);
        Tank follower = new Tank(new GridPoint2(1, 1), Direction.RIGHT, SECONDS_PER_CELL);
        Level level = new Level(10, 8, List.of(leaving, follower));

        leaving.move(Direction.RIGHT, level);
        leaving.update(1f);
        follower.move(Direction.RIGHT, level);

        assertEquals(new GridPoint2(2, 1), follower.getDestination());
    }

    @Test
    void shouldStopAtLevelBorder() {
        Tank tank = new Tank(new GridPoint2(9, 7), Direction.UP, SECONDS_PER_CELL);
        Level level = new Level(10, 8, List.of(tank));

        tank.move(Direction.RIGHT, level);
        tank.move(Direction.UP, level);

        assertEquals(new GridPoint2(9, 7), tank.getDestination());
        assertEquals(Direction.UP, tank.getDirection());
    }
}
