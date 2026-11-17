package ru.mipt.bit.platformer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.mipt.bit.platformer.model.TestTanks.tank;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

class TankCollisionTest {

    @Test
    void shouldNotEnterCellAnotherTankIsLeaving() {
        GameWorld world = TestTanks.world();
        Tank leaving = tank(2, 1, Direction.RIGHT);
        Tank follower = tank(1, 1, Direction.RIGHT);
        world.add(leaving);
        world.add(follower);

        leaving.move(Direction.RIGHT, world);
        follower.move(Direction.RIGHT, world);

        assertEquals(new GridPoint2(1, 1), follower.getDestination());
    }

    @Test
    void shouldNotEnterCellAnotherTankIsHeadingTo() {
        GameWorld world = TestTanks.world();
        Tank first = tank(1, 1, Direction.RIGHT);
        Tank second = tank(3, 1, Direction.LEFT);
        world.add(first);
        world.add(second);

        first.move(Direction.RIGHT, world);
        second.move(Direction.LEFT, world);

        assertEquals(new GridPoint2(2, 1), first.getDestination());
        assertEquals(new GridPoint2(3, 1), second.getDestination());
    }

    @Test
    void shouldEnterCellAfterAnotherTankLeftIt() {
        GameWorld world = TestTanks.world();
        Tank leaving = tank(2, 1, Direction.RIGHT);
        Tank follower = tank(1, 1, Direction.RIGHT);
        world.add(leaving);
        world.add(follower);

        leaving.move(Direction.RIGHT, world);
        world.update(1f);
        follower.move(Direction.RIGHT, world);

        assertEquals(new GridPoint2(2, 1), follower.getDestination());
    }

    @Test
    void shouldStopAtLevelBorder() {
        GameWorld world = TestTanks.world();
        Tank tank = tank(9, 7, Direction.UP);
        world.add(tank);

        tank.move(Direction.RIGHT, world);
        tank.move(Direction.UP, world);

        assertEquals(new GridPoint2(9, 7), tank.getDestination());
        assertEquals(Direction.UP, tank.getDirection());
    }
}
