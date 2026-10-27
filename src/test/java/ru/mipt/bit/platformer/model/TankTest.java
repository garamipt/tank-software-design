package ru.mipt.bit.platformer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

public class TankTest {
    private static final float SECONDS_PER_CELL = 0.4f;

    @Test
    public void shouldStartMovingToFreeCell() {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.RIGHT, SECONDS_PER_CELL);

        tank.move(Direction.RIGHT, point -> true);
        assertEquals(new GridPoint2(2, 1), tank.getDestination());
        assertEquals(0f, tank.getMovementProgress(), 1e-6f);
        assertEquals(Direction.RIGHT, tank.getDirection());
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
    }

    @Test
    public void shouldNotMoveIntoObstacle() {
        Tank tank = new Tank(new GridPoint2(1, 2), Direction.RIGHT, SECONDS_PER_CELL);

        tank.move(Direction.UP, point -> false);
        assertEquals(new GridPoint2(1, 2), tank.getCoordinates());
        assertEquals(new GridPoint2(1, 2), tank.getDestination());
        assertEquals(1f, tank.getMovementProgress(), 1e-6f);
        assertEquals(Direction.UP, tank.getDirection());
    }

    @Test
    public void shouldIgnoreMoveCommandWhileMoving() {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.RIGHT, SECONDS_PER_CELL);

        tank.move(Direction.RIGHT, point -> true);
        tank.move(Direction.UP, point -> true);
        assertEquals(new GridPoint2(2, 1), tank.getDestination());
        assertEquals(0f, tank.getMovementProgress(), 1e-6f);
        assertEquals(Direction.RIGHT, tank.getDirection());
    }

    @Test
    public void shouldArriveAtDestinationAfterUpdate() {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.RIGHT, SECONDS_PER_CELL);

        tank.move(Direction.RIGHT, point -> true);
        tank.update(1f);
        assertEquals(new GridPoint2(2, 1), tank.getCoordinates());
        assertEquals(new GridPoint2(2, 1), tank.getDestination());
        assertEquals(1f, tank.getMovementProgress(), 1e-6f);
    }

    @Test
    public void shouldCrossCellInConfiguredTime() {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.RIGHT, 1f);

        tank.move(Direction.RIGHT, point -> true);
        tank.update(0.5f);

        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
        assertEquals(0.5f, tank.getMovementProgress(), 1e-6f);

        tank.update(0.5f);

        assertEquals(new GridPoint2(2, 1), tank.getCoordinates());
    }

    @Test
    public void shouldOccupyOnlyOwnCellWhenStanding() {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.RIGHT, SECONDS_PER_CELL);

        assertTrue(tank.occupies(new GridPoint2(1, 1)));
        assertFalse(tank.occupies(new GridPoint2(2, 1)));
    }

    @Test
    public void shouldOccupyBothCellsWhileMoving() {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.RIGHT, SECONDS_PER_CELL);

        tank.move(Direction.RIGHT, point -> true);

        assertTrue(tank.occupies(new GridPoint2(1, 1)));
        assertTrue(tank.occupies(new GridPoint2(2, 1)));
    }

    @Test
    public void shouldReleaseOriginCellAfterArrival() {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.RIGHT, SECONDS_PER_CELL);

        tank.move(Direction.RIGHT, point -> true);
        tank.update(1f);

        assertFalse(tank.occupies(new GridPoint2(1, 1)));
        assertTrue(tank.occupies(new GridPoint2(2, 1)));
    }
}
