package ru.mipt.bit.platformer;

import com.badlogic.gdx.utils.Disposable;
import java.util.List;
import ru.mipt.bit.platformer.control.Command;
import ru.mipt.bit.platformer.control.CommandSource;
import ru.mipt.bit.platformer.graphics.Renderer;
import ru.mipt.bit.platformer.model.Updatable;

/**
 * Application service: one game loop step over ports {@link CommandSource}, {@link Updatable} and
 * {@link Renderer}.
 */
public class GameSession implements Disposable {
    private final CommandSource commandSource;
    private final Updatable world;
    private final Renderer renderer;

    public GameSession(CommandSource commandSource, Updatable world, Renderer renderer) {
        this.commandSource = commandSource;
        this.world = world;
        this.renderer = renderer;
    }

    public void update(float deltaTime) {
        List<Command> commands = commandSource.getActiveCommands();
        for (Command command : commands) {
            command.execute();
        }
        world.update(deltaTime);
    }

    public void render() {
        renderer.render();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
