package ru.mipt.bit.platformer.graphics;

public class HealthBarsVisibility {
    private boolean visible;

    public HealthBarsVisibility(boolean initiallyVisible) {
        this.visible = initiallyVisible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void toggle() {
        visible = !visible;
    }
}
