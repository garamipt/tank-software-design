package ru.mipt.bit.platformer.control;

import java.util.List;

/**
 * Port (driving, input): where the game loop takes commands from. Adapters: {@link InputController}
 * for the keyboard, {@link AiController} for bots.
 */
@FunctionalInterface
public interface CommandSource {
    List<Command> getActiveCommands();
}
