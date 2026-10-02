package ru.mipt.bit.platformer.model;

import org.junit.jupiter.api.Test;
import com.badlogic.gdx.math.GridPoint2;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TankTest {

    @Test
    public void shouldStartMovingToFreeCell() {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.RIGHT);

        tank.move(Direction.RIGHT, point -> true);
        assertEquals(new GridPoint2(2, 1), tank.getDestination());
        assertEquals(0f, tank.getMovementProgress(), 1e-6f);
        assertEquals(Direction.RIGHT, tank.getDirection());
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
    }

    @Test
    public void shouldNotMoveIntoObstacle() {
        Tank tank = new Tank(new GridPoint2(1, 2), Direction.RIGHT);

        tank.move(Direction.UP, point -> false);
        assertEquals(new GridPoint2(1, 2), tank.getCoordinates());
        assertEquals(new GridPoint2(1, 2), tank.getDestination());
        assertEquals(1f, tank.getMovementProgress(), 1e-6f);
        assertEquals(Direction.UP, tank.getDirection());
    }

    @Test 
    public void shouldIgnoreMoveCommandWhileMoving() {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.RIGHT);

        tank.move(Direction.RIGHT, point -> true);
        tank.move(Direction.UP, point -> true);
        assertEquals(new GridPoint2(2, 1), tank.getDestination());
        assertEquals(0f, tank.getMovementProgress(), 1e-6f);
        assertEquals(Direction.RIGHT, tank.getDirection());
    }

    @Test
    public void shouldArriveAtDestinationAfterUpdate() {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.RIGHT);

        tank.move(Direction.RIGHT, point -> true);
        tank.update(1f);
        assertEquals(new GridPoint2(2, 1), tank.getCoordinates());
        assertEquals(new GridPoint2(2, 1), tank.getDestination());
        assertEquals(1f, tank.getMovementProgress(), 1e-6f);
    }
}
