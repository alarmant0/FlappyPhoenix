package com.piners.projectv1.gameObjects.PowerUps;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;
import com.piners.projectv1.gameObjects.GameObject;
import com.piners.projectv1.R;
import java.util.List;

public class Destroy extends PowerUp implements TouchesHandler {

    private Context context;

    public Destroy(Context context, double positionX, double positionY) {
        super(positionX, positionY, 35);
        this.context = context;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.red);
        paint.setColor(color);
        canvas.drawCircle((float) positionX, (float) positionY, radius, paint);
    }

    @Override
    public void update() { positionX+=velocityX; }

    public void destroy(List<GameObject> gameObjectList) { gameObjectList.removeAll(gameObjectList); }

    @Override
    public void onTouch(List<GameObject> gameObjectList) {
        destroy(gameObjectList);
    }
}
