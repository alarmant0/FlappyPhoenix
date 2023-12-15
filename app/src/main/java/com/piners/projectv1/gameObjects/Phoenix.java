package com.piners.projectv1.gameObjects;

import static com.piners.projectv1.Game.GRAVITY;
import static com.piners.projectv1.Game.SCREEN_HEIGHT;
import static com.piners.projectv1.Game.SCREEN_WIDTH;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import androidx.core.content.ContextCompat;
import com.piners.projectv1.R;
import com.piners.projectv1.graphics.Animator;
import com.piners.projectv1.graphics.SpriteSheet;

public class Phoenix extends GameObject{

    private static final int sprites = 6;
    private int health;
    private final Paint paint;
    private long startTime;
    private boolean isJumping;
    private final Context context;
    private static final float jumpStrengthY = 8;
    private float jumpStrengthX = 4;
    private Animator animator;
    private Bitmap bitmap;
    private boolean soundOn;

    public Phoenix(Context context, float positionX, float positionY, boolean soundOn) {
        super(positionX, positionY, 42);
        paint = new Paint();
        this.soundOn = soundOn;
        this.context = context;
        int color = ContextCompat.getColor(context, R.color.teal_200);
        paint.setColor(color);
        this.isJumping = false;
        health = 5;
        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
        bitmapOptions.inScaled = false;
        Bitmap originalBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.phoenix_spritesheet);
        this.bitmap = Bitmap.createScaledBitmap(originalBitmap, (int) (originalBitmap.getWidth() * 1.5), (int) (originalBitmap.getHeight() * 1.5), false);
        SpriteSheet spriteSheet = new SpriteSheet(context, bitmap,sprites, 504);
        this.animator = new Animator(spriteSheet.getSpriteList());
    }

    public void update() {
        if (isJumping) {
            if (positionY-35 > SCREEN_HEIGHT) positionY = 0;
            if(positionX+35 >= SCREEN_WIDTH || positionX-35 <= 0) jumpStrengthX = -jumpStrengthX;
            if (positionY+35 < 0) positionY = SCREEN_HEIGHT;
            float velocityOfY = jumpStrengthY - (GRAVITY * (System.currentTimeMillis() - startTime)/1000F);
            if (velocityOfY < -40) velocityOfY = -40;
            velocityY = velocityOfY;
            positionY -= velocityY;
            animator.update();
        }
    }

    public int getHealth() {
        return this.health;
    }

    public void setHealth(int i) {
        health = i;
    }

    public void setIsJumping(boolean b) {
        if(soundOn) {
            MediaPlayer a = MediaPlayer.create(context,R.raw.jump);
            a.setVolume(2,2);
            MediaPlayer.create(context, R.raw.jump).start();
        }
        this.isJumping = b;
        startTime = System.currentTimeMillis();
    }

    public void draw(Canvas canvas) { animator.draw(canvas, this); }
}
