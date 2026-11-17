package ru.mipt.bit.platformer.control;

import com.badlogic.gdx.Gdx;

/** Adapter (driven, infrastructure): {@link KeyboardState} over libGDX {@code Gdx.input}. */
public class GdxKeyboardState implements KeyboardState {
    @Override
    public boolean isPressed(int keycode) {
        return Gdx.input.isKeyPressed(keycode);
    }
}
