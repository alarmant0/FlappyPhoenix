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

public class MainActivity extends Activity implements OnPlayerDeathListener {

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

        setContentView(R.layout.main_menu);
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> startGame());
        optionButton = findViewById(R.id.optionsButton);
        optionButton.setOnClickListener(v -> goToMenu());
        this.game = new Game(this, this.window, this, optionsGame);
    }

    public void goToOptions() {
        setContentView(R.layout.options_menu);
        switchMusic = findViewById(R.id.switchMusic);
        switchSound = findViewById(R.id.switchSound);
        switchSound.setOnCheckedChangeListener((buttonView, isChecked) -> {
            optionsGame.setSoundEffects(isChecked);
        });
        switchMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            optionsGame.setMusic(isChecked);
        });
        Button exitButtonOptions = findViewById(R.id.exitButtonOptions);
        switchMusic.setChecked(optionsGame.getMusicOn());
        switchSound.setChecked(optionsGame.getSoundOn());
        exitButtonOptions.setOnClickListener(v -> goToMenu());
    }

    public void goToMenu() {
        setContentView(R.layout.main_menu);
        startButton = findViewById(R.id.startButton);
        startButton.setOnClickListener(v -> startGame());
        optionButton = findViewById(R.id.optionsButton);
        optionButton.setOnClickListener(v -> goToOptions());
    }

    @SuppressLint("ResourceType")
    @Override
    public void onPlayerDeath() {
        runOnUiThread(() -> {
            setContentView(R.layout.try_again);
            restartButton = findViewById(R.id.restartButton);
            restartButton.setOnClickListener(v -> restartGame());
            exitButton = findViewById(R.id.exitButton);
            exitButton.setOnClickListener(v -> goToMenu());
        });
    }

    private void restartGame() {
        this.game = new Game(this, this.window, this, optionsGame);
        getWindow().setContentView(game);
    }

    private void startGame() {
        //Intent intent = new Intent(this, TesteActivity.class);
        //startActivity(intent);
        this.game = new Game(this, this.window, this, optionsGame);
        getWindow().setContentView(game);
    }

    @Override
    protected void onStart() {
        Log.d("GameActivity.java", "onStart()");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("GameActivity.java", "onResume()");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("GameActivity.java", "onPause()");
        game.pause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("GameActivity.java", "onStop()");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("GameActivity.java", "onDestroy()");
        super.onDestroy();
    }
}