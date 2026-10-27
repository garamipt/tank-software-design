package ru.mipt.bit.platformer.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;

class AiControllerTest {

    @Test
    void shouldIssueSingleCommandEachTime() {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.UP, 0.4f);
        AiController controller = new AiController(tank, () -> Direction.RIGHT, point -> true);

        List<Command> commands = controller.getActiveCommands();

        assertEquals(1, commands.size());
    }

    @Test
    void shouldMoveTankInDirectionChosenByStrategy() {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.UP, 0.4f);
        AiController controller = new AiController(tank, () -> Direction.RIGHT, point -> true);

        controller.getActiveCommands().forEach(Command::execute);

        assertEquals(new GridPoint2(2, 1), tank.getDestination());
        assertEquals(Direction.RIGHT, tank.getDirection());
    }

    @Test
    void shouldRespectCollisionChecker() {
        Tank tank = new Tank(new GridPoint2(1, 1), Direction.UP, 0.4f);
        AiController controller = new AiController(tank, () -> Direction.RIGHT, point -> false);

        controller.getActiveCommands().forEach(Command::execute);

        assertEquals(new GridPoint2(1, 1), tank.getDestination());
    }
}
