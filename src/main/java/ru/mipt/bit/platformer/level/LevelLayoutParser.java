package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
import java.util.List;
import ru.mipt.bit.platformer.model.LevelLayout;

public class LevelLayoutParser {
    private static final char TREE = 'T';
    private static final char PLAYER = 'X';
    private static final char EMPTY = '_';

    public LevelLayout parse(List<String> lines, int width, int height) {
        List<String> rows = withoutTrailingBlankLines(lines);
        if (rows.size() != height) {
            throw new IllegalArgumentException(
                    "Expected " + height + " rows but found " + rows.size());
        }

        List<GridPoint2> playerPositions = new ArrayList<>();
        List<GridPoint2> obstaclePositions = new ArrayList<>();
        for (int row = 0; row < height; row++) {
            String line = rows.get(row);
            if (line.length() != width) {
                throw new IllegalArgumentException(
                        "Row "
                                + (row + 1)
                                + ": expected "
                                + width
                                + " cells but found "
                                + line.length());
            }
            int y = height - 1 - row;
            for (int x = 0; x < width; x++) {
                char symbol = line.charAt(x);
                GridPoint2 cell = new GridPoint2(x, y);
                switch (symbol) {
                    case TREE -> obstaclePositions.add(cell);
                    case PLAYER -> playerPositions.add(cell);
                    case EMPTY -> {}
                    default ->
                            throw new IllegalArgumentException(
                                    "Unknown symbol '"
                                            + symbol
                                            + "' at row "
                                            + (row + 1)
                                            + ", column "
                                            + (x + 1));
                }
            }
        }

        if (playerPositions.size() != 1) {
            throw new IllegalArgumentException(
                    "Expected exactly one player position '"
                            + PLAYER
                            + "' but found "
                            + playerPositions.size());
        }
        return new LevelLayout(playerPositions.get(0), obstaclePositions);
    }

    private static List<String> withoutTrailingBlankLines(List<String> lines) {
        int end = lines.size();
        while (end > 0 && lines.get(end - 1).isBlank()) {
            end--;
        }
        return lines.subList(0, end);
    }
}
