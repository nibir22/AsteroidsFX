package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CollisionDetectorTest {

    private CollisionDetector collisionDetector;
    private GameData gameData;
    private World world;

    @BeforeEach
    public void setUp() {
        collisionDetector = new CollisionDetector();
        gameData = new GameData();
        world = new World();
    }

    @Test
    public void collidesTest() {
        Entity entity1 = new Entity();
        entity1.setX(0);
        entity1.setY(0);
        entity1.setRadius(5);

        Entity entity2 = new Entity();
        entity2.setX(3);
        entity2.setY(4);
        entity2.setRadius(5);

        assertTrue(collisionDetector.collides(entity1, entity2));
    }

}