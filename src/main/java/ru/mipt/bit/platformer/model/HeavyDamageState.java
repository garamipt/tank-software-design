package ru.mipt.bit.platformer.model;

public class HeavyDamageState extends DamageState {

    @Override
    public float speedFactor() {
        return 1f / 3;
    }

    @Override
    public boolean canShoot() {
        return false;
    }
}
