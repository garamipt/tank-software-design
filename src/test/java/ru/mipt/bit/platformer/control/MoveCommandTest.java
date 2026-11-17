package ru.mipt.bit.platformer.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.mipt.bit.platformer.model.TestTanks.tank;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

class MoveCommandTest {

    @Test
    void shouldMoveTankInBoundDirection() {
        Tank tank = tank(1, 1, Direction.UP);

        new MoveCommand(tank, Direction.RIGHT, point -> true).execute();

        assertEquals(new GridPoint2(2, 1), tank.getDestination());
        assertEquals(Direction.RIGHT, tank.getDirection());
    }
}
