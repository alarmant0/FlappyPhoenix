package com.piners.projectv1.gamePanel;

import android.graphics.Bitmap;
import android.graphics.Canvas;
public class Background {

    private Bitmap bitmap;
    private float x;

    public Background(Bitmap bitmap, int x) {
        this.bitmap = bitmap;
        this.x = x;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }
    public float getCoords() { return this.x; }

    public void update() { x-=0.3; }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, x, 0 , null);
    }

    public int getWidth() { return bitmap.getWidth(); }
}
