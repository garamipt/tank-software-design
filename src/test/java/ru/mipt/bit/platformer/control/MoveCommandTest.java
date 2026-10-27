package ru.mipt.bit.platformer.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

public class MoveCommandTest {
    private static final float SECONDS_PER_CELL = 0.4f;

    @Test
    void shouldMoveTankInBoundDirection() {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.UP, SECONDS_PER_CELL);

        new MoveCommand(tank, Direction.RIGHT, point -> true).execute();

        assertEquals(new GridPoint2(2, 1), tank.getDestination());
        assertEquals(Direction.RIGHT, tank.getDirection());
    }
}
