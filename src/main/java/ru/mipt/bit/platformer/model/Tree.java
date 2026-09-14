package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;

import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.moveRectangleAtTileCenter;

public class Tree {
    private final GridPoint2 coordinates;
    private final TextureRegion image;
    private final Rectangle screenArea;

    public Tree(GridPoint2 coordinates, TextureRegion image, TiledMapTileLayer groundLayer) {
        this.coordinates = new GridPoint2(coordinates);
        this.image = image;
        this.screenArea = moveRectangleAtTileCenter(groundLayer, createBoundingRectangle(image), coordinates);
    }

    public void draw(Batch batch) {
        drawTextureRegionUnscaled(batch, image, screenArea, 0f);
    }

    public boolean occupies(GridPoint2 point) {
        return coordinates.equals(point);
    }
}