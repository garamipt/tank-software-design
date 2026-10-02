package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.GameObject;

import static ru.mipt.bit.platformer.graphics.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.graphics.GdxGameUtils.drawTextureRegionUnscaled;

public class GameObjectGraphics {
    private final GameObject object;
    private final TextureRegion image;
    private final Rectangle screenArea;
    private final TileMovement tileMovement;

    public GameObjectGraphics(GameObject object, TextureRegion image, TileMovement tileMovement) {
        this.object = object;
        this.image = image;
        this.screenArea = createBoundingRectangle(image);
        this.tileMovement = tileMovement;
    }

    public void render(Batch batch) {
        tileMovement.moveRectangleBetweenTileCenters(
                screenArea,
                object.getCoordinates(),
                object.getDestination(),
                object.getMovementProgress()
        );
        drawTextureRegionUnscaled(batch, image, screenArea, object.getRotation());
    }
}
