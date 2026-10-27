package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import ru.mipt.bit.platformer.model.LevelLayout;

public class RandomBotsLayoutSource implements LevelLayoutSource {
    private final LevelLayoutSource levelSource;
    private final int botCount;
    private final Random random;

    public RandomBotsLayoutSource(LevelLayoutSource levelSource, int botCount, Random random) {
        if (botCount < 0) {
            throw new IllegalArgumentException("Bot count must not be negative: " + botCount);
        }
        this.levelSource = levelSource;
        this.botCount = botCount;
        this.random = random;
    }

    @Override
    public LevelLayout createLayout(int width, int height) {
        LevelLayout layout = levelSource.createLayout(width, height);
        List<GridPoint2> freeCells = LevelCells.all(width, height);
        freeCells.removeAll(layout.occupiedPositions());
        if (botCount > freeCells.size()) {
            throw new IllegalArgumentException(
                    "Cannot place " + botCount + " bots into " + freeCells.size() + " free cells");
        }
        Collections.shuffle(freeCells, random);

        List<GridPoint2> botPositions = new ArrayList<>(layout.botPositions());
        botPositions.addAll(freeCells.subList(0, botCount));
        return new LevelLayout(layout.playerPosition(), layout.obstaclePositions(), botPositions);
    }
}
