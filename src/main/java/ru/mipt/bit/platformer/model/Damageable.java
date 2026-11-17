package ru.mipt.bit.platformer.model;

/** Domain interface: an object with health, read by the health bar graphics. */
public interface Damageable {
    int getHealth();

    int getMaxHealth();

    default float getHealthFraction() {
        return Math.max(0f, Math.min(1f, (float) getHealth() / getMaxHealth()));
    }
}
