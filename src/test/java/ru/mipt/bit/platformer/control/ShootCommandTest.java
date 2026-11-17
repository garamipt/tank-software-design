package ru.mipt.bit.platformer.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.mipt.bit.platformer.model.TestTanks.tank;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameWorld;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.TestTanks;
import ru.mipt.bit.platformer.model.TestTanks.RecordingListener;

class ShootCommandTest {

    @Test
    void shouldAskTankToShoot() {
        GameWorld world = TestTanks.world();
        RecordingListener listener = new RecordingListener();
        world.addListener(listener);
        Tank tank = tank(1, 1, Direction.UP);

        new ShootCommand(tank, world).execute();

        assertEquals(1, listener.addedBullets().size());
        assertEquals(new GridPoint2(1, 2), listener.addedBullets().get(0).getDestination());
    }
}
