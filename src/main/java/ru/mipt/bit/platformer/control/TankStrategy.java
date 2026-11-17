package ru.mipt.bit.platformer.control;

@FunctionalInterface
public interface TankStrategy {
    TankAction chooseAction();
}
