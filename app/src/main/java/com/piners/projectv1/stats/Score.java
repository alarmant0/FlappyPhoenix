package com.piners.projectv1.stats;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Score {

    private int score;
    private Context context;

    public Score(Context context) {
        score = 0;
        this.context = context;
    }

    public int getScore() {
        return this.score;
    }

    public void add() {
        score++;
    }

    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        String text = String.valueOf(score);
        paint.setTextSize(400);
        paint.setAlpha(128);

        // get the bounds of the text
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // calculate the x and y coordinates to draw the text
        int x = (canvas.getWidth() / 2) - (bounds.width() / 2);
        int y = (canvas.getHeight() / 2) - (bounds.height() / 2);

        // draw the text
        canvas.drawText(text, x, y, paint);

    }
}
