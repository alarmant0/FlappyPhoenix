package com.piners.projectv1;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameLoop extends Thread {

    public static final double MAX_UPS = 60.0;
    private static final double UPS_PERIOD = 1E+3/MAX_UPS;
    private boolean isRunning = false;
    private SurfaceHolder surfaceHolder;
    private Game game;
    private double averageUPS;
    private double averageFPS;

    public GameLoop(Game game, SurfaceHolder surfaceHolder) {
        this.game = game;
        this.surfaceHolder = surfaceHolder;
    }

    public double getAverageUPS() {
        return averageUPS;
    }

    public double getAverageFPS() {
        return averageFPS;
    }

    public void startLoop() {
        Log.d("GameLoop.java", "startLoop()");
        isRunning = true;
        super.start();
    }

    @Override
    public void run() {
        Log.d("GameLoop.java", "run()");
        super.run();

        //Declare time and cycle count variables
        int updateCount = 0;
        int frameCount = 0;

        long starTime;
        long elapsedTime;
        long sleepTime;

        //GameLoop
        Canvas canvas = null;
        starTime = System.currentTimeMillis();
        while(isRunning) {
            //Try to update and render the game
            try {
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder ) {
                    game.update();
                    updateCount++;
                    game.draw(canvas);
                }
            }
            catch (IllegalArgumentException e) {
                e.printStackTrace();
            } finally {
                if(canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        frameCount++;
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //Pause game loop to not exceed target UPS
            elapsedTime = System.currentTimeMillis() - starTime;
            sleepTime = (long)(updateCount*UPS_PERIOD - elapsedTime);
            if (sleepTime > 0) {
                try {
                    sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //Skip frames to keep up with target UPS
            //while(sleepTime < 0 && updateCount < MAX_UPS - 1) {
            //game.update();
            //updateCount++;
            //elapsedTime = System.currentTimeMillis() - starTime;
            //sleepTime = (long)(updateCount*UPS_PERIOD - elapsedTime);
            //}
            //Calculate average UPS and FPS
            elapsedTime = System.currentTimeMillis() - starTime;
            if (elapsedTime >= 1000) {
                averageUPS = (int)(updateCount / (1E-3*elapsedTime));
                averageFPS = (int)(frameCount / (1E-3*elapsedTime));
                updateCount = 0;
                frameCount = 0;
                starTime = System.currentTimeMillis();
            }
        }
    }

    public void stopLoop() {
        Log.d("GameLoop.java", "stopLoop()");
        this.isRunning = false;
        // Wait for thread to join
        try {
            join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
