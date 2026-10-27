package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import java.util.Random;
import java.util.function.Supplier;
import ru.mipt.bit.platformer.level.FileLevelLayoutSource;
import ru.mipt.bit.platformer.level.LevelLayoutSource;
import ru.mipt.bit.platformer.level.RandomLevelLayoutSource;

public class GameDesktopLauncher implements ApplicationListener {
    private static final String MAP_PATH = "level.tmx";
    private static final String DEFAULT_LEVEL_PATH = "level.txt";
    private static final String RANDOM_LEVEL_ARGUMENT = "random";
    private static final int RANDOM_OBSTACLE_COUNT = 10;

    private final Supplier<GameSession> sessionFactory;
    private GameSession session;

    public GameDesktopLauncher(Supplier<GameSession> sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void create() {
        session = sessionFactory.get();
    }

    @Override
    public void render() {
        session.update(Gdx.graphics.getDeltaTime());
        session.render();
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
        session.dispose();
    }

    public static void main(String[] args) {
        LevelLoader levelLoader = new LevelLoader(MAP_PATH, createLayoutSource(args));

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(levelLoader::load), config);
    }

    private static LevelLayoutSource createLayoutSource(String[] args) {
        if (args.length == 0) {
            return new FileLevelLayoutSource(DEFAULT_LEVEL_PATH);
        }
        if (RANDOM_LEVEL_ARGUMENT.equals(args[0])) {
            return new RandomLevelLayoutSource(RANDOM_OBSTACLE_COUNT, new Random());
        }
        return new FileLevelLayoutSource(args[0]);
    }
}
