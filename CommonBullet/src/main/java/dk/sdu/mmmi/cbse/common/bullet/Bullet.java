package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;


public class Bullet extends Entity {



    public static final double SPEED = 2.0;
    private int duration =5;

    private static final long CoolDown = 200;
    private static long lastFired = 0;

    public Bullet(){

        //Old bullet timer, was removed when implementing enemy ships

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFired < CoolDown){
            System.out.println("Nope, cooldown in effect");
        }
        lastFired = currentTime;



    }



    public void update(){
        double DelX = Math.cos(Math.toRadians(getRotation()))*SPEED;
        double DelY = Math.sin(Math.toRadians(getRotation()))*SPEED;
        this.setX(this.getX()+DelX);
        this.setY(this.getY()+DelY);



    }

    public static boolean canFire(){
        return System.currentTimeMillis() - lastFired >= CoolDown;
    };


    



}
