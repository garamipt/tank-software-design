package ru.mipt.bit.platformer.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.mipt.bit.platformer.model.LevelLayout;

class LevelLayoutParserTest {
    private final LevelLayoutParser parser = new LevelLayoutParser();

    @Test
    void shouldPlaceTopRowAtHighestY() {
        LevelLayout layout = parser.parse(List.of("T_", "_X"), 2, 2);

        assertEquals(new GridPoint2(1, 0), layout.playerPosition());
        assertEquals(List.of(new GridPoint2(0, 1)), layout.obstaclePositions());
    }

    @Test
    void shouldParseLevelFromTaskExample() {
        String level =
                """
                ___T__T___
                __TT__TTTT
                _________T
                TTTT__T__T
                _____X____
                __________
                """;

        LevelLayout layout = parser.parse(level.lines().toList(), 10, 6);

        assertEquals(new GridPoint2(5, 1), layout.playerPosition());
        assertEquals(15, layout.obstaclePositions().size());
        assertTrue(layout.obstaclePositions().contains(new GridPoint2(3, 5)));
        assertTrue(layout.obstaclePositions().contains(new GridPoint2(0, 2)));
        assertTrue(layout.obstaclePositions().contains(new GridPoint2(9, 3)));
    }

    @Test
    void shouldIgnoreTrailingBlankLines() {
        LevelLayout layout = parser.parse(List.of("T_", "_X", "", "  "), 2, 2);

        assertEquals(new GridPoint2(1, 0), layout.playerPosition());
    }

    @Test
    void shouldRejectUnknownSymbol() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(List.of("T?", "_X"), 2, 2));
    }

    @ParameterizedTest
    @ValueSource(strings = {"__,__", "X_,_X"})
    void shouldRequireExactlyOnePlayer(String rows) {
        List<String> lines = List.of(rows.split(","));

        assertThrows(IllegalArgumentException.class, () -> parser.parse(lines, 2, 2));
    }

    @Test
    void shouldRejectWrongNumberOfRows() {
        assertThrows(IllegalArgumentException.class, () -> parser.parse(List.of("_X"), 2, 2));
    }

    @Test
    void shouldRejectRowOfWrongWidth() {
        assertThrows(
                IllegalArgumentException.class, () -> parser.parse(List.of("T__", "_X"), 2, 2));
    }
}
