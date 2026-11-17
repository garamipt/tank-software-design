package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.model.Direction;

/** Port (driven): chooses where an AI tank goes. Implemented by {@link RandomMovementStrategy}. */
@FunctionalInterface
public interface MovementStrategy {
    Direction chooseDirection();
}
