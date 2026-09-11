package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import ru.mipt.bit.platformer.control.InputController;
import ru.mipt.bit.platformer.model.Level;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.Tree;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;

public class GameDesktopLauncher implements ApplicationListener {
    private Batch batch;
    private Level level;
    private Tank playerTank;
    private Texture tankTexture;
    private Texture treeTexture;
    private InputController inputController;

    @Override
    public void create() {
        batch = new SpriteBatch();
        inputController = new InputController();

        level = new Level("level.tmx", batch);

        tankTexture = new Texture("images/tank_blue.png");
        playerTank = new Tank(new GridPoint2(1, 1), new TextureRegion(tankTexture), level.getTileMovement());

        treeTexture = new Texture("images/greenTree.png");
        level.addObstacle(new Tree(new GridPoint2(1, 3), new TextureRegion(treeTexture), level.getGroundLayer()));
    }

    @Override
    public void render() {

        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        inputController.getPressedDirection().ifPresent(direction -> playerTank.move(direction, level));

        playerTank.update(Gdx.graphics.getDeltaTime());

        level.render();
        batch.begin();
        playerTank.draw(batch);
        level.drawObstacles(batch);
        batch.end();
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
        // dispose of all the native resources (classes which implement
        // com.badlogic.gdx.utils.Disposable)
        treeTexture.dispose();
        tankTexture.dispose();
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
