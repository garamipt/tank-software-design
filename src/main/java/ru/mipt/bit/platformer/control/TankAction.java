package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.model.Direction;

/** Application value: an action chosen by a {@link TankStrategy}. */
public sealed interface TankAction permits TankAction.Move, TankAction.Shoot {

    record Move(Direction direction) implements TankAction {}

    record Shoot() implements TankAction {}
}
