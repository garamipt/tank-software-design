package ru.mipt.bit.platformer.control;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InputController {
    private final KeyboardState keyboard;
    private final Map<Integer, Command> bindings = new LinkedHashMap<>();

    public InputController(KeyboardState keyboard) {
        this.keyboard = keyboard;
    }

    public void bind(int keycode, Command command) {
        bindings.put(keycode, command);
    }

    public List<Command> getActiveCommands() {
        List<Command> active = new ArrayList<>();
        for (Map.Entry<Integer, Command> binding : bindings.entrySet()) {
            if (keyboard.isPressed(binding.getKey())) {
                active.add(binding.getValue());
            }
        }
        return active;
    }
}
