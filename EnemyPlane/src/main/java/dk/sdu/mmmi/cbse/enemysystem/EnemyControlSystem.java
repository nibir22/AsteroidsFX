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
    private double speed = 1.0;

    // Cooldown management with individual tracking
    private final Map<Entity, Long> lastShotTimes = new HashMap<>();
    private final long shootCooldown = 10000; // 10 seconds in milliseconds



    @Override
    public void process(GameData gameData, World world) {
        long currentTime = System.currentTimeMillis();


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


            // Initialize or check cooldown for shooting
            //Not working atm it goes machine gun fire

            lastShotTimes.putIfAbsent(enemy, 0L); // Initialize if not present
            if (currentTime - lastShotTimes.get(enemy) > shootCooldown) {


                getBulletSPIs().stream().findFirst().ifPresent(shoot -> {
                    world.addEntity(shoot.createBullet(enemy, gameData));

                    lastShotTimes.put(enemy, currentTime); // Reset the cooldown timer for this enemy
                    System.out.println("Current Time: " + currentTime);
                    System.out.println("Last Shot for Enemy: " + lastShotTimes.get(enemy));
                    System.out.println("Time Since Last Shot: " + (currentTime - lastShotTimes.get(enemy)));
                    System.out.println("Cooldown: " + shootCooldown);

                });
            }

             
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
