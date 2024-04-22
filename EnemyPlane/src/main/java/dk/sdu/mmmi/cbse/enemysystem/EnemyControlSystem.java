package dk.sdu.mmmi.cbse.enemysystem;

import java.util.*;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {
    private final Random random = new Random();
    private double speed = 0.8;




    @Override
    public void process(GameData gameData, World world) {


        if (world.getEntities(Enemy.class).stream()
                .count() <= 1) {
            world.addEntity(createEnemyShip(gameData));
            System.out.println("Spawning Enemy");
        }


        for (Entity enemy : world.getEntities(Enemy.class)) {


            // Adjust speed randomly within a range
            double speedVariance = 0.5; // Half speed up or down
            speed = 1.0 + (random.nextDouble() * 2 * speedVariance) - speedVariance;

            // Slightly adjust rotation to change direction gradually
            int rotationVariance = 5;
            double currentRotation = enemy.getRotation();
            currentRotation += random.nextInt(rotationVariance * 2) - rotationVariance; // Adjust current rotation
            enemy.setRotation(currentRotation);

            // Move based on current rotation
            double deltaX = Math.cos(Math.toRadians(enemy.getRotation())) * speed;
            double deltaY = Math.sin(Math.toRadians(enemy.getRotation())) * speed;
            enemy.setX(enemy.getX() + deltaX);
            enemy.setY(enemy.getY() + deltaY);

            double margin = 50; // Distance from the edge to start turning away
            if (enemy.getX() < margin) {
                enemy.setRotation(0); // Point right
            } else if (enemy.getX() > gameData.getDisplayWidth() - margin) {
                enemy.setRotation(180); // Point left
            }

            if (enemy.getY() < margin) {
                enemy.setRotation(90); // Point up
            } else if (enemy.getY() > gameData.getDisplayHeight() - margin) {
                enemy.setRotation(270); // Point down
            }



            //Shooting logic, simple random function
            if (Math.random() > 0.99){
                for (BulletSPI bulletSPI : getBulletSPIs()){
                    Entity bullet = bulletSPI.createBullet(enemy,gameData);
                    world.addEntity(bullet);
                    world.addEntity(bullet);
                }
            }

        }

    }
    public Entity createEnemyShip(GameData gameData) {
        Random random = new Random();
        Entity enemy = new Enemy();
        enemy.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
        enemy.setRadius(4);
        enemy.setType("Enemy");



        // Randomly decide which edge to spawn the enemy
        int edge = random.nextInt(4); // Returns an integer between 0 and 3

        switch (edge) {
            case 0: // Top edge
                enemy.setX(random.nextInt(gameData.getDisplayWidth()));
                enemy.setY(gameData.getDisplayHeight());
                break;
            case 1: // Bottom edge
                enemy.setX(random.nextInt(gameData.getDisplayWidth()));
                enemy.setY(0);
                break;
            case 2: // Left edge
                enemy.setX(0);
                enemy.setY(random.nextInt(gameData.getDisplayHeight()));
                break;
            case 3: // Right edge
                enemy.setX(gameData.getDisplayWidth());
                enemy.setY(random.nextInt(gameData.getDisplayHeight()));
                break;
        }

        return enemy;
    }



    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
