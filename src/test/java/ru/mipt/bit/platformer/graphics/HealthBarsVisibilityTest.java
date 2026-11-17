package ru.mipt.bit.platformer.graphics;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HealthBarsVisibilityTest {

    @Test
    void shouldStartWithGivenVisibility() {
        assertTrue(new HealthBarsVisibility(true).isVisible());
        assertFalse(new HealthBarsVisibility(false).isVisible());
    }

    @Test
    void shouldFlipVisibilityOnToggle() {
        HealthBarsVisibility visibility = new HealthBarsVisibility(true);

        visibility.toggle();
        assertFalse(visibility.isVisible());

        visibility.toggle();
        assertTrue(visibility.isVisible());
    }
}
