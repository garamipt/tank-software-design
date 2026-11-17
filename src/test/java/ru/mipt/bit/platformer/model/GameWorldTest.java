package ru.mipt.bit.platformer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.mipt.bit.platformer.model.TestTanks.tank;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.TestTanks.RecordingListener;

class GameWorldTest {
    private GameWorld world;
    private RecordingListener listener;

    @BeforeEach
    void setUp() {
        world = TestTanks.world();
        listener = new RecordingListener();
        world.addListener(listener);
    }

    @Test
    void shouldNotifyListenersAboutAddedObjects() {
        Tank tank = tank(1, 1, Direction.UP);
        Obstacle tree = new Obstacle(new GridPoint2(3, 3));

        world.add(tank);
        world.add(tree);

        assertEquals(List.of(tank, tree), listener.added);
    }

    @Test
    void shouldUpdateEveryObject() {
        Tank first = tank(1, 1, Direction.RIGHT);
        Tank second = tank(5, 5, Direction.UP);
        world.add(first);
        world.add(second);

        first.move(Direction.RIGHT, world);
        second.move(Direction.UP, world);
        world.update(1f);

        assertEquals(new GridPoint2(2, 1), first.getCoordinates());
        assertEquals(new GridPoint2(5, 6), second.getCoordinates());
    }

    @Test
    void shouldRemoveDestroyedObjectsAndNotifyListeners() {
        Tank tank = tank(1, 1, Direction.UP);
        world.add(tank);

        tank.receiveHit(100);
        world.update(0.1f);

        assertEquals(List.of(tank), listener.removed);
        assertTrue(world.isFree(new GridPoint2(1, 1)));
    }

    @Test
    void shouldTreatCellsOfObjectsAsOccupied() {
        Tank tank = tank(1, 1, Direction.RIGHT);
        world.add(tank);
        world.add(new Obstacle(new GridPoint2(5, 5)));

        tank.move(Direction.RIGHT, world);

        assertFalse(world.isFree(new GridPoint2(1, 1)));
        assertFalse(world.isFree(new GridPoint2(2, 1)));
        assertFalse(world.isFree(new GridPoint2(5, 5)));
        assertTrue(world.isFree(new GridPoint2(3, 1)));
    }

    @Test
    void shouldTreatCellsOutsideLevelAsOccupied() {
        assertFalse(world.isFree(new GridPoint2(-1, 0)));
        assertFalse(world.isFree(new GridPoint2(10, 0)));
    }

    @Test
    void shouldIgnoreDestroyedObjectsBeforeTheyAreRemoved() {
        Tank tank = tank(1, 1, Direction.UP);
        world.add(tank);

        tank.receiveHit(100);

        assertTrue(world.objectsAt(new GridPoint2(1, 1)).isEmpty());
    }
}
