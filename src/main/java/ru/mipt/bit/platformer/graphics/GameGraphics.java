package ru.mipt.bit.platformer.graphics;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.graphics.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.graphics.GdxGameUtils.getSingleLayer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Interpolation;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.LevelListener;
import ru.mipt.bit.platformer.model.LevelObject;

public class GameGraphics implements Renderer, LevelListener {
    private final TiledMap tiledMap;
    private final Batch batch;
    private final MapRenderer levelRenderer;
    private final TileMovement tileMovement;
    private final Color backgroundColor;
    private final AppearanceResolver appearances;

    private final Map<String, Texture> textures = new HashMap<>();
    private final Map<GameObject, GameObjectGraphics> objects = new LinkedHashMap<>();

    public GameGraphics(
            TiledMap tiledMap,
            Color backgroundColor,
            Interpolation movementInterpolation,
            AppearanceResolver appearances) {
        this.tiledMap = tiledMap;
        this.batch = new SpriteBatch();
        this.levelRenderer = createSingleLayerMapRenderer(tiledMap, batch);
        this.backgroundColor = new Color(backgroundColor);
        this.appearances = appearances;

        TiledMapTileLayer groundLayer = getSingleLayer(tiledMap);
        this.tileMovement = new TileMovement(groundLayer, movementInterpolation);
    }

    @Override
    public void onObjectAdded(LevelObject object) {
        Appearance appearance = appearances.appearanceOf(object);
        Texture texture = textures.computeIfAbsent(appearance.texturePath(), Texture::new);
        objects.put(
                object,
                new GameObjectGraphics(
                        object, new TextureRegion(texture), appearance.tint(), tileMovement));
    }

    @Override
    public void onObjectRemoved(LevelObject object) {
        objects.remove(object);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(
                backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        levelRenderer.render();

        batch.begin();
        for (GameObjectGraphics object : objects.values()) {
            object.render(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        for (Texture texture : textures.values()) {
            texture.dispose();
        }
        tiledMap.dispose();
        batch.dispose();
    }
}
