package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import ru.mipt.bit.platformer.model.LevelLayout;

public class RandomLevelLayoutSource implements LevelLayoutSource {
    private final int obstacleCount;
    private final Random random;

    public RandomLevelLayoutSource(int obstacleCount, Random random) {
        if (obstacleCount < 0) {
            throw new IllegalArgumentException(
                    "Obstacle count must not be negative: " + obstacleCount);
        }
        this.obstacleCount = obstacleCount;
        this.random = random;
    }

    @Override
    public LevelLayout createLayout(int width, int height) {
        List<GridPoint2> cells = LevelCells.all(width, height);
        if (obstacleCount >= cells.size()) {
            throw new IllegalArgumentException(
                    "Cannot place "
                            + obstacleCount
                            + " obstacles and a player on a "
                            + width
                            + "x"
                            + height
                            + " level");
        }
        Collections.shuffle(cells, random);
        return new LevelLayout(cells.get(0), cells.subList(1, obstacleCount + 1));
    }
}
