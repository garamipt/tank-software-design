package ru.mipt.bit.platformer.control;
import com.badlogic.gdx.Gdx;

public class GdxKeyboardState implements KeyboardState {
    @Override
    public boolean isPressed(int keycode) {
        return Gdx.input.isKeyPressed(keycode);
    }

}
