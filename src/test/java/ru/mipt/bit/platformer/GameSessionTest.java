package ru.mipt.bit.platformer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ru.mipt.bit.platformer.control.Command;
import ru.mipt.bit.platformer.graphics.Renderer;

class GameSessionTest {

    @Test
    void shouldExecuteCommandsBeforeUpdatingWorld() {
        List<String> events = new ArrayList<>();
        Command command = () -> events.add("command");
        GameSession session =
                new GameSession(
                        () -> List.of(command),
                        deltaTime -> events.add("update"),
                        new RecordingRenderer());

        session.update(0.1f);

        assertEquals(List.of("command", "update"), events);
    }

    @Test
    void shouldPassDeltaTimeToWorld() {
        List<Float> updates = new ArrayList<>();
        GameSession session =
                new GameSession(() -> List.of(), updates::add, new RecordingRenderer());

        session.update(0.25f);

        assertEquals(List.of(0.25f), updates);
    }

    @Test
    void shouldDelegateRenderingAndDisposalToRenderer() {
        RecordingRenderer renderer = new RecordingRenderer();
        GameSession session = new GameSession(() -> List.of(), deltaTime -> {}, renderer);

        session.render();
        session.dispose();

        assertEquals(1, renderer.renderCount);
        assertTrue(renderer.disposed);
    }

    private static class RecordingRenderer implements Renderer {
        private int renderCount;
        private boolean disposed;

        @Override
        public void render() {
            renderCount++;
        }

        @Override
        public void dispose() {
            disposed = true;
        }
    }
}
