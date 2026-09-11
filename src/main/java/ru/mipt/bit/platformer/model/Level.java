package ru.mipt.bit.platformer.model;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class Level {
    private final TiledMap tiledMap;
    private final MapRenderer levelRenderer;
    private final TileMovement tileMovement;
    private final List<Tree> obstacles = new ArrayList<>();
    private final TiledMapTileLayer groundLayer;
    
    public Level(String mapFilename, Batch batch) {
        this.tiledMap = new TmxMapLoader().load(mapFilename);
        this.groundLayer = getSingleLayer(tiledMap);
        this.levelRenderer = createSingleLayerMapRenderer(tiledMap, batch);
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    public void addObstacle(Tree obstacle) {
        obstacles.add(obstacle);
    }

    public TiledMapTileLayer getGroundLayer() {
        return groundLayer;
    }

    public TileMovement getTileMovement() {
        return tileMovement;
    }
    private boolean isOutOfBounds(GridPoint2 point) {
        return point.x < 0 || point.y < 0 || point.x >= groundLayer.getWidth() || point.y >= (groundLayer.getHeight());
    }
    public boolean isFree(GridPoint2 point) {
        if (isOutOfBounds(point)){
            return false;
        }
        for (Tree obstacle : obstacles) {
            if (obstacle.occupies(point)){
                return false;
            }
        }
        return true;
    }
    public void render() {
        levelRenderer.render();
    }
    public void drawObstacles(Batch batch) {
        for (Tree obstacle : obstacles) {
            obstacle.draw(batch);
        }
    }
    public void dispose() {
        tiledMap.dispose();
    }
}
