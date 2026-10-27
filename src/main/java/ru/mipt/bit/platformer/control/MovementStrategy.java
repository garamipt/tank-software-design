package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.model.Direction;

@FunctionalInterface
public interface MovementStrategy {
    Direction chooseDirection();
}
