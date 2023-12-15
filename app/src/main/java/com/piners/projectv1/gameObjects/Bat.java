package com.piners.projectv1.gameObjects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.core.content.ContextCompat;
import com.piners.projectv1.GameLoop;
import com.piners.projectv1.R;
import com.piners.projectv1.graphics.Animator;
import com.piners.projectv1.graphics.SpriteSheet;

public class Bat extends GameObject {

    private static final int sprites = 7;
    private Bitmap bitmap;
    private Animator animator;
    private static final double SPAWNS_PER_MINUTE = 220;
    private static final double SPAWNS_PER_SECOND = SPAWNS_PER_MINUTE/60.0;
    private static final double UPDATES_PER_SPAWN = GameLoop.MAX_UPS / SPAWNS_PER_SECOND;
    private static double updatesUntilNextSpawn = UPDATES_PER_SPAWN;
    private final Paint paint;

    public Bat(Context context, double positionX, double positionY) {
        super(positionX, positionY, 35);
        paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.purple_200);
        paint.setColor(color);
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bat);
        this.bitmap = Bitmap.createScaledBitmap(originalBitmap, (int) (originalBitmap.getWidth() * 1), (int) (originalBitmap.getHeight() * 1), false);
        SpriteSheet spriteSheet = new SpriteSheet(context, bitmap,sprites, 0);
        this.animator = new Animator(spriteSheet.getSpriteList());
    }

    @Override
    public void draw(Canvas canvas) { animator.draw(canvas, this); }

    @Override
    public void update() {
        animator.update();
        positionX+=velocityX;
    }

    public static boolean readyToSpawn() {
        if (updatesUntilNextSpawn <= 0) {
            updatesUntilNextSpawn += UPDATES_PER_SPAWN;
            return true;
        } else {
            updatesUntilNextSpawn--;
            return false;
        }
    }
}
