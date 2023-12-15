package com.piners.projectv1.gameObjects;

import android.graphics.Canvas;

/**
 * GameObject is an abstract class which is the foundation of all world objects in the game.
 */

public abstract class GameObject {

    public static boolean isSlowed = false;
    protected double positionX, positionY = 0.0;
    protected double velocityX;
    public double velocityY = 0.0;
    protected float radius;

    public GameObject(double positionX, double positionY, float radius) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;
        if(!isSlowed) {
            velocityX=-5;
        }
        else {
            velocityX -=2;
        }
    }

    public abstract void draw(Canvas canvas);
    public abstract void update();

    public double getPositionX() { return this.positionX; }
    public double getPositionY() { return this.positionY; }

    /**
     * getDistanceBetweenObjects returns the distance between two game objects
     */
    public static double getDistanceBetweenObjects(GameObject obj1, GameObject obj2) {
        return Math.sqrt(
                Math.pow(obj2.getPositionX() - obj1.getPositionX(), 2) +
                        Math.pow(obj2.getPositionY() - obj1.getPositionY(), 2)
        );
    }

    public static boolean isColliding(GameObject obj1, GameObject obj2) {
        double distance = getDistanceBetweenObjects(obj1, obj2);
        double distanceToCollision = obj1.radius + obj2.radius;
        if (distance < distanceToCollision) return true;
        else { return false; }
    }

    public void setVelocityX(double i) { this.velocityX=i; }
}
