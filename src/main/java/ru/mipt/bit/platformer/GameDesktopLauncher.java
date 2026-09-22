package ru.mipt.bit.platformer;

import java.util.LinkedHashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;

import ru.mipt.bit.platformer.control.InputController;
import ru.mipt.bit.platformer.model.GameWorld;
import ru.mipt.bit.platformer.graphics.GameGraphics;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.CollisionChecker;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.control.MoveCommand;
import ru.mipt.bit.platformer.control.GdxKeyboardState;
import ru.mipt.bit.platformer.control.Command;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;
    private GameWorld world;
    private GameGraphics graphics;
    private InputController inputController;

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



    @Override
    public void create() {
        batch = new SpriteBatch();

        LevelLoader levelLoader = new LevelLoader("level.tmx");
        world = levelLoader.loadWorld();
        graphics = levelLoader.loadGraphics(world, batch);

        inputController = new InputController(new GdxKeyboardState());

        Tank player = world.getPlayer();

        CollisionChecker collisionChecker = world.getLevel();

        MOVEMENT_KEYS.forEach((key, direction) -> {
            inputController.bind(key, new MoveCommand(player, direction, collisionChecker));
        });

        
    }

    @Override
    public void render() {
        for (Command command : inputController.getActiveCommands()) {
            command.execute();
        }

        world.update(Gdx.graphics.getDeltaTime());

        graphics.render();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        graphics.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
