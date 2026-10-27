package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
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
        List<GridPoint2> cells = allCells(width, height);
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

    private static List<GridPoint2> allCells(int width, int height) {
        List<GridPoint2> cells = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells.add(new GridPoint2(x, y));
            }
        }
        return cells;
    }
}
