package ru.mipt.bit.platformer.control;

/**
 * Port (driven): decision maker for an AI tank, the place for a recommendation engine adapter.
 * Implemented by {@link RandomTankStrategy}.
 */
@FunctionalInterface
public interface TankStrategy {
    TankAction chooseAction();
}
