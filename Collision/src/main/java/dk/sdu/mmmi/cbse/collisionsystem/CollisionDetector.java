package dk.sdu.mmmi.cbse.collisionsystem;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;







public class CollisionDetector implements IPostEntityProcessingService {




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

                    //Microservice
                    int scoreadd =0;
                    boolean scoreUpdated = false;
                    if (entity1.getType().equals("Asteroid") && entity2.getType().equals("Bullet")){

                        //System.out.println("Asteroid hit by bullet");
                    } else {
                        if (entity1.getType().equals("Bullet") && entity2.getType().equals("SplitAsteroid")){


                            //System.out.println("SplitAsteroid hit by bullet");
                        }
                    }

                    if (entity1.getType().equals("SplitAsteroid") && entity2.getType().equals("SplitAsteroid")){

                        world.removeEntity(entity1);
                        world.removeEntity(entity2);
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
