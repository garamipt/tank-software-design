package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.math.GridPoint2;
import java.util.List;
import java.util.function.Supplier;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.LevelLayout;

public class GameDesktopLauncher implements ApplicationListener {
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
        LevelLayout layout =
                new LevelLayout(
                        new GridPoint2(1, 1),
                        Direction.UP,
                        List.of(new GridPoint2(3, 3), new GridPoint2(4, 3), new GridPoint2(5, 3)));
        LevelLoader levelLoader = new LevelLoader("level.tmx", layout);

        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(levelLoader::load), config);
    }
}
