package com.piners.projectv1.graphics;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * TODO
 */

public class CustomSpriteSheet {

    private Bitmap bitmap;
    private Context context;
    private int width;
    private int height;
    private int length;
    private int division;

    public CustomSpriteSheet(Context context, Bitmap bitmap, int division, int lessWidth) {
        this.context = context;
        this.bitmap = bitmap;
        this.width = bitmap.getWidth() - lessWidth;
        this.height = bitmap.getHeight();
        this.length = width/division;
        this.division = division;
    }
}
