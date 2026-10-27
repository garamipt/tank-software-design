package ru.mipt.bit.platformer;

import static ru.mipt.bit.platformer.graphics.GdxGameUtils.getSingleLayer;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import ru.mipt.bit.platformer.control.GdxKeyboardState;
import ru.mipt.bit.platformer.control.InputController;
import ru.mipt.bit.platformer.control.MoveCommand;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.model.CollisionChecker;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameWorld;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.LevelLayout;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;

public class LevelLoader {
    private static final String TANK_TEXTURE_PATH = "images/tank_blue.png";
    private static final String TREE_TEXTURE_PATH = "images/greenTree.png";
    private static final float PLAYER_SECONDS_PER_CELL = 0.4f;
    private static final Color BACKGROUND_COLOR = new Color(0f, 0f, 0.2f, 1f);
    private static final Interpolation MOVEMENT_INTERPOLATION = Interpolation.smooth;
    private static final Map<Integer, Direction> MOVEMENT_KEYS = new LinkedHashMap<>();

    static {
        MOVEMENT_KEYS.put(Keys.UP, Direction.UP);
        MOVEMENT_KEYS.put(Keys.W, Direction.UP);
        MOVEMENT_KEYS.put(Keys.LEFT, Direction.LEFT);
        MOVEMENT_KEYS.put(Keys.A, Direction.LEFT);
        MOVEMENT_KEYS.put(Keys.DOWN, Direction.DOWN);
        MOVEMENT_KEYS.put(Keys.S, Direction.DOWN);
        MOVEMENT_KEYS.put(Keys.RIGHT, Direction.RIGHT);
        MOVEMENT_KEYS.put(Keys.D, Direction.RIGHT);
    }

    private final String mapPath;
    private final LevelLayout layout;

    public LevelLoader(String mapPath, LevelLayout layout) {
        this.mapPath = mapPath;
        this.layout = layout;
    }

    public GameSession load() {
        TiledMap tiledMap = new TmxMapLoader().load(mapPath);
        TiledMapTileLayer groundLayer = getSingleLayer(tiledMap);

        List<Obstacle> obstacles = layout.obstaclePositions().stream().map(Obstacle::new).toList();
        Level level = new Level(groundLayer.getWidth(), groundLayer.getHeight(), obstacles);
        Tank player =
                new Tank(
                        layout.playerPosition(), layout.playerDirection(), PLAYER_SECONDS_PER_CELL);
        GameWorld world = new GameWorld(List.of(player));

        return new GameSession(
                createInputController(player, level),
                world,
                createGraphics(tiledMap, player, obstacles));
    }

    private InputController createInputController(Tank player, CollisionChecker collisionChecker) {
        InputController inputController = new InputController(new GdxKeyboardState());
        MOVEMENT_KEYS.forEach(
                (key, direction) ->
                        inputController.bind(
                                key, new MoveCommand(player, direction, collisionChecker)));
        return inputController;
    }

    private GameGraphics createGraphics(TiledMap tiledMap, Tank player, List<Obstacle> obstacles) {
        GameGraphics graphics =
                new GameGraphics(tiledMap, BACKGROUND_COLOR, MOVEMENT_INTERPOLATION);
        graphics.add(player, TANK_TEXTURE_PATH);
        for (Obstacle obstacle : obstacles) {
            graphics.add(obstacle, TREE_TEXTURE_PATH);
        }
        return graphics;
    }
}
