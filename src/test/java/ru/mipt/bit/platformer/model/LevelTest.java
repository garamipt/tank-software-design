package ru.mipt.bit.platformer.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LevelTest {

    @ParameterizedTest
    @CsvSource({"0, 0", "9, 7", "5, 5"})
    void shouldContainCellsInsideLevel(int x, int y) {
        assertTrue(new Level(10, 8).contains(new GridPoint2(x, y)));
    }

    @ParameterizedTest
    @CsvSource({"-1, 0", "0, -1", "10, 0", "0, 8"})
    void shouldNotContainCellsOutsideLevel(int x, int y) {
        assertFalse(new Level(10, 8).contains(new GridPoint2(x, y)));
    }

    @Test
    void shouldTreatSingleCellLevelAsContainingOnlyOrigin() {
        Level level = new Level(1, 1);

        assertTrue(level.contains(new GridPoint2(0, 0)));
        assertFalse(level.contains(new GridPoint2(1, 0)));
    }
}
