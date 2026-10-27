package ru.mipt.bit.platformer.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.math.GridPoint2;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.mipt.bit.platformer.model.LevelLayout;

class RandomLevelLayoutSourceTest {

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 42, 2026})
    void shouldPlaceDistinctObstaclesInsideLevel(long seed) {
        LevelLayout layout = new RandomLevelLayoutSource(10, new Random(seed)).createLayout(10, 8);
        List<GridPoint2> obstacles = layout.obstaclePositions();

        assertEquals(10, obstacles.size());
        assertEquals(10, new HashSet<>(obstacles).size());
        assertFalse(obstacles.contains(layout.playerPosition()));
        assertTrue(isInside(layout.playerPosition(), 10, 8));
        assertTrue(obstacles.stream().allMatch(position -> isInside(position, 10, 8)));
    }

    @Test
    void shouldBeReproducibleWithSameSeed() {
        LevelLayout first = new RandomLevelLayoutSource(5, new Random(7)).createLayout(10, 8);
        LevelLayout second = new RandomLevelLayoutSource(5, new Random(7)).createLayout(10, 8);

        assertEquals(first, second);
    }

    @Test
    void shouldFillEveryCellButPlayerWhenAsked() {
        LevelLayout layout = new RandomLevelLayoutSource(3, new Random(1)).createLayout(2, 2);

        assertEquals(3, layout.obstaclePositions().size());
    }

    @Test
    void shouldRejectMoreObstaclesThanFreeCells() {
        RandomLevelLayoutSource source = new RandomLevelLayoutSource(4, new Random(1));

        assertThrows(IllegalArgumentException.class, () -> source.createLayout(2, 2));
    }

    @Test
    void shouldRejectNegativeObstacleCount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new RandomLevelLayoutSource(-1, new Random(1)));
    }

    private static boolean isInside(GridPoint2 position, int width, int height) {
        return position.x >= 0 && position.y >= 0 && position.x < width && position.y < height;
    }
}
