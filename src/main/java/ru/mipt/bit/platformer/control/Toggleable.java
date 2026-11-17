package ru.mipt.bit.platformer.control;

/** Application interface: target of {@link ToggleCommand}. */
@FunctionalInterface
public interface Toggleable {
    void toggle();
}
