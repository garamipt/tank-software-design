package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class Level {
    private final int width;
    private final int height;

    public Level(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public boolean contains(GridPoint2 point) {
        return point.x >= 0 && point.y >= 0 && point.x < width && point.y < height;
    }
}
