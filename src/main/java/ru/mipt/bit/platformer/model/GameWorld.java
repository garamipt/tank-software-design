package ru.mipt.bit.platformer.model;

import java.util.List;

public class GameWorld implements Updatable {
    private final List<Updatable> objects;

    public GameWorld(List<Updatable> objects) {
        this.objects = List.copyOf(objects);
    }

    @Override
    public void update(float deltaTime) {
        for (Updatable object : objects) {
            object.update(deltaTime);
        }
    }
}
