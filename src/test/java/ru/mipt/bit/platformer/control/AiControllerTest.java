package ru.mipt.bit.platformer.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static ru.mipt.bit.platformer.model.TestTanks.tank;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameWorld;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.TestTanks;
import ru.mipt.bit.platformer.model.TestTanks.RecordingListener;

class AiControllerTest {
    private GameWorld world;
    private RecordingListener listener;
    private Tank tank;

    @BeforeEach
    void setUp() {
        world = TestTanks.world();
        listener = new RecordingListener();
        world.addListener(listener);
        tank = tank(1, 1, Direction.UP);
        world.add(tank);
    }

    @Test
    void shouldIssueSingleCommandEachTime() {
        AiController controller =
                new AiController(tank, () -> new TankAction.Move(Direction.RIGHT), world);

        List<Command> commands = controller.getActiveCommands();

        assertEquals(1, commands.size());
        assertInstanceOf(MoveCommand.class, commands.get(0));
    }

    @Test
    void shouldMoveTankInDirectionChosenByStrategy() {
        AiController controller =
                new AiController(tank, () -> new TankAction.Move(Direction.RIGHT), world);

        controller.getActiveCommands().forEach(Command::execute);

        assertEquals(new GridPoint2(2, 1), tank.getDestination());
        assertEquals(Direction.RIGHT, tank.getDirection());
    }

    @Test
    void shouldRespectObstacles() {
        world.add(new Obstacle(new GridPoint2(2, 1)));
        AiController controller =
                new AiController(tank, () -> new TankAction.Move(Direction.RIGHT), world);

        controller.getActiveCommands().forEach(Command::execute);

        assertEquals(new GridPoint2(1, 1), tank.getDestination());
    }

    @Test
    void shouldShootWhenStrategyChoosesShooting() {
        AiController controller = new AiController(tank, TankAction.Shoot::new, world);

        controller.getActiveCommands().forEach(Command::execute);

        assertEquals(1, listener.addedBullets().size());
        assertEquals(new GridPoint2(1, 2), listener.addedBullets().get(0).getCoordinates());
    }
}
