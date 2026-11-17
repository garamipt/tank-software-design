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
import ru.mipt.bit.platformer.control.ShootCommand;
import ru.mipt.bit.platformer.control.TankStrategy;
import ru.mipt.bit.platformer.control.ToggleCommand;
import ru.mipt.bit.platformer.graphics.Appearance;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.graphics.HealthBarsVisibility;
import ru.mipt.bit.platformer.level.LevelLayoutSource;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.BulletSettings;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.GameWorld;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.LevelLayout;
import ru.mipt.bit.platformer.model.Obstacle;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.TankSettings;

/**
 * Composition root (infrastructure): plugs adapters into ports and builds a {@link GameSession}.
 */
public class LevelLoader {
    private static final Appearance PLAYER_APPEARANCE =
            new Appearance("images/tank_blue.png", Color.WHITE);
    private static final Appearance BOT_APPEARANCE =
            new Appearance("images/tank_blue.png", new Color(1f, 0.55f, 0.55f, 1f));
    private static final Appearance TREE_APPEARANCE =
            new Appearance("images/greenTree.png", Color.WHITE);
    private static final Appearance BULLET_APPEARANCE =
            new Appearance("images/bullet.png", Color.WHITE);

    private static final BulletSettings BULLET_SETTINGS = new BulletSettings(30, 0.08f);
    private static final TankSettings PLAYER_SETTINGS =
            new TankSettings(0.4f, 100, 0.5f, BULLET_SETTINGS);
    private static final TankSettings BOT_SETTINGS =
            new TankSettings(0.6f, 100, 1.5f, BULLET_SETTINGS);
    private static final Direction PLAYER_INITIAL_DIRECTION = Direction.UP;
    private static final Direction BOT_INITIAL_DIRECTION = Direction.DOWN;

    private static final Color BACKGROUND_COLOR = new Color(0f, 0f, 0.2f, 1f);
    private static final Interpolation MOVEMENT_INTERPOLATION = Interpolation.smooth;
    private static final int SHOOT_KEY = Keys.SPACE;
    private static final int TOGGLE_HEALTH_BARS_KEY = Keys.L;
    private static final boolean HEALTH_BARS_INITIALLY_VISIBLE = true;
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
    private final Supplier<TankStrategy> botStrategyFactory;

    public LevelLoader(
            String mapPath,
            LevelLayoutSource layoutSource,
            Supplier<TankStrategy> botStrategyFactory) {
        this.mapPath = mapPath;
        this.layoutSource = layoutSource;
        this.botStrategyFactory = botStrategyFactory;
    }

    public GameSession load() {
        TiledMap tiledMap = new TmxMapLoader().load(mapPath);
        TiledMapTileLayer groundLayer = getSingleLayer(tiledMap);
        LevelLayout layout =
                layoutSource.createLayout(groundLayer.getWidth(), groundLayer.getHeight());

        Tank player = new Tank(layout.playerPosition(), PLAYER_INITIAL_DIRECTION, PLAYER_SETTINGS);
        List<Tank> bots =
                layout.botPositions().stream()
                        .map(position -> new Tank(position, BOT_INITIAL_DIRECTION, BOT_SETTINGS))
                        .toList();
        List<Obstacle> obstacles = layout.obstaclePositions().stream().map(Obstacle::new).toList();

        GameWorld world = new GameWorld(new Level(groundLayer.getWidth(), groundLayer.getHeight()));
        HealthBarsVisibility healthBars = new HealthBarsVisibility(HEALTH_BARS_INITIALLY_VISIBLE);
        GameGraphics graphics =
                new GameGraphics(
                        tiledMap,
                        BACKGROUND_COLOR,
                        MOVEMENT_INTERPOLATION,
                        object -> appearanceOf(object, player),
                        healthBars);
        world.addListener(graphics);

        world.add(player);
        bots.forEach(world::add);
        obstacles.forEach(world::add);

        return new GameSession(
                createCommandSource(player, bots, world, healthBars), world, graphics);
    }

    private CommandSource createCommandSource(
            Tank player, List<Tank> bots, GameWorld world, HealthBarsVisibility healthBars) {
        List<CommandSource> sources = new ArrayList<>();
        sources.add(createInputController(player, world, healthBars));
        for (Tank bot : bots) {
            sources.add(new AiController(bot, botStrategyFactory.get(), world));
        }
        return new CompositeCommandSource(sources);
    }

    private InputController createInputController(
            Tank player, GameWorld world, HealthBarsVisibility healthBars) {
        InputController inputController = new InputController(new GdxKeyboardState());
        MOVEMENT_KEYS.forEach(
                (key, direction) ->
                        inputController.bind(key, new MoveCommand(player, direction, world)));
        inputController.bind(SHOOT_KEY, new ShootCommand(player, world));
        inputController.bindOnPress(TOGGLE_HEALTH_BARS_KEY, new ToggleCommand(healthBars::toggle));
        return inputController;
    }

    private static Appearance appearanceOf(GameObject object, Tank player) {
        if (object == player) {
            return PLAYER_APPEARANCE;
        }
        if (object instanceof Tank) {
            return BOT_APPEARANCE;
        }
        if (object instanceof Bullet) {
            return BULLET_APPEARANCE;
        }
        if (object instanceof Obstacle) {
            return TREE_APPEARANCE;
        }
        throw new IllegalArgumentException("No appearance for " + object);
    }
}
