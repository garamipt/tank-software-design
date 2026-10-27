package ru.mipt.bit.platformer.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.model.LevelLayout;

class FileLevelLayoutSourceTest {

    @Test
    void shouldReadLayoutFromResource() {
        LevelLayout layout = new FileLevelLayoutSource("levels/small-level.txt").createLayout(2, 2);

        assertEquals(new GridPoint2(1, 0), layout.playerPosition());
        assertEquals(List.of(new GridPoint2(0, 1)), layout.obstaclePositions());
    }

    @Test
    void shouldFitBundledLevelIntoGameMap() {
        LevelLayout layout = new FileLevelLayoutSource("level.txt").createLayout(10, 8);

        assertEquals(new GridPoint2(5, 3), layout.playerPosition());
    }

    @Test
    void shouldRejectMissingFile() {
        FileLevelLayoutSource source = new FileLevelLayoutSource("levels/missing.txt");

        assertThrows(IllegalArgumentException.class, () -> source.createLayout(2, 2));
    }
}
