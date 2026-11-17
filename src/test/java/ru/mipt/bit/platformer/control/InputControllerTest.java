package ru.mipt.bit.platformer.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.badlogic.gdx.Input.Keys;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    @Test
    void shouldFirePressBindingOnlyOncePerPress() {
        Set<Integer> pressedKeys = new HashSet<>();
        Command toggle = () -> {};
        InputController controller = new InputController(pressedKeys::contains);
        controller.bindOnPress(Keys.L, toggle);

        pressedKeys.add(Keys.L);
        List<Command> onPress = controller.getActiveCommands();
        List<Command> whileHeld = controller.getActiveCommands();
        pressedKeys.remove(Keys.L);
        List<Command> afterRelease = controller.getActiveCommands();
        pressedKeys.add(Keys.L);
        List<Command> onNextPress = controller.getActiveCommands();

        assertEquals(List.of(toggle), onPress);
        assertTrue(whileHeld.isEmpty());
        assertTrue(afterRelease.isEmpty());
        assertEquals(List.of(toggle), onNextPress);
    }

    @Test
    void shouldKeepHeldAndPressBindingsIndependent() {
        Set<Integer> pressedKeys = new HashSet<>(Set.of(Keys.SPACE, Keys.L));
        Command shoot = () -> {};
        Command toggle = () -> {};
        InputController controller = new InputController(pressedKeys::contains);
        controller.bind(Keys.SPACE, shoot);
        controller.bindOnPress(Keys.L, toggle);

        assertEquals(List.of(shoot, toggle), controller.getActiveCommands());
        assertEquals(List.of(shoot), controller.getActiveCommands());
    }
}
