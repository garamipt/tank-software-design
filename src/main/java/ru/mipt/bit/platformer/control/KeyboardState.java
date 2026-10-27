package ru.mipt.bit.platformer.control;

@FunctionalInterface
public interface KeyboardState {
    boolean isPressed(int keycode);
}
