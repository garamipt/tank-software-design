package ru.mipt.bit.platformer.model;

public class LightDamageState extends DamageState {

    @Override
    public float speedFactor() {
        return 1f;
    }

    @Override
    public boolean canShoot() {
        return true;
    }
}
