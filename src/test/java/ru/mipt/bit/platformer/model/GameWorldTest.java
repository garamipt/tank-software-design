package ru.mipt.bit.platformer.model;

import java.util.List;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameWorldTest {

    @Test 
    public void shouldAdvancePlayerMovement() {
        Tank player = new Tank(new GridPoint2(1, 1), Direction.RIGHT);
        GameWorld world = new GameWorld(new Level(10, 8,List.of()), player);

        player.move(Direction.RIGHT, point -> true);
        world.update(1f);

        assertEquals(new GridPoint2(2, 1), player.getCoordinates());
    }
}
