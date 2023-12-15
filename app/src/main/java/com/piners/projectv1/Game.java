package com.piners.projectv1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import com.piners.projectv1.gameObjects.OnPlayerDeathListener;
import com.piners.projectv1.gameObjects.Phoenix;
import com.piners.projectv1.gameObjects.GameObject;
import com.piners.projectv1.gameObjects.PowerUps.PowerUp;
import com.piners.projectv1.gameObjects.Bat;
import com.piners.projectv1.gamePanel.Background;
import com.piners.projectv1.gamePanel.Bioms;
import com.piners.projectv1.gamePanel.GameOver;
import com.piners.projectv1.gamePanel.Performance;
import com.piners.projectv1.stats.Score;
import com.piners.projectv1.stats.Stats;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game extends SurfaceView implements SurfaceHolder.Callback {

    private Bioms bioms;
    private Background backgroundImageBackUp;
    private Background backgroundImage;
    public final static float SCREEN_HEIGHT = 2186;
    public final static float SCREEN_WIDTH = 1080;
    public final static float GRAVITY = 14f;
    private Performance performance;
    private GameLoop gameLoop;
    private Bitmap background;
    private final Phoenix phoenix;
    private final List<GameObject> gameObjectList = new ArrayList<>();
    private final GameOver gameOver;
    private boolean gameStarted;
    private Score score;
    private int updates = 0;
    private Stats stats;
    private Window window;
    private OnPlayerDeathListener listener;
    private MediaPlayer mediaPlayer;
    public static OptionsGame optionsGame;

    public Game(Context context, Window window, OnPlayerDeathListener listener, OptionsGame optionsGame) {

        super(context);
        this.optionsGame = optionsGame;
        if(optionsGame.getMusicOn()) mediaPlayer = MediaPlayer.create(context, R.raw.backgroundmusic);
        this.listener = listener;

        // Get surfaceHolder and callback

        this.window = window;
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        // Initialize the gameLoop

        gameLoop = new GameLoop(this, surfaceHolder);
        this.performance = new Performance(context, gameLoop);
        gameStarted = false;

        // Get the screen dimensions

        DisplayMetrics displayMetrics = new DisplayMetrics();
        window.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;
        int screenHeight = displayMetrics.heightPixels;

        // Initialize gameOver
        gameOver = new GameOver(context);

        //Initialize Stats
        this.stats = new Stats(getContext());

        // Initialize phoenix
        this.phoenix = new Phoenix(context, (float) screenWidth/2 , (float) screenHeight/2, optionsGame.getSoundOn());

        // Making the firts background
        Bitmap originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.clouds_night);
        background = Bitmap.createScaledBitmap(originalBitmap, screenWidth, screenHeight, true);
        backgroundImage = new Background(background, 0);
        backgroundImageBackUp = new Background(background, backgroundImage.getWidth());

        // Initialize bioms
        List<Background> backgrounds = new ArrayList<Background>();
        Background background1 = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.mountain1),0);
        Background background2 = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.glacial_mountains),0);
        backgrounds.add(backgroundImage);
        backgrounds.add(background1);
        backgrounds.add(background2);
        bioms = new Bioms(backgrounds);

        // Initialize Score
        this.score = new Score(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch event actions
        phoenix.setIsJumping(true);
        gameStarted = true;
        return super.onTouchEvent(event);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        backgroundImage.draw(canvas);
        backgroundImageBackUp.draw(canvas);
        score.draw(canvas);
        performance.draw(canvas);
        phoenix.draw(canvas);
        for(GameObject gameObject : gameObjectList) gameObject.draw(canvas);
        if(phoenix.getHealth() <= 0) { gameOver.show(canvas); }
    }

    public void releaseMusic() {
        if(optionsGame.getMusicOn()) {
            mediaPlayer.stop();
            mediaPlayer.setOnCompletionListener(mp -> mediaPlayer.release());
        }
    }

    public void update() {
        if (gameStarted) {
            if(phoenix.getHealth() <= 0) {
                listener.onPlayerDeath();
                releaseMusic();
                gameLoop.stopLoop();
            }
            background = bioms.getBackground(score.getScore()).getBitmap();
            if(backgroundImage.getCoords() + backgroundImage.getWidth() <= 0) backgroundImage = new Background(background, background.getWidth());
            if(backgroundImageBackUp.getCoords() + backgroundImageBackUp.getWidth() <= 0) backgroundImageBackUp = new Background(background, background.getWidth());
            backgroundImage.update();
            backgroundImageBackUp.update();
            phoenix.update();
            if(Bat.readyToSpawn()) gameObjectList.add(new Bat(getContext(), SCREEN_WIDTH+35, (int)(Math.random()*SCREEN_HEIGHT)));
            PowerUp.randomSpawn(getContext(), gameObjectList);
            for(GameObject gameObject : gameObjectList) gameObject.update();
            Iterator<GameObject> gameObjectIterator = gameObjectList.iterator();
            while(gameObjectIterator.hasNext()) {
                GameObject gameObject = gameObjectIterator.next();
                if(GameObject.isColliding(gameObject, phoenix)) {
                    gameObjectIterator.remove();
                    if(gameObject instanceof PowerUp) {
                        ((PowerUp) gameObject).onTouch(gameObjectList);
                        break;
                    }
                    if(gameObject instanceof Bat) {
                        phoenix.setHealth(phoenix.getHealth()-1);
                    }
                }
                if(gameObject.getPositionX() <= -32) { gameObjectIterator.remove(); }
            }
            if(updates >= 150) {
                score.add();
                updates = 0;
            }
            updates++;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d("Game.java", "surfaceCreated()");
        if (gameLoop.getState().equals(Thread.State.TERMINATED)) {
            gameLoop = new GameLoop(this, surfaceHolder);
        }
        gameLoop.startLoop();
        if(optionsGame.getMusicOn()) mediaPlayer.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    public void pause() {
        gameLoop.stopLoop();
    }
}
