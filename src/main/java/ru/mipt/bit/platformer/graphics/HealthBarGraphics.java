package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.Damageable;

public class HealthBarGraphics implements ObjectGraphics {
    private static final float BAR_HEIGHT = 8f;
    private static final Color LOST_HEALTH_COLOR = new Color(0.45f, 0f, 0f, 1f);
    private static final Color HEALTH_COLOR = new Color(0.2f, 0.85f, 0.2f, 1f);

    private final ObjectGraphics decorated;
    private final Damageable target;
    private final HealthBarsVisibility visibility;
    private final TextureRegion pixel;

    public HealthBarGraphics(
            ObjectGraphics decorated,
            Damageable target,
            HealthBarsVisibility visibility,
            TextureRegion pixel) {
        this.decorated = decorated;
        this.target = target;
        this.visibility = visibility;
        this.pixel = pixel;
    }

    @Override
    public void render(Batch batch) {
        decorated.render(batch);
        if (visibility.isVisible()) {
            renderHealthBar(batch);
        }
    }

    @Override
    public Rectangle getScreenArea() {
        return decorated.getScreenArea();
    }

    private void renderHealthBar(Batch batch) {
        Rectangle area = decorated.getScreenArea();
        float y = area.y + area.height - BAR_HEIGHT;

        batch.setColor(LOST_HEALTH_COLOR);
        batch.draw(pixel, area.x, y, area.width, BAR_HEIGHT);
        batch.setColor(HEALTH_COLOR);
        batch.draw(pixel, area.x, y, area.width * target.getHealthFraction(), BAR_HEIGHT);
        batch.setColor(Color.WHITE);
    }
}
