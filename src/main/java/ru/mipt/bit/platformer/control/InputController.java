package ru.mipt.bit.platformer.control;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class InputController implements CommandSource {
    private final KeyboardState keyboard;
    private final Map<Integer, Command> bindings = new LinkedHashMap<>();
    private final Map<Integer, Command> pressBindings = new LinkedHashMap<>();
    private final Set<Integer> keysHeldDown = new HashSet<>();

    public InputController(KeyboardState keyboard) {
        this.keyboard = keyboard;
    }

    public void bind(int keycode, Command command) {
        bindings.put(keycode, command);
    }

    public void bindOnPress(int keycode, Command command) {
        pressBindings.put(keycode, command);
    }

    @Override
    public List<Command> getActiveCommands() {
        List<Command> active = new ArrayList<>();
        for (Map.Entry<Integer, Command> binding : bindings.entrySet()) {
            if (keyboard.isPressed(binding.getKey())) {
                active.add(binding.getValue());
            }
        }
        for (Map.Entry<Integer, Command> binding : pressBindings.entrySet()) {
            if (isJustPressed(binding.getKey())) {
                active.add(binding.getValue());
            }
        }
        return active;
    }

    private boolean isJustPressed(int keycode) {
        if (!keyboard.isPressed(keycode)) {
            keysHeldDown.remove(keycode);
            return false;
        }
        return keysHeldDown.add(keycode);
    }
}
