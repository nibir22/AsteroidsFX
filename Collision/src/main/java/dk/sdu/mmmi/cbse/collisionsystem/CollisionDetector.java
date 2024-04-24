package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;


public class CollisionDetector implements IPostEntityProcessingService {

    public CollisionDetector() {}

    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                if (entity2.getID().equals(entity1.getID())){

                    continue;


                    //System.out.println("Continue");


                }
                if (this.collides(entity1,entity2)){
                    // Kinda works
                    /*
                    if (entity1.getType().equals("Bullet") && entity2.getType().equals("Bullet")){
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                    } if (entity1.getType().equals("Enemy") && entity2.getType().equals("Bullet") || entity1.getType().equals("Bullet") && entity2.getType().equals("Enemy")){
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);

                    } if (entity1.getType().equals("Player") && entity2.getType().equals("Enemy") || entity1.getType().equals("Enemy") && entity2.getType().equals("Player")){
                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
                    } if (entity1.getType().equals("Enemy") && entity2.getType().equals("Asteroids")){
                        world.removeEntity(entity1);
                    } if (entity1.getType().equals("Asteroids") && entity2.getType().equals("Enemy")) {
                        world.removeEntity(entity2);
                    }
                     */

                    entity1.setHit(true);
                    entity2.setHit(true);
                    //Testing hours
                    /*
                    System.out.println("Collision detected: " + entity1.getType() + " with " + entity2.getType());
                    System.out.println(entity1.isHit() + " " + entity2.isHit());
                     */
                }


            }
        }

    }

    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

}
