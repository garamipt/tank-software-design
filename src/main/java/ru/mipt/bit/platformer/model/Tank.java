package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.util.TileMovement;
import com.badlogic.gdx.graphics.g2d.Batch;

import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class Tank {
    private static final float MOVEMENT_SPEED = 0.4f;
    private static final float MOVEMENT_STARTED = 0f;
    private static final float MOVEMENT_COMPLETED = 1f;

    private final GridPoint2 coordinates;
    private final GridPoint2 targetCoordinates;
    private final TextureRegion image;
    private final Rectangle screenArea;
    private final TileMovement tileMovement;

    private Direction direction = Direction.UP;
    private float movementProgress = MOVEMENT_COMPLETED;

    public Tank(GridPoint2 initialCoordinates, TextureRegion image, TileMovement tileMovement) {
        this.coordinates = new GridPoint2(initialCoordinates);
        this.targetCoordinates = new GridPoint2(initialCoordinates);
        this.image = image;
        this.screenArea = createBoundingRectangle(image);
        this.tileMovement = tileMovement;
    }

    private boolean isMoving() {
        return movementProgress < MOVEMENT_COMPLETED;
    }

    public void move(Direction direction, Level level) {
        if (isMoving()) {
            return;
        }
        GridPoint2 next = direction.stepFrom(coordinates);
        if (level.isFree(next)) {
            targetCoordinates.set(next);
            movementProgress = MOVEMENT_STARTED;
        }
        this.direction = direction;
    }

    public void update(float deltaTime) {
        tileMovement.moveRectangleBetweenTileCenters(screenArea, coordinates, targetCoordinates, movementProgress);
        movementProgress = continueProgress(movementProgress, deltaTime, MOVEMENT_SPEED);
        if (!isMoving()) {
            coordinates.set(targetCoordinates);
        }
    }

    public void draw(Batch batch) {
        drawTextureRegionUnscaled(batch, image, screenArea, direction.getRotation());
    }

}
