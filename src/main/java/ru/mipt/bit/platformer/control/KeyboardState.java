package ru.mipt.bit.platformer.control;

/** Port (driven): state of the keyboard. Adapter: {@link GdxKeyboardState}. */
@FunctionalInterface
public interface KeyboardState {
    boolean isPressed(int keycode);
}
