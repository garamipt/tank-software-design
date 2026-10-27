package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;

public class Level implements CollisionChecker {
    private final int width;
    private final int height;
    private final List<Occupant> occupants;

    public Level(int width, int height, List<Occupant> occupants) {
        this.width = width;
        this.height = height;
        this.occupants = List.copyOf(occupants);
    }

    @Override
    public boolean isFree(GridPoint2 point) {
        if (isOutOfBounds(point)) {
            return false;
        }
        for (Occupant occupant : occupants) {
            if (occupant.occupies(point)) {
                return false;
            }
        }
        return true;
    }

    private boolean isOutOfBounds(GridPoint2 point) {
        return point.x < 0 || point.y < 0 || point.x >= width || point.y >= height;
    }
}
