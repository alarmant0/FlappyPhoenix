package com.piners.projectv1.gameObjects.PowerUps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;
import com.piners.projectv1.gameObjects.GameObject;
import com.piners.projectv1.gameObjects.Bat;
import com.piners.projectv1.R;
import java.util.List;

public class Slow extends PowerUp implements TouchesHandler {

    private Context context;

    public Slow(Context context, double positionX, double positionY) {
        super(positionX, positionY, 35);
        this.context = context;
    }

    public static void startTimer(List<GameObject> gameObjectList) {
        Bat.isSlowed = true;
        Timer timer = new Timer(5, gameObjectList);
        timer.start();
    }

    public void slow(List<GameObject> gameObjectList) {
        startTimer(gameObjectList);
        for(GameObject gameObject : gameObjectList) gameObject.setVelocityX(-2);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.teal_700);
        paint.setColor(color);
        canvas.drawCircle((float) positionX, (float) positionY, radius, paint);
    }

    @Override
    public void update() { 
        positionX+=velocityX;
    }

    @Override
    public void onTouch(List<GameObject> gameObjectList) {
        slow(gameObjectList);
    }
}
