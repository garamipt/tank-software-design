package ru.mipt.bit.platformer;

import static ru.mipt.bit.platformer.graphics.GdxGameUtils.getSingleLayer;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import java.util.List;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.model.*;

public class LevelLoader {
    private static final String TANK_TEXTURE_PATH = "images/tank_blue.png";
    private static final String TREE_TEXTURE_PATH = "images/greenTree.png";

    private final TiledMap tiledMap;
    private final TiledMapTileLayer groundLayer;

    public LevelLoader(String mapFilePath) {
        this.tiledMap = new TmxMapLoader().load(mapFilePath);
        this.groundLayer = getSingleLayer(tiledMap);
    }

    public GameWorld loadWorld() {
        Level level =
                new Level(
                        groundLayer.getWidth(),
                        groundLayer.getHeight(),
                        List.of(
                                new Obstacle(new GridPoint2(3, 3)),
                                new Obstacle(new GridPoint2(4, 3)),
                                new Obstacle(new GridPoint2(5, 3))));
        Tank player = new Tank(new GridPoint2(1, 1), Direction.UP);

        return new GameWorld(level, player);
    }

    public GameGraphics loadGraphics(GameWorld world, Batch batch) {
        GameGraphics graphics = new GameGraphics(tiledMap, batch);
        graphics.add(world.getPlayer(), TANK_TEXTURE_PATH);
        for (Obstacle obstacle : world.getLevel().getObstacles()) {
            graphics.add(obstacle, TREE_TEXTURE_PATH);
        }
        return graphics;
    }
}
