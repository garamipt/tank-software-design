package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;

public class Level implements CollisionChecker {
    private final int width;
    private final int height;
    private final List<Obstacle> obstacles;

    public Level(int width, int height, List<Obstacle> obstacles) {
        this.width = width;
        this.height = height;
        this.obstacles = List.copyOf(obstacles);
    }

    @Override
    public boolean isFree(GridPoint2 point) {
        if (isOutOfBounds(point)) {
            return false;
        }
        for (Obstacle obstacle : obstacles) {
            if (obstacle.occupies(point)) {
                return false;
            }
        }
        return true;
    }

    private boolean isOutOfBounds(GridPoint2 point) {
        return point.x < 0 || point.y < 0 || point.x >= width || point.y >= height;
    }

    public List<Obstacle> getObstacles() {
        return obstacles;
    }
}
