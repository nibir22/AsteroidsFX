package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;


public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;
    private Entity extraEnemy;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {


        if (world.getEntities(Enemy.class).isEmpty()){
            // Add entities to the world if theres none enemies
            enemy = createEnemyShip(gameData);
            world.addEntity(enemy);

        }
        Random random = new Random();
        if (random.nextBoolean()){
             enemy = createEnemyShip(gameData);
            world.addEntity(enemy);
        }
    }

    private Entity createEnemyShip(GameData gameData) {
        Random random = new Random();
        enemy = new Enemy();
        enemy.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);
        enemy.setRadius(1);

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

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemy);
    }

}
