package ru.mipt.bit.platformer.graphics;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.graphics.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.graphics.GdxGameUtils.getSingleLayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Disposable;
import java.util.ArrayList;
import java.util.List;
import ru.mipt.bit.platformer.model.GameObject;

public class GameGraphics implements Disposable {
    private final TiledMap tiledMap;
    private final Batch batch;
    private final MapRenderer levelRenderer;
    private final TileMovement tileMovement;

    private final List<Texture> textures = new ArrayList<>();
    private final List<GameObjectGraphics> objects = new ArrayList<>();

    public GameGraphics(TiledMap tiledMap, Batch batch) {
        this.tiledMap = tiledMap;
        this.batch = batch;
        this.levelRenderer = createSingleLayerMapRenderer(tiledMap, batch);

        TiledMapTileLayer groundLayer = getSingleLayer(tiledMap);
        this.tileMovement = new TileMovement(groundLayer, Interpolation.smooth);
    }

    public void add(GameObject object, String texturePath) {
        Texture texture = new Texture(texturePath);
        textures.add(texture);
        objects.add(new GameObjectGraphics(object, new TextureRegion(texture), tileMovement));
    }

    public void render() {

        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        levelRenderer.render();

        batch.begin();
        for (GameObjectGraphics object : objects) {
            object.render(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        for (Texture texture : textures) {
            texture.dispose();
        }
        tiledMap.dispose();
    }
}
