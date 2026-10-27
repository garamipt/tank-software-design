package ru.mipt.bit.platformer.control;

import java.util.List;
import ru.mipt.bit.platformer.model.CollisionChecker;
import ru.mipt.bit.platformer.model.Tank;

public class AiController implements CommandSource {
    private final Tank tank;
    private final MovementStrategy strategy;
    private final CollisionChecker collisionChecker;

    public AiController(Tank tank, MovementStrategy strategy, CollisionChecker collisionChecker) {
        this.tank = tank;
        this.strategy = strategy;
        this.collisionChecker = collisionChecker;
    }

    @Override
    public List<Command> getActiveCommands() {
        return List.of(new MoveCommand(tank, strategy.chooseDirection(), collisionChecker));
    }
}
