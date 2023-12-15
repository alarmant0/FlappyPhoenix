package com.piners.projectv1;

public class OptionsGame {
    private boolean musicOn;
    private boolean soundEffectsOn;

    public OptionsGame() {
        this.musicOn = true;
        this.soundEffectsOn = true;
    }

    public boolean getMusicOn() {
        return this.musicOn;
    }

    public boolean getSoundOn() {
        return this.soundEffectsOn;
    }

    public void setMusic(boolean b) {
        this.musicOn = b;
    }

    public void setSoundEffects(boolean b) {
        this.soundEffectsOn = b;
    }
}
