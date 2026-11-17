package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

/** Infrastructure interface (Decorator component): draws one object. */
public interface ObjectGraphics {
    void render(Batch batch);

    Rectangle getScreenArea();
}
