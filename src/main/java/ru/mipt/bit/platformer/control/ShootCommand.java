package ru.mipt.bit.platformer.control;

import ru.mipt.bit.platformer.model.Tank;
import ru.mipt.bit.platformer.model.World;

public class ShootCommand implements Command {
    private final Tank tank;
    private final World world;

    public ShootCommand(Tank tank, World world) {
        this.tank = tank;
        this.world = world;
    }

    @Override
    public void execute() {
        tank.shoot(world);
    }
}
