package ru.mipt.bit.platformer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;
import org.junit.jupiter.api.Test;

class LevelLayoutTest {

    @Test
    void shouldIgnoreChangesToConstructorArguments() {
        GridPoint2 playerPosition = new GridPoint2(1, 1);
        GridPoint2 obstaclePosition = new GridPoint2(3, 3);
        LevelLayout layout =
                new LevelLayout(playerPosition, Direction.UP, List.of(obstaclePosition));

        playerPosition.set(7, 7);
        obstaclePosition.set(8, 8);

        assertEquals(new GridPoint2(1, 1), layout.playerPosition());
        assertEquals(List.of(new GridPoint2(3, 3)), layout.obstaclePositions());
    }

    @Test
    void shouldNotExposeInternalPositions() {
        LevelLayout layout =
                new LevelLayout(new GridPoint2(1, 1), Direction.UP, List.of(new GridPoint2(3, 3)));

        layout.playerPosition().set(7, 7);
        layout.obstaclePositions().get(0).set(8, 8);

        assertEquals(new GridPoint2(1, 1), layout.playerPosition());
        assertEquals(List.of(new GridPoint2(3, 3)), layout.obstaclePositions());
    }
}
