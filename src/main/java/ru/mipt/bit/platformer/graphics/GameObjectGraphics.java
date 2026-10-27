package ru.mipt.bit.platformer.graphics;

import static ru.mipt.bit.platformer.graphics.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.graphics.GdxGameUtils.drawTextureRegionUnscaled;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.GameObject;

public class GameObjectGraphics {
    private final GameObject object;
    private final TextureRegion image;
    private final Color tint;
    private final Rectangle screenArea;
    private final TileMovement tileMovement;

    public GameObjectGraphics(
            GameObject object, TextureRegion image, Color tint, TileMovement tileMovement) {
        this.object = object;
        this.image = image;
        this.tint = new Color(tint);
        this.screenArea = createBoundingRectangle(image);
        this.tileMovement = tileMovement;
    }

    public void render(Batch batch) {
        tileMovement.moveRectangleBetweenTileCenters(
                screenArea,
                object.getCoordinates(),
                object.getDestination(),
                object.getMovementProgress());
        batch.setColor(tint);
        drawTextureRegionUnscaled(batch, image, screenArea, object.getRotation());
        batch.setColor(Color.WHITE);
    }
}
