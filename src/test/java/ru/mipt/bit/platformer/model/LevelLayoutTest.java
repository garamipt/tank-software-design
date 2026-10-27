package ru.mipt.bit.platformer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;
import org.junit.jupiter.api.Test;

class LevelLayoutTest {

    @Test
    void shouldIgnoreChangesToConstructorArguments() {
        GridPoint2 playerPosition = new GridPoint2(1, 1);
        GridPoint2 obstaclePosition = new GridPoint2(3, 3);
        LevelLayout layout = new LevelLayout(playerPosition, List.of(obstaclePosition));

        playerPosition.set(7, 7);
        obstaclePosition.set(8, 8);

        assertEquals(new GridPoint2(1, 1), layout.playerPosition());
        assertEquals(List.of(new GridPoint2(3, 3)), layout.obstaclePositions());
    }

    @Test
    void shouldNotExposeInternalPositions() {
        LevelLayout layout = new LevelLayout(new GridPoint2(1, 1), List.of(new GridPoint2(3, 3)));

        layout.playerPosition().set(7, 7);
        layout.obstaclePositions().get(0).set(8, 8);

        assertEquals(new GridPoint2(1, 1), layout.playerPosition());
        assertEquals(List.of(new GridPoint2(3, 3)), layout.obstaclePositions());
    }

    @Test
    void shouldRejectPlayerOnObstacle() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new LevelLayout(new GridPoint2(1, 1), List.of(new GridPoint2(1, 1))));
    }

    @Test
    void shouldHaveNoBotsByDefault() {
        LevelLayout layout = new LevelLayout(new GridPoint2(1, 1), List.of());

        assertTrue(layout.botPositions().isEmpty());
    }

    @Test
    void shouldRejectBotOnPlayer() {
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        new LevelLayout(
                                new GridPoint2(1, 1), List.of(), List.of(new GridPoint2(1, 1))));
    }

    @Test
    void shouldRejectBotOnObstacle() {
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        new LevelLayout(
                                new GridPoint2(1, 1),
                                List.of(new GridPoint2(2, 2)),
                                List.of(new GridPoint2(2, 2))));
    }

    @Test
    void shouldListEveryOccupiedPosition() {
        LevelLayout layout =
                new LevelLayout(
                        new GridPoint2(1, 1),
                        List.of(new GridPoint2(2, 2)),
                        List.of(new GridPoint2(3, 3)));

        assertEquals(
                List.of(new GridPoint2(1, 1), new GridPoint2(2, 2), new GridPoint2(3, 3)),
                layout.occupiedPositions());
    }
}
