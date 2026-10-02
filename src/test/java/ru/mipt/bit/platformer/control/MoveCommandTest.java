package ru.mipt.bit.platformer.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

public class MoveCommandTest {
    @Test
    void shouldMoveTankInBoundDirection() {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.UP);

        new MoveCommand(tank, Direction.RIGHT, point -> true).execute();

        assertEquals(new GridPoint2(2, 1), tank.getDestination());
        assertEquals(Direction.RIGHT, tank.getDirection());
    }
}
