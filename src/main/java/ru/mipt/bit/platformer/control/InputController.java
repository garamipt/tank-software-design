package ru.mipt.bit.platformer.control;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.model.Direction;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InputController {
    private static final Map<Direction, List<Integer>> KEYS = new EnumMap<>(Direction.class);

    static {
        KEYS.put(Direction.UP, List.of(Keys.UP, Keys.W));
        KEYS.put(Direction.LEFT, List.of(Keys.LEFT, Keys.A));
        KEYS.put(Direction.DOWN, List.of(Keys.DOWN, Keys.S));
        KEYS.put(Direction.RIGHT, List.of(Keys.RIGHT, Keys.D));
    }

    public Optional<Direction> getPressedDirection() {
        for (Map.Entry<Direction, List<Integer>> entry : KEYS.entrySet()) {
            for (int key : entry.getValue()) {
                if (Gdx.input.isKeyPressed(key)) {
                    return Optional.of(entry.getKey());
                }
            }
        }
        return Optional.empty();
    }
}
