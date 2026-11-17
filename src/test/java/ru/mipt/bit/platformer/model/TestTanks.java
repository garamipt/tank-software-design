package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
import java.util.List;

public final class TestTanks {
    public static final BulletSettings BULLET = new BulletSettings(25, 0.1f);
    public static final TankSettings SETTINGS = new TankSettings(0.4f, 100, 1f, BULLET);

    private TestTanks() {}

    public static Tank tank(int x, int y, Direction direction) {
        return new Tank(new GridPoint2(x, y), direction, SETTINGS);
    }

    public static Tank tank(int x, int y, Direction direction, TankSettings settings) {
        return new Tank(new GridPoint2(x, y), direction, settings);
    }

    public static TankSettings withSecondsPerCell(float secondsPerCell) {
        return new TankSettings(
                secondsPerCell, SETTINGS.health(), SETTINGS.reloadSeconds(), SETTINGS.bullet());
    }

    public static TankSettings withHealth(int health) {
        return new TankSettings(
                SETTINGS.secondsPerCell(), health, SETTINGS.reloadSeconds(), SETTINGS.bullet());
    }

    public static GameWorld world() {
        return new GameWorld(new Level(10, 8));
    }

    public static class RecordingListener implements LevelListener {
        public final List<LevelObject> added = new ArrayList<>();
        public final List<LevelObject> removed = new ArrayList<>();

        @Override
        public void onObjectAdded(LevelObject object) {
            added.add(object);
        }

        @Override
        public void onObjectRemoved(LevelObject object) {
            removed.add(object);
        }

        public List<Bullet> addedBullets() {
            return added.stream().filter(Bullet.class::isInstance).map(Bullet.class::cast).toList();
        }
    }
}
