package ru.mipt.bit.platformer.model;

public record TankSettings(
        float secondsPerCell, int health, float reloadSeconds, BulletSettings bullet) {}
