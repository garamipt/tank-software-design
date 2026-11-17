package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameWorld implements World, Updatable {
    private final Level level;
    private final List<LevelObject> objects = new ArrayList<>();
    private final List<LevelListener> listeners = new ArrayList<>();

    public GameWorld(Level level) {
        this.level = level;
    }

    public void addListener(LevelListener listener) {
        listeners.add(listener);
    }

    @Override
    public void add(LevelObject object) {
        objects.add(object);
        for (LevelListener listener : listeners) {
            listener.onObjectAdded(object);
        }
    }

    @Override
    public boolean contains(GridPoint2 point) {
        return level.contains(point);
    }

    @Override
    public boolean isFree(GridPoint2 point) {
        return contains(point) && objectsAt(point).isEmpty();
    }

    @Override
    public List<LevelObject> objectsAt(GridPoint2 point) {
        return objects.stream()
                .filter(object -> !object.isDestroyed() && object.occupies(point))
                .toList();
    }

    @Override
    public void update(float deltaTime) {
        for (LevelObject object : List.copyOf(objects)) {
            if (!object.isDestroyed()) {
                object.update(deltaTime);
            }
        }
        removeDestroyedObjects();
    }

    private void removeDestroyedObjects() {
        Iterator<LevelObject> iterator = objects.iterator();
        while (iterator.hasNext()) {
            LevelObject object = iterator.next();
            if (object.isDestroyed()) {
                iterator.remove();
                for (LevelListener listener : listeners) {
                    listener.onObjectRemoved(object);
                }
            }
        }
    }
}
