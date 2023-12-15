package com.piners.projectv1.gamePanel;

import static com.piners.projectv1.Game.SCREEN_WIDTH;
import static com.piners.projectv1.Game.SCREEN_HEIGHT;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.app.Activity;
import androidx.core.content.ContextCompat;
import com.piners.projectv1.R;

public class GameOver extends Activity{

    private Context context;

    public GameOver(Context context) {
        this.context = context;
    }

    public void draw(Canvas canvas) {
        String text = "Game Over";
        float x = SCREEN_WIDTH/2 - 350;
        float y = SCREEN_HEIGHT/2;
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.black);
        paint.setColor(color);
        float textSize = 150;
        paint.setTextSize(textSize);
        canvas.drawText(text, x, y, paint);
    }

    public void show(Canvas canvas) {
        draw(canvas);
    }
}
