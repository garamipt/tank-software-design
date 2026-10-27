package ru.mipt.bit.platformer.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.Input.Keys;
import java.util.List;
import org.junit.jupiter.api.Test;

public class InputControllerTest {
    @Test
    void shouldReturnCommandBoundToPressedKey() {
        Command command = () -> {};
        InputController inputController = new InputController(keycode -> keycode == Keys.UP);
        inputController.bind(Keys.UP, command);

        assertEquals(List.of(command), inputController.getActiveCommands());
    }

    @Test
    void shouldReturnNoCommandsWhenNothingPressed() {
        InputController inputController = new InputController(keycode -> false);
        inputController.bind(Keys.UP, () -> {});

        assertTrue(inputController.getActiveCommands().isEmpty());
    }

    @Test
    void shouldIgnorePressedKeysWithoutBinding() {
        InputController controller = new InputController(keycode -> true);

        assertTrue(controller.getActiveCommands().isEmpty());
    }

    @Test
    void shouldReturnCommandsInBindingOrder() {
        Command first = () -> {};
        Command second = () -> {};
        InputController controller = new InputController(keycode -> true);
        controller.bind(Keys.UP, first);
        controller.bind(Keys.RIGHT, second);

        assertEquals(List.of(first, second), controller.getActiveCommands());
    }
}
