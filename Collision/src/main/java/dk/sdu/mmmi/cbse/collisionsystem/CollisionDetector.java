package dk.sdu.mmmi.cbse.collisionsystem;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import java.util.Timer;
import java.util.TimerTask;


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
                    if (entity1.getType().equals("SplitAsteroid") && entity2.getType().equals("SplitAsteroid")){
                        // Create a timer task to change the type after 5 seconds
                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                entity1.setType("Asteroid");
                                entity2.setType("Asteroid");
                            }
                        };

                        // Schedule the task
                        Timer timer = new Timer();
                        timer.schedule(task, 2000); //Wait 5 seconds then change the type back to asteroid

                        continue;
                    }
                    if (!entity1.isHit()) {
                        entity1.setHealth(entity1.getHealth() - 1);
                        if (entity1.getHealth() == 0){
                            entity1.setHit(true);
                        }
                    }

                    if (!entity2.isHit()) {
                        entity2.setHealth(entity2.getHealth() - 1);
                        if (entity2.getHealth() == 0){
                            entity2.setHit(true);
                        }
                    }


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
