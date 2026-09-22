package ru.mipt.bit.platformer.model;

public class GameWorld {
    private final Level level;
    private final Tank player;

    public GameWorld(Level level, Tank player) {
        this.level = level;
        this.player = player;
    }

    public void update(float deltaTime) {
        player.update(deltaTime);
    }

    public Level getLevel() {
        return level;
    }

    public Tank getPlayer() {
        return player;
    }
}
