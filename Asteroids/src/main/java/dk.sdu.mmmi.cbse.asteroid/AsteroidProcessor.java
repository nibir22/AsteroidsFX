package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;


import java.util.Random;

public class AsteroidProcessor implements IEntityProcessingService {



    Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {

        int spawner = (int) (Math.random() * (100)-1);
        if (spawner == 1) {
            world.addEntity(createAsteroid(gameData));
            //System.out.println("Asteroid spawned");
        }


        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            if (asteroid.isHit() == true) {

                //Randomiser for wheter spawn smaller or not

                if (asteroid.getType().equals("Asteroid")){
                    /*
                    int random = (int) (Math.random() * (2));
                    if (random == 1) {
                        splitAsteroid(asteroid, world);
                        //System.out.println("Asteroid split and smaller spawned");
                        world.removeEntity(asteroid);
                    }
                    world.removeEntity(asteroid);

                     */
                    splitAsteroid(asteroid, world);

                }



                world.removeEntity(asteroid);
                //For testing
                //System.out.println("Asteroid hit");
                continue;
            }

                //Movement
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

        //Asteroid spawner, if less than x asteroids, spawn a new one
        if (world.getEntities(Asteroid.class).stream()
                .count() <= 10) {
            //System.out.println("Spawning asteroid");
            world.addEntity(createAsteroid(gameData));
        }


    }

    public Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        Random rnd = new Random();
        int size = rnd.nextInt(20) + 5;
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setRadius(size + 5);
        asteroid.setRotation(rnd.nextInt(90));
        asteroidSpawnLocation(asteroid,gameData);
        asteroid.setType("Asteroid");
        return asteroid;
    }

    public void splitAsteroid(Entity asteroid, World world){

        int spawnDirAngle = 25;

        Entity asteroid1 = new Asteroid();
        asteroid1.setPolygonCoordinates(-5,-5,5,-5,5,5,-5,5);
        double asteroidX1 = Math.cos(Math.toRadians(asteroid.getRotation()+spawnDirAngle));
        double asteroidY1 = Math.sin(Math.toRadians(asteroid.getRotation()+spawnDirAngle));
        asteroid1.setRotation(asteroid.getRotation()+spawnDirAngle);
        asteroid1.setX(asteroid.getX() + (asteroidX1*20));
        asteroid1.setY(asteroid.getY() + (asteroidY1*20));
        asteroid1.setRadius(5);
        asteroid1.setType("SplitAsteroid");
        world.addEntity(asteroid1);

        Entity asteroid2 = new Asteroid();
        asteroid2.setPolygonCoordinates(-5,-5,5,-5,5,5,-5,5);
        double asteroidX2 = Math.cos(Math.toRadians(asteroid.getRotation()-spawnDirAngle));
        double asteroidY2 = Math.sin(Math.toRadians(asteroid.getRotation()-spawnDirAngle));
        asteroid2.setRotation(asteroid.getRotation()-spawnDirAngle);
        asteroid2.setX(asteroid.getX() + (asteroidX2*20));
        asteroid2.setY(asteroid.getY() + (asteroidY2*20));
        asteroid2.setRadius(5);
        asteroid2.setType("SplitAsteroid");


        world.addEntity(asteroid2);


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



}
