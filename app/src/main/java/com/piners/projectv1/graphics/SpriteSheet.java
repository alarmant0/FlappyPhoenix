package com.piners.projectv1.graphics;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

    private Bitmap bitmap;
    private Context context;
    private int width;
    private int height;
    private int length;
    private int division;

    public SpriteSheet(Context context, Bitmap bitmap, int division, int lessWidth) {
        this.context = context;
        this.bitmap = bitmap;
        this.width = bitmap.getWidth() - lessWidth;
        this.height = bitmap.getHeight();
        this.length = width/division;
        this.division = division;
    }

    public List<Sprite> getSpriteList() {
        List<Sprite> spriteList = new ArrayList<>();
        for(int i = 0;i<division;i++) spriteList.add(new Sprite(this, new Rect(i*length, 0, (i+1)*length, length-1)));
        return spriteList;
    }

    public Bitmap getBitmap() { return this.bitmap; }
}
