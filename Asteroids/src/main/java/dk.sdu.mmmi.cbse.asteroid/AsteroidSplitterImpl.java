package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import java.util.Random;

public class AsteroidSplitterImpl implements IAsteroidSplitter {


    private Entity createSmallerAsteroid (Entity entity){
        Entity asteroid = new Asteroid();
        Random random = new Random();
        double size = random.nextDouble(5,(entity.getRadius()/2));
        asteroid.setPolygonCoordinates(size,-size,-size,-size,-size,size,size,size);
        asteroid.setX(entity.getX());
        asteroid.setY(entity.getY());
        asteroid.setRadius((float) size);
        asteroid.setRotation(random.nextInt(90));
        asteroid.setType("Asteroid");
        return asteroid;
    }
    @Override
    public void createSplitAsteroid(Entity e, World world) {
        if (e.getRadius()/2 > 5){
            world.addEntity(createSmallerAsteroid(e));
        }


    }


}
