package ru.mipt.bit.platformer.model;

public abstract class DamageState implements TankState {
    private static final float LIGHT_DAMAGE_MIN_HEALTH = 0.7f;
    private static final float HEAVY_DAMAGE_MAX_HEALTH = 0.15f;

    public static TankState forHealthFraction(float healthFraction) {
        if (healthFraction > LIGHT_DAMAGE_MIN_HEALTH) {
            return new LightDamageState();
        }
        if (healthFraction >= HEAVY_DAMAGE_MAX_HEALTH) {
            return new MediumDamageState();
        }
        return new HeavyDamageState();
    }

    @Override
    public float fireRateFactor() {
        return 1f;
    }

    @Override
    public TankState onHealthChanged(float healthFraction) {
        return forHealthFraction(healthFraction);
    }
}
