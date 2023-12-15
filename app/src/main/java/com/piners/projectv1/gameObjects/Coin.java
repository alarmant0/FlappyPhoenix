package com.piners.projectv1.gameObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import androidx.core.content.ContextCompat;
import com.piners.projectv1.R;
import com.piners.projectv1.gameObjects.PowerUps.PowerUp;
import com.piners.projectv1.gameObjects.PowerUps.TouchesHandler;
import com.piners.projectv1.graphics.Animator;
import com.piners.projectv1.graphics.SpriteSheet;
import java.util.List;

public class Coin extends PowerUp implements TouchesHandler {

    private Context context;
    private Animator animation;
    private Bitmap bitmap;
    private boolean soundOn;

    public Coin(Context context, double positionX, double positionY, boolean soundOn) {
        super(positionX, positionY, 35);
        this.context = context;
        this.soundOn = soundOn;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin);
        this.bitmap = Bitmap.createScaledBitmap(originalBitmap, originalBitmap.getWidth() * 2, originalBitmap.getHeight() * 2, false);
        SpriteSheet spriteSheet = new SpriteSheet(context,bitmap, 5, 0);
        this.animation = new Animator(spriteSheet.getSpriteList());
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.invencible);
        paint.setColor(color);
        animation.draw(canvas, this);
    }

    @Override
    public void update() {
        positionX += velocityX;
        animation.update();
    }

    @Override
    public void onTouch(List<GameObject> gameObjectList) {
        if(soundOn) MediaPlayer.create(context, R.raw.coin).start();
    }
}
