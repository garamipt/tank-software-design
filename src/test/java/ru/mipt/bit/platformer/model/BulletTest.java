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

class BulletTest {
    private static final float ONE_CELL = TestTanks.BULLET.secondsPerCell();

    private GameWorld world;
    private RecordingListener listener;

    @BeforeEach
    void setUp() {
        world = TestTanks.world();
        listener = new RecordingListener();
        world.addListener(listener);
    }

    @Test
    void shouldFlyInShooterDirection() {
        Bullet bullet = bulletAt(1, 1, Direction.RIGHT);

        world.update(ONE_CELL);
        world.update(ONE_CELL);

        assertEquals(new GridPoint2(3, 1), bullet.getCoordinates());
        assertFalse(bullet.isDestroyed());
    }

    @Test
    void shouldDisappearAtLevelBorder() {
        Bullet bullet = bulletAt(9, 1, Direction.RIGHT);

        world.update(ONE_CELL);

        assertEquals(List.of(bullet), listener.removed);
    }

    @Test
    void shouldDisappearWhenSpawnedOutsideLevel() {
        Tank tank = tank(9, 1, Direction.RIGHT);
        world.add(tank);

        tank.shoot(world);
        world.update(ONE_CELL);

        assertEquals(listener.addedBullets(), listener.removed);
    }

    @Test
    void shouldDisappearOnTreeAndKeepTree() {
        Obstacle tree = new Obstacle(new GridPoint2(3, 1));
        world.add(tree);
        Bullet bullet = bulletAt(2, 1, Direction.RIGHT);

        world.update(ONE_CELL);

        assertEquals(List.of(bullet), listener.removed);
        assertFalse(world.isFree(new GridPoint2(3, 1)));
    }

    @Test
    void shouldDamageTankItHits() {
        Tank target = tank(3, 1, Direction.LEFT);
        world.add(target);
        Bullet bullet = bulletAt(2, 1, Direction.RIGHT);

        world.update(ONE_CELL);

        assertEquals(100 - TestTanks.BULLET.damage(), target.getHealth());
        assertEquals(List.of(bullet), listener.removed);
    }

    @Test
    void shouldRemoveTankWhenHealthRunsOut() {
        Tank target = tank(3, 1, Direction.LEFT, TestTanks.withHealth(TestTanks.BULLET.damage()));
        world.add(target);
        Bullet bullet = bulletAt(2, 1, Direction.RIGHT);

        world.update(ONE_CELL);

        assertTrue(listener.removed.containsAll(List.of(target, bullet)));
        assertTrue(world.isFree(new GridPoint2(3, 1)));
    }

    @Test
    void shouldDestroyBothBulletsOnCollision() {
        Bullet first = bulletAt(2, 1, Direction.RIGHT);
        Bullet second = bulletAt(3, 1, Direction.LEFT);

        world.update(ONE_CELL);

        assertTrue(listener.removed.containsAll(List.of(first, second)));
    }

    @Test
    void shouldHitEnemyStandingPointBlank() {
        Tank shooter = tank(1, 1, Direction.RIGHT);
        Tank enemy = tank(2, 1, Direction.LEFT);
        world.add(shooter);
        world.add(enemy);

        shooter.shoot(world);
        world.update(ONE_CELL);

        assertEquals(100 - TestTanks.BULLET.damage(), enemy.getHealth());
        assertEquals(100, shooter.getHealth());
        assertEquals(listener.addedBullets(), listener.removed);
    }

    @Test
    void shouldLetTanksShootingPointBlankHitEachOther() {
        Tank left = tank(1, 1, Direction.RIGHT);
        Tank right = tank(2, 1, Direction.LEFT);
        world.add(left);
        world.add(right);

        left.shoot(world);
        right.shoot(world);
        world.update(ONE_CELL);

        assertEquals(100 - TestTanks.BULLET.damage(), left.getHealth());
        assertEquals(100 - TestTanks.BULLET.damage(), right.getHealth());
    }

    @Test
    void shouldBlockTanksFromEnteringItsCells() {
        Bullet bullet = bulletAt(2, 1, Direction.RIGHT);
        world.update(ONE_CELL / 2);

        assertTrue(bullet.occupies(new GridPoint2(2, 1)));
        assertFalse(world.isFree(new GridPoint2(3, 1)));
    }

    private Bullet bulletAt(int x, int y, Direction direction) {
        Bullet bullet = new Bullet(new GridPoint2(x, y), direction, TestTanks.BULLET, world);
        world.add(bullet);
        return bullet;
    }
}
