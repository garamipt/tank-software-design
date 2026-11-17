package ru.mipt.bit.platformer.control;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

class ToggleCommandTest {

    @Test
    void shouldToggleTargetOnEveryExecution() {
        AtomicInteger toggles = new AtomicInteger();
        ToggleCommand command = new ToggleCommand(toggles::incrementAndGet);

        command.execute();
        command.execute();

        assertEquals(2, toggles.get());
    }
}
