package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LevelTest {

    @Test
    void shouldTreatEmptyCellAsFree() {
        Level level = new Level(10, 8, List.of());

        assertTrue(level.isFree(new GridPoint2(5, 5)));
    }

    @Test
    void shouldTreatCellWithObstacleAsOccupied() {
        Level level = new Level(10, 8, List.of(new Obstacle(new GridPoint2(1, 3))));

        assertFalse(level.isFree(new GridPoint2(1, 3)));
    }

    @ParameterizedTest
    @CsvSource({"-1, 0", "0, -1", "10, 0", "0, 8"})
    void shouldTreatCellOutsideLevelAsOccupied(int x, int y) {
        Level level = new Level(10, 8, List.of());

        assertFalse(level.isFree(new GridPoint2(x, y)));
    }
}
