package ru.mipt.bit.platformer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.mipt.bit.platformer.model.TestTanks.tank;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.TestTanks.RecordingListener;

class TankTest {

    @Test
    void shouldStartMovingToFreeCell() {
        Tank tank = tank(1, 1, Direction.RIGHT);

        tank.move(Direction.RIGHT, point -> true);

        assertEquals(new GridPoint2(2, 1), tank.getDestination());
        assertEquals(0f, tank.getMovementProgress(), 1e-6f);
        assertEquals(Direction.RIGHT, tank.getDirection());
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
    }

    @Test
    void shouldNotMoveIntoObstacle() {
        Tank tank = tank(1, 2, Direction.RIGHT);

        tank.move(Direction.UP, point -> false);

        assertEquals(new GridPoint2(1, 2), tank.getCoordinates());
        assertEquals(new GridPoint2(1, 2), tank.getDestination());
        assertEquals(1f, tank.getMovementProgress(), 1e-6f);
        assertEquals(Direction.UP, tank.getDirection());
    }

    @Test
    void shouldIgnoreMoveCommandWhileMoving() {
        Tank tank = tank(1, 1, Direction.RIGHT);

        tank.move(Direction.RIGHT, point -> true);
        tank.move(Direction.UP, point -> true);

        assertEquals(new GridPoint2(2, 1), tank.getDestination());
        assertEquals(Direction.RIGHT, tank.getDirection());
    }

    @Test
    void shouldArriveAtDestinationAfterUpdate() {
        Tank tank = tank(1, 1, Direction.RIGHT);

        tank.move(Direction.RIGHT, point -> true);
        tank.update(1f);

        assertEquals(new GridPoint2(2, 1), tank.getCoordinates());
        assertEquals(1f, tank.getMovementProgress(), 1e-6f);
    }

    @Test
    void shouldCrossCellInConfiguredTime() {
        Tank tank = tank(1, 1, Direction.RIGHT, TestTanks.withSecondsPerCell(1f));

        tank.move(Direction.RIGHT, point -> true);
        tank.update(0.5f);

        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
        assertEquals(0.5f, tank.getMovementProgress(), 1e-6f);

        tank.update(0.5f);

        assertEquals(new GridPoint2(2, 1), tank.getCoordinates());
    }

    @Test
    void shouldOccupyBothCellsWhileMoving() {
        Tank tank = tank(1, 1, Direction.RIGHT);

        tank.move(Direction.RIGHT, point -> true);

        assertTrue(tank.occupies(new GridPoint2(1, 1)));
        assertTrue(tank.occupies(new GridPoint2(2, 1)));
    }

    @Test
    void shouldReleaseOriginCellAfterArrival() {
        Tank tank = tank(1, 1, Direction.RIGHT);

        tank.move(Direction.RIGHT, point -> true);
        tank.update(1f);

        assertFalse(tank.occupies(new GridPoint2(1, 1)));
        assertTrue(tank.occupies(new GridPoint2(2, 1)));
    }

    @Test
    void shouldRegisterBulletInNextCellInFrontOfTank() {
        GameWorld world = TestTanks.world();
        RecordingListener listener = new RecordingListener();
        world.addListener(listener);
        Tank tank = tank(1, 1, Direction.RIGHT);

        tank.shoot(world);

        assertEquals(1, listener.addedBullets().size());
        Bullet bullet = listener.addedBullets().get(0);
        assertEquals(new GridPoint2(2, 1), bullet.getCoordinates());
        assertEquals(Direction.RIGHT.getRotation(), bullet.getRotation(), 1e-6f);
    }

    @Test
    void shouldShootAheadOfDestinationWhileMoving() {
        GameWorld world = TestTanks.world();
        RecordingListener listener = new RecordingListener();
        world.addListener(listener);
        Tank tank = tank(1, 1, Direction.RIGHT);

        tank.move(Direction.RIGHT, world);
        tank.shoot(world);

        assertEquals(new GridPoint2(3, 1), listener.addedBullets().get(0).getCoordinates());
    }

    @Test
    void shouldNotShootWhileReloading() {
        GameWorld world = TestTanks.world();
        RecordingListener listener = new RecordingListener();
        world.addListener(listener);
        Tank tank = tank(1, 1, Direction.RIGHT);

        tank.shoot(world);
        tank.shoot(world);

        assertEquals(1, listener.addedBullets().size());
    }

    @Test
    void shouldShootAgainAfterReload() {
        GameWorld world = TestTanks.world();
        RecordingListener listener = new RecordingListener();
        world.addListener(listener);
        Tank tank = tank(1, 1, Direction.RIGHT);

        tank.shoot(world);
        tank.update(TestTanks.SETTINGS.reloadSeconds());
        tank.shoot(world);

        assertEquals(2, listener.addedBullets().size());
    }

    @Test
    void shouldLoseHealthWhenHit() {
        Tank tank = tank(1, 1, Direction.RIGHT);

        tank.receiveHit(25);

        assertEquals(75, tank.getHealth());
        assertFalse(tank.isDestroyed());
    }

    @Test
    void shouldBeDestroyedWhenHealthRunsOut() {
        Tank tank = tank(1, 1, Direction.RIGHT);

        tank.receiveHit(100);

        assertTrue(tank.isDestroyed());
    }

    @Test
    void shouldIgnoreCommandsWhenDestroyed() {
        GameWorld world = TestTanks.world();
        RecordingListener listener = new RecordingListener();
        world.addListener(listener);
        Tank tank = tank(1, 1, Direction.RIGHT);
        tank.receiveHit(100);

        tank.move(Direction.UP, world);
        tank.shoot(world);

        assertEquals(new GridPoint2(1, 1), tank.getDestination());
        assertEquals(Direction.RIGHT, tank.getDirection());
        assertTrue(listener.addedBullets().isEmpty());
    }
}
