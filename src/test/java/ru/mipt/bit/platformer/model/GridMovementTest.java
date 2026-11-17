package ru.mipt.bit.platformer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

class GridMovementTest {

    @Test
    void shouldStartStandingInOwnCell() {
        GridMovement movement = new GridMovement(new GridPoint2(1, 1), 1f);

        assertFalse(movement.isMoving());
        assertTrue(movement.occupies(new GridPoint2(1, 1)));
        assertFalse(movement.occupies(new GridPoint2(2, 1)));
    }

    @Test
    void shouldOccupyBothCellsWhileMoving() {
        GridMovement movement = new GridMovement(new GridPoint2(1, 1), 1f);

        movement.startTowards(new GridPoint2(2, 1));

        assertTrue(movement.isMoving());
        assertTrue(movement.occupies(new GridPoint2(1, 1)));
        assertTrue(movement.occupies(new GridPoint2(2, 1)));
    }

    @Test
    void shouldArriveAfterConfiguredTime() {
        GridMovement movement = new GridMovement(new GridPoint2(1, 1), 1f);

        movement.startTowards(new GridPoint2(2, 1));
        movement.update(0.5f);

        assertEquals(new GridPoint2(1, 1), movement.getCoordinates());
        assertEquals(0.5f, movement.getProgress(), 1e-6f);

        movement.update(0.5f);

        assertEquals(new GridPoint2(2, 1), movement.getCoordinates());
        assertFalse(movement.occupies(new GridPoint2(1, 1)));
    }

    @Test
    void shouldNotExposeInternalCoordinates() {
        GridMovement movement = new GridMovement(new GridPoint2(1, 1), 1f);

        movement.getCoordinates().set(5, 5);
        movement.getDestination().set(5, 5);

        assertEquals(new GridPoint2(1, 1), movement.getCoordinates());
        assertEquals(new GridPoint2(1, 1), movement.getDestination());
    }
}
