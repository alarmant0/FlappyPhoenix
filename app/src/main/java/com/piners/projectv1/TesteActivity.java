package com.piners.projectv1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Switch;

import com.piners.projectv1.gameObjects.OnPlayerDeathListener;

public class TesteActivity extends Activity implements OnPlayerDeathListener {

    private Game game;
    private Button startButton;
    private Button restartButton;
    private Button optionButton;
    private Button exitButton;
    private Window window;
    private Switch switchMusic;
    private Switch switchSound;
    private OptionsGame optionsGame = new OptionsGame();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("GameActivity.java", "onCreate()");
        super.onCreate(savedInstanceState);

        // Set window to fullscreen

        window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set content view to game, so that objects in the game class can be rendered to the screen
        this.game = new Game(this, this.window, this, optionsGame);
        setContentView(game);

    }

    @Override
    public void onPlayerDeath() {

    }
}
