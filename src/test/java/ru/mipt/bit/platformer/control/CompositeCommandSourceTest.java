package ru.mipt.bit.platformer.control;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class CompositeCommandSourceTest {

    @Test
    void shouldCollectCommandsFromAllSourcesInOrder() {
        Command first = () -> {};
        Command second = () -> {};
        Command third = () -> {};
        CompositeCommandSource source =
                new CompositeCommandSource(
                        List.of(() -> List.of(first, second), () -> List.of(third)));

        assertEquals(List.of(first, second, third), source.getActiveCommands());
    }

    @Test
    void shouldReturnNoCommandsWithoutSources() {
        assertTrue(new CompositeCommandSource(List.of()).getActiveCommands().isEmpty());
    }
}
