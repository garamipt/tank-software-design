package ru.mipt.bit.platformer;

import static ru.mipt.bit.platformer.graphics.GdxGameUtils.getSingleLayer;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Interpolation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import ru.mipt.bit.platformer.control.AiController;
import ru.mipt.bit.platformer.control.CommandSource;
import ru.mipt.bit.platformer.control.CompositeCommandSource;
import ru.mipt.bit.platformer.control.GdxKeyboardState;
import ru.mipt.bit.platformer.control.InputController;
import ru.mipt.bit.platformer.control.MoveCommand;
import ru.mipt.bit.platformer.control.MovementStrategy;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.level.LevelLayoutSource;
import ru.mipt.bit.platformer.model.CollisionChecker;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameWorld;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.LevelLayout;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Occupant;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Updatable;

public class LevelLoader {
    private static final String TANK_TEXTURE_PATH = "images/tank_blue.png";
    private static final String TREE_TEXTURE_PATH = "images/greenTree.png";
    private static final float PLAYER_SECONDS_PER_CELL = 0.4f;
    private static final Direction PLAYER_INITIAL_DIRECTION = Direction.UP;
    private static final float BOT_SECONDS_PER_CELL = 0.6f;
    private static final Direction BOT_INITIAL_DIRECTION = Direction.DOWN;
    private static final Color BOT_TINT = new Color(1f, 0.55f, 0.55f, 1f);
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
    private final LevelLayoutSource layoutSource;
    private final Supplier<MovementStrategy> botStrategyFactory;

    public LevelLoader(
            String mapPath,
            LevelLayoutSource layoutSource,
            Supplier<MovementStrategy> botStrategyFactory) {
        this.mapPath = mapPath;
        this.layoutSource = layoutSource;
        this.botStrategyFactory = botStrategyFactory;
    }

    public GameSession load() {
        TiledMap tiledMap = new TmxMapLoader().load(mapPath);
        TiledMapTileLayer groundLayer = getSingleLayer(tiledMap);
        LevelLayout layout =
                layoutSource.createLayout(groundLayer.getWidth(), groundLayer.getHeight());

        List<Obstacle> obstacles = layout.obstaclePositions().stream().map(Obstacle::new).toList();
        Tank player =
                new Tank(
                        layout.playerPosition(), PLAYER_INITIAL_DIRECTION, PLAYER_SECONDS_PER_CELL);
        List<Tank> bots =
                layout.botPositions().stream()
                        .map(
                                position ->
                                        new Tank(
                                                position,
                                                BOT_INITIAL_DIRECTION,
                                                BOT_SECONDS_PER_CELL))
                        .toList();

        List<Occupant> occupants = new ArrayList<>(obstacles);
        occupants.add(player);
        occupants.addAll(bots);
        Level level = new Level(groundLayer.getWidth(), groundLayer.getHeight(), occupants);

        List<Updatable> tanks = new ArrayList<>();
        tanks.add(player);
        tanks.addAll(bots);

        return new GameSession(
                createCommandSource(player, bots, level),
                new GameWorld(tanks),
                createGraphics(tiledMap, player, bots, obstacles));
    }

    private CommandSource createCommandSource(
            Tank player, List<Tank> bots, CollisionChecker collisionChecker) {
        List<CommandSource> sources = new ArrayList<>();
        sources.add(createInputController(player, collisionChecker));
        for (Tank bot : bots) {
            sources.add(new AiController(bot, botStrategyFactory.get(), collisionChecker));
        }
        return new CompositeCommandSource(sources);
    }

    private InputController createInputController(Tank player, CollisionChecker collisionChecker) {
        InputController inputController = new InputController(new GdxKeyboardState());
        MOVEMENT_KEYS.forEach(
                (key, direction) ->
                        inputController.bind(
                                key, new MoveCommand(player, direction, collisionChecker)));
        return inputController;
    }

    private GameGraphics createGraphics(
            TiledMap tiledMap, Tank player, List<Tank> bots, List<Obstacle> obstacles) {
        GameGraphics graphics =
                new GameGraphics(tiledMap, BACKGROUND_COLOR, MOVEMENT_INTERPOLATION);
        graphics.add(player, TANK_TEXTURE_PATH);
        for (Tank bot : bots) {
            graphics.add(bot, TANK_TEXTURE_PATH, BOT_TINT);
        }
        for (Obstacle obstacle : obstacles) {
            graphics.add(obstacle, TREE_TEXTURE_PATH);
        }
        return graphics;
    }
}
