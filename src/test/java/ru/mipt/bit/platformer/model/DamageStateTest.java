package ru.mipt.bit.platformer.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DamageStateTest {

    @ParameterizedTest
    @ValueSource(floats = {1f, 0.71f})
    void shouldBeLightAboveSeventyPercent(float healthFraction) {
        assertInstanceOf(LightDamageState.class, DamageState.forHealthFraction(healthFraction));
    }

    @ParameterizedTest
    @ValueSource(floats = {0.7f, 0.5f, 0.15f})
    void shouldBeMediumFromFifteenToSeventyPercent(float healthFraction) {
        assertInstanceOf(MediumDamageState.class, DamageState.forHealthFraction(healthFraction));
    }

    @ParameterizedTest
    @ValueSource(floats = {0.14f, 0.01f, 0f})
    void shouldBeHeavyBelowFifteenPercent(float healthFraction) {
        assertInstanceOf(HeavyDamageState.class, DamageState.forHealthFraction(healthFraction));
    }

    @Test
    void shouldBehaveAsUndamagedInLightState() {
        TankState state = new LightDamageState();

        assertEquals(1f, state.speedFactor(), 1e-6f);
        assertEquals(1f, state.fireRateFactor(), 1e-6f);
        assertTrue(state.canShoot());
    }

    @Test
    void shouldMoveTwiceSlowerInMediumState() {
        TankState state = new MediumDamageState();

        assertEquals(0.5f, state.speedFactor(), 1e-6f);
        assertTrue(state.canShoot());
    }

    @Test
    void shouldMoveThreeTimesSlowerAndNotShootInHeavyState() {
        TankState state = new HeavyDamageState();

        assertEquals(1f / 3, state.speedFactor(), 1e-6f);
        assertFalse(state.canShoot());
    }

    @Test
    void shouldSwitchStateWhenHealthChanges() {
        TankState state = new LightDamageState();

        state = state.onHealthChanged(0.5f);
        assertInstanceOf(MediumDamageState.class, state);

        state = state.onHealthChanged(0.1f);
        assertInstanceOf(HeavyDamageState.class, state);
    }
}
