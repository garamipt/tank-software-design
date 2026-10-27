package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import java.util.List;

public record LevelLayout(GridPoint2 playerPosition, List<GridPoint2> obstaclePositions) {

    public LevelLayout {
        playerPosition = new GridPoint2(playerPosition);
        obstaclePositions = copyOf(obstaclePositions);
        if (obstaclePositions.contains(playerPosition)) {
            throw new IllegalArgumentException(
                    "Player position " + playerPosition + " is occupied by an obstacle");
        }
    }

    @Override
    public GridPoint2 playerPosition() {
        return new GridPoint2(playerPosition);
    }

    @Override
    public List<GridPoint2> obstaclePositions() {
        return copyOf(obstaclePositions);
    }

    private static List<GridPoint2> copyOf(List<GridPoint2> positions) {
        return positions.stream().map(GridPoint2::new).toList();
    }
}
