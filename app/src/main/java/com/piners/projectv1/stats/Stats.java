package com.piners.projectv1.stats;

import static com.piners.projectv1.Game.SCREEN_HEIGHT;
import static com.piners.projectv1.Game.SCREEN_WIDTH;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Stats {

    private final Context context;
    private int [] top5;

    public Stats(Context context) {
        this.context = context;
        this.top5 = new int[5];
        importer();
    }

    public int[] importer() {
        File file = new File("./java/com/piners/projectv1/db/stats.txt");
        try {
            Scanner scanner = new Scanner(file);
            int i = 0;
            while(scanner.hasNextLine() && i<5) {
                int score = Integer.parseInt(scanner.next());
                top5[i] = score;
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return top5;
    }

    public void draw(Canvas canvas) {
        float x = SCREEN_WIDTH/2 - 10;
        float y = SCREEN_HEIGHT/2;
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, com.google.android.material.R.color.design_default_color_secondary);
        paint.setColor(color);
        float textSize = 100;
        paint.setTextSize(textSize);
        for (int j : top5) {
            String text = Integer.toString(j);
            canvas.drawText(text, x, y, paint);
        }
    }
}
