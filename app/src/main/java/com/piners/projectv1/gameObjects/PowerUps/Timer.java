package com.piners.projectv1.gameObjects.PowerUps;

import com.piners.projectv1.gameObjects.GameObject;
import java.util.List;

public class Timer extends Thread {

    private int seconds;
    private List<GameObject> gameObjectList;

    public Timer(int seconds, List<GameObject> gameObjectList) {
        this.seconds = seconds;
        this.gameObjectList = gameObjectList;
    }

    @Override
    public void run() {
        super.run();
        for (int i = 1; i <= seconds; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GameObject.isSlowed = false;
        for(GameObject gameObject : gameObjectList) gameObject.setVelocityX(-5);
    }
}
