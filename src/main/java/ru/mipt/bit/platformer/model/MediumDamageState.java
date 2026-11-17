package ru.mipt.bit.platformer.model;

public class MediumDamageState extends DamageState {

    @Override
    public float speedFactor() {
        return 1f / 2;
    }

    @Override
    public boolean canShoot() {
        return true;
    }
}
