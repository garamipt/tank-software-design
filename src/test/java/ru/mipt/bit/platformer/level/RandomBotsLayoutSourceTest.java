package ru.mipt.bit.platformer.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.mipt.bit.platformer.model.LevelLayout;

class RandomBotsLayoutSourceTest {
    private static final LevelLayoutSource SMALL_LEVEL =
            (width, height) -> new LevelLayout(new GridPoint2(0, 0), List.of(new GridPoint2(1, 1)));

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 42})
    void shouldPlaceBotsOnFreeCellsOnly(long seed) {
        LevelLayout layout =
                new RandomBotsLayoutSource(SMALL_LEVEL, 2, new Random(seed)).createLayout(2, 2);

        assertEquals(
                List.of(new GridPoint2(0, 1), new GridPoint2(1, 0)),
                layout.botPositions().stream()
                        .sorted((a, b) -> a.x != b.x ? a.x - b.x : a.y - b.y)
                        .toList());
    }

    @Test
    void shouldKeepPlayerAndObstaclesFromWrappedSource() {
        LevelLayout layout =
                new RandomBotsLayoutSource(SMALL_LEVEL, 1, new Random(1)).createLayout(2, 2);

        assertEquals(new GridPoint2(0, 0), layout.playerPosition());
        assertEquals(List.of(new GridPoint2(1, 1)), layout.obstaclePositions());
    }

    @Test
    void shouldKeepBotsFromWrappedSource() {
        LevelLayoutSource withOneBot = new RandomBotsLayoutSource(SMALL_LEVEL, 1, new Random(1));

        LevelLayout layout =
                new RandomBotsLayoutSource(withOneBot, 1, new Random(1)).createLayout(2, 2);

        assertEquals(2, layout.botPositions().size());
    }

    @Test
    void shouldRejectMoreBotsThanFreeCells() {
        RandomBotsLayoutSource source = new RandomBotsLayoutSource(SMALL_LEVEL, 3, new Random(1));

        assertThrows(IllegalArgumentException.class, () -> source.createLayout(2, 2));
    }

    @Test
    void shouldRejectNegativeBotCount() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new RandomBotsLayoutSource(SMALL_LEVEL, -1, new Random(1)));
    }
}
