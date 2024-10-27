package com.badlogic.hassubhoi;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;


public class AngryBirdGames extends Game {
    private com.badlogic.hassubhoi.UIManager uiManager;
    private Music backgroundMusic;

    @Override
    public void create() {
        uiManager = new com.badlogic.hassubhoi.UIManager(this);

        uiManager.showSplashScreen();

        // load and play background music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background_music.mp3"));
//        backgroundMusic.setLooping(true);
//        backgroundMusic.play();
        com.badlogic.hassubhoi.SoundManager.getInstance().setBackgroundMusic(backgroundMusic);


        uiManager.showSplashScreen(); // Start with the splash screen
    }
    public Music getBackgroundMusic() {
        return backgroundMusic;
    }


    public com.badlogic.hassubhoi.UIManager getUIManager() {
        return uiManager;
    }
    @Override
    public void render() {
        super.render();

        // fullscreen mode when F11 is pressed
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(800, 480);
            } else {
                DisplayMode displayMode = Gdx.graphics.getDisplayMode();
                Gdx.graphics.setFullscreenMode(displayMode);
            }
        }
    }
    public void debugSwitchScreen(Screen screen) {
        setScreen(screen);
    }

    @Override
    public void dispose() {
        super.dispose();
        // dispose background music
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
        com.badlogic.hassubhoi.SoundManager.getInstance().dispose();

    }
}
