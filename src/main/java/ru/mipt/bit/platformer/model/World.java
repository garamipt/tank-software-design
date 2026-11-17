package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;

public interface World extends CollisionChecker {
    void add(LevelObject object);

    boolean contains(GridPoint2 point);

    List<LevelObject> objectsAt(GridPoint2 point);
}
