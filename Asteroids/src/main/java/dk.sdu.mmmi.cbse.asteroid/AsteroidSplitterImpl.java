package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import java.util.Random;

public class AsteroidSplitterImpl implements IAsteroidSplitter {

    @Override
    public void createSplitAsteroid(Entity e, World world) {
        if (!(e instanceof Asteroid)) return;

        Asteroid asteroid = (Asteroid) e;
        int originalSize = (int) asteroid.getRadius();

        // Ensure we're working with a "big" asteroid based on its radius.
        if (originalSize > 5) { // Example threshold for "big" asteroids.
            Random rnd = new Random();

            for (int i = 0; i < 2; i++) {
                int newSize = originalSize / 2;
                Asteroid smallAsteroid = new Asteroid();
                smallAsteroid.setRadius(newSize);
                smallAsteroid.setX(asteroid.getX() + rnd.nextInt(10) - 5); // Slightly randomize position
                smallAsteroid.setY(asteroid.getY() + rnd.nextInt(10) - 5);
                smallAsteroid.setRotation(rnd.nextInt(360));

                // Set polygon coordinates for the smaller asteroids
                smallAsteroid.setPolygonCoordinates(newSize, -newSize, -newSize, -newSize, -newSize, newSize, newSize, newSize);

                world.addEntity(smallAsteroid);
            }

            world.removeEntity(asteroid); // Remove the original asteroid
        }
    }
}
