package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {


    // Value that determines the speed of the bullet
    double speedFactor = 2.0;

    @Override
    public void process(GameData gameData, World world) {


        for (Entity bullet : world.getEntities(Bullet.class)) {
            double changeX = Math.cos(Math.toRadians(bullet.getRotation())) * speedFactor;
            double changeY = Math.sin(Math.toRadians(bullet.getRotation())) * speedFactor;
            bullet.setX(bullet.getX() + changeX);
            bullet.setY(bullet.getY() + changeY);

            //New update who this
            if (bullet.isHit()== true){
                world.removeEntity(bullet);
            }


            if (bullet.getX() > gameData.getDisplayWidth() ||
                    bullet.getX() + gameData.getDisplayWidth() < gameData.getDisplayWidth() ||
                    bullet.getY() > gameData.getDisplayHeight() ||
                    bullet.getY() + gameData.getDisplayHeight() < gameData.getDisplayHeight()) {
                world.removeEntity(bullet);
            }
        }
    }
    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();

        bullet.setPolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);
        double changeX = Math.cos(Math.toRadians(shooter.getRotation()));
        double changeY = Math.sin(Math.toRadians(shooter.getRotation()));
        bullet.setX(shooter.getX() + changeX * 10);
        bullet.setY(shooter.getY() + changeY * 10);
        bullet.setRotation(shooter.getRotation());
        bullet.setRadius(2);
        bullet.setType("Bullet");
        bullet.setHealth(1);


        return bullet;
    }


}