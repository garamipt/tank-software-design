package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record LevelLayout(
        GridPoint2 playerPosition,
        List<GridPoint2> obstaclePositions,
        List<GridPoint2> botPositions) {

    public LevelLayout {
        playerPosition = new GridPoint2(playerPosition);
        obstaclePositions = copyOf(obstaclePositions);
        botPositions = copyOf(botPositions);
        requireDistinctCells(playerPosition, obstaclePositions, botPositions);
    }

    public LevelLayout(GridPoint2 playerPosition, List<GridPoint2> obstaclePositions) {
        this(playerPosition, obstaclePositions, List.of());
    }

    @Override
    public GridPoint2 playerPosition() {
        return new GridPoint2(playerPosition);
    }

    @Override
    public List<GridPoint2> obstaclePositions() {
        return copyOf(obstaclePositions);
    }

    @Override
    public List<GridPoint2> botPositions() {
        return copyOf(botPositions);
    }

    public List<GridPoint2> occupiedPositions() {
        List<GridPoint2> occupied = new ArrayList<>();
        occupied.add(playerPosition());
        occupied.addAll(obstaclePositions());
        occupied.addAll(botPositions());
        return occupied;
    }

    private static void requireDistinctCells(
            GridPoint2 playerPosition,
            List<GridPoint2> obstaclePositions,
            List<GridPoint2> botPositions) {
        Set<GridPoint2> occupied = new HashSet<>();
        occupied.add(playerPosition);
        for (GridPoint2 position : concat(obstaclePositions, botPositions)) {
            if (!occupied.add(position)) {
                throw new IllegalArgumentException(
                        "Cell " + position + " is occupied by more than one object");
            }
        }
    }

    private static List<GridPoint2> concat(List<GridPoint2> first, List<GridPoint2> second) {
        List<GridPoint2> result = new ArrayList<>(first);
        result.addAll(second);
        return result;
    }

    private static List<GridPoint2> copyOf(List<GridPoint2> positions) {
        return positions.stream().map(GridPoint2::new).toList();
    }
}
