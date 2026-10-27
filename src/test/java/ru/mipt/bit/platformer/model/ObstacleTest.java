package ru.mipt.bit.platformer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

public class ObstacleTest {

    @Test
    public void shouldOccupyOwnCellOnly() {
        Obstacle obstacle = new Obstacle(new GridPoint2(1, 2));

        assertTrue(obstacle.occupies(new GridPoint2(1, 2)));

        assertFalse(obstacle.occupies(new GridPoint2(2, 1)));
        assertFalse(obstacle.occupies(new GridPoint2(1, 3)));
        assertFalse(obstacle.occupies(new GridPoint2(3, 2)));
        assertFalse(obstacle.occupies(new GridPoint2(2, 3)));
    }

    @Test
    public void shouldIgnoreChangesToConstructorArgument() {
        GridPoint2 coordinates = new GridPoint2(1, 2);
        Obstacle obstacle = new Obstacle(coordinates);

        coordinates.set(3, 4);

        assertTrue(obstacle.occupies(new GridPoint2(1, 2)));
        assertFalse(obstacle.occupies(new GridPoint2(3, 4)));
    }

    @Test
    public void shouldBeMotionless() {
        Obstacle obstacle = new Obstacle(new GridPoint2(1, 3));

        assertEquals(obstacle.getCoordinates(), obstacle.getDestination());
        assertEquals(1f, obstacle.getMovementProgress(), 1e-6f);
        assertEquals(0f, obstacle.getRotation(), 1e-6f);
    }
}
