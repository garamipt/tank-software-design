package ru.mipt.bit.platformer.control;

/** Application interface (Command pattern): one action on the domain executed by the game loop. */
@FunctionalInterface
public interface Command {
    void execute();
}
