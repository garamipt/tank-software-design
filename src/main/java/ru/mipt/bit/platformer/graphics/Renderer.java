package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.utils.Disposable;

/** Port (driven, output): shows the game on a screen. Adapter: {@link GameGraphics}. */
public interface Renderer extends Disposable {
    void render();
}
