package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Random;

public class AsteroidProcessor implements IEntityProcessingService {

    private IAsteroidSplitter asteroidSplitter = new AsteroidSplitterImpl();

    Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {

        if (world.getEntities(Asteroid.class).stream()
                .count() <= 5) {
            System.out.println("Spawning asteroid");
            world.addEntity(createAsteroid(gameData));
        }



        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));

            // Update the position of the asteroid
            asteroid.setX(asteroid.getX() + changeX * 0.5);
            asteroid.setY(asteroid.getY() + changeY * 0.5);

            // Check if the asteroid hits the horizontal boundaries
            if (asteroid.getX() <= 0 || asteroid.getX() >= gameData.getDisplayWidth()) {
                // Reverse the horizontal direction by changing the rotation
                asteroid.setRotation(180 - asteroid.getRotation());
            }

            // Check if the asteroid hits the vertical boundaries
            if (asteroid.getY() <= 0 || asteroid.getY() >= gameData.getDisplayHeight()) {
                // Reverse the vertical direction by changing the rotation
                asteroid.setRotation(-asteroid.getRotation());
            }
        }







    }

    public Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        Random rnd = new Random();
        int size = rnd.nextInt(20) + 5;
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setRadius(size);
        asteroid.setRotation(rnd.nextInt(90));
        asteroidSpawnLocation(asteroid,gameData);
        asteroid.setType("Asteroid");
        return asteroid;
    }



    public void asteroidSpawnLocation(Entity asteroid, GameData gameData) {
        int asteroidSpawnX = random.nextInt(gameData.getDisplayWidth());
        int asteroidSpawnY = random.nextInt(gameData.getDisplayHeight());

        // Decide whether to spawn on top/bottom or left/right
        if (random.nextBoolean()) {  // Top or Bottom
            asteroid.setX(asteroidSpawnX);

            if (random.nextBoolean()) {
                asteroid.setY(+30);  // Top outside
                asteroid.setRotation(90 + random.nextInt(180));  // Move downward
            } else {
                asteroid.setY(gameData.getDisplayHeight() - 30);  // Bottom outside
                asteroid.setRotation(270 - random.nextInt(180));  // Move upward
            }
        } else {  // Left or Right
            asteroid.setY(asteroidSpawnY);

            if (random.nextBoolean()) {
                asteroid.setX(+30);  // Left outside
                asteroid.setRotation(random.nextInt(180));  // Move rightward
            } else {
                asteroid.setX(gameData.getDisplayWidth() -30 );  // Right outside
                asteroid.setRotation(180 + random.nextInt(180));  // Move leftward
            }
        }
    }



    /**
     * Dependency Injection using OSGi Declarative Services
     */
    public void setAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = asteroidSplitter;
    }

    public void removeAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = null;
    }


}
