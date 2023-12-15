package com.piners.projectv1.gameObjects.PowerUps;

import static com.piners.projectv1.Game.SCREEN_HEIGHT;
import static com.piners.projectv1.Game.SCREEN_WIDTH;
import android.content.Context;
import com.piners.projectv1.Game;
import com.piners.projectv1.gameObjects.Coin;
import com.piners.projectv1.gameObjects.GameObject;
import java.util.List;

public abstract class PowerUp extends GameObject implements TouchesHandler {

    private static final int NUMBER_SPELLS = 3;

    public PowerUp(double positionX, double positionY, float radius) {
        super(positionX, positionY, radius);
    }

    public static void randomSpawn(Context context, List<GameObject> gameObjectList) {
        int aux = (int)(Math.random()*3000);
        if(aux < 10) {
            int x = (int) (Math.random()*NUMBER_SPELLS);
            switch (x) {
                case 0:
                    gameObjectList.add(new Destroy(context, SCREEN_WIDTH+35, (int)(Math.random()*SCREEN_HEIGHT)));
                    break;
                case 1:
                    gameObjectList.add(new Coin(context, SCREEN_WIDTH+35, (int)(Math.random()*SCREEN_HEIGHT),  Game.optionsGame.getSoundOn()));
                    break;
                case 2:
                    gameObjectList.add(new Slow(context, SCREEN_WIDTH+35, (int)(Math.random()*SCREEN_HEIGHT)));
                    break;
                default:
                    break;
            }
        }
    }
}
