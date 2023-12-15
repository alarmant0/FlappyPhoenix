package com.piners.projectv1.graphics;

import android.graphics.Canvas;
import com.piners.projectv1.gameObjects.GameObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animator {
    private static final int MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME = 7;
    private int updates = 0;
    private List<Sprite> spriteList;
    private Map<Integer, Sprite> integerSpriteMap = new HashMap<>();
    private int currentState;

    public Animator(List<Sprite> spriteList) {
        this.spriteList = spriteList;
        for(int i = 0;i<spriteList.size();i++) { integerSpriteMap.put(i,spriteList.get(i)); }
        this.currentState = 0;
    }

    public void draw(Canvas canvas, GameObject gameObject) { drawFrame(canvas, gameObject, integerSpriteMap.get(currentState)); }

    public void update() {
        if(updates > MAX_UPDATES_BEFORE_NEXT_MOVE_FRAME) {
            updates = 0;
            if(currentState >= spriteList.size() - 1) {
                currentState = 0;
            }
            else {
                currentState++;
            }
        }
        if(currentState >= spriteList.size() - 1) {
            currentState = 0;
        }
        updates++;
    }

    public void drawFrame(Canvas canvas, GameObject gameObject, Sprite sprite) {
        sprite.draw(canvas,
                (int) (gameObject.getPositionX() - sprite.getWidth()/2),
                (int) (gameObject.getPositionY())- sprite.getHeight()/2);
    }
}
