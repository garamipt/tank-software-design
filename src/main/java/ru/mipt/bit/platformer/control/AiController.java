package ru.mipt.bit.platformer.control;

import java.util.List;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.World;

/**
 * Adapter (driving, application): turns {@link TankStrategy} decisions into commands for {@link
 * CommandSource}.
 */
public class AiController implements CommandSource {
    private final Tank tank;
    private final TankStrategy strategy;
    private final World world;

    public AiController(Tank tank, TankStrategy strategy, World world) {
        this.tank = tank;
        this.strategy = strategy;
        this.world = world;
    }

    @Override
    public List<Command> getActiveCommands() {
        return List.of(toCommand(strategy.chooseAction()));
    }

    private Command toCommand(TankAction action) {
        return switch (action) {
            case TankAction.Move(Direction direction) -> new MoveCommand(tank, direction, world);
            case TankAction.Shoot() -> new ShootCommand(tank, world);
        };
    }
}
