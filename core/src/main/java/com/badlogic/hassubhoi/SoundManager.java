package com.badlogic.hassubhoi;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {
    private static SoundManager instance;

    private boolean soundsEnabled;
    private boolean musicEnabled;

    private Music backgroundMusic;

    private SoundManager() {
        soundsEnabled = true; // default settings
        musicEnabled = true;
    }

    public static SoundManager getInstance() {
        if (instance == null) {
            instance = new SoundManager();
        }
        return instance;
    }

    public void setSoundsEnabled(boolean enabled) {
        soundsEnabled = enabled;
    }

    public boolean areSoundsEnabled() {
        return soundsEnabled;
    }

    public void setMusicEnabled(boolean enabled) {
        musicEnabled = enabled;
        if (backgroundMusic != null) {
            if (enabled) {
                backgroundMusic.play();
            } else {
                backgroundMusic.pause();
            }
        }
    }

    public boolean isMusicEnabled() {
        return musicEnabled;
    }

    public void setBackgroundMusic(Music music) {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
        backgroundMusic = music;
        if (musicEnabled) {
            backgroundMusic.setLooping(true);
            backgroundMusic.play();
        }
    }

    public void playSound(Sound sound) {
        if (soundsEnabled && sound != null) {
            sound.play();
        }
    }

    public void dispose() {
        if (backgroundMusic != null) {
            backgroundMusic.dispose();
        }
    }

    public void pauseBackgroundMusic() {
        if (backgroundMusic != null && backgroundMusic.isPlaying()) {
            backgroundMusic.pause();
        }
    }

    public void resumeBackgroundMusic() {
        if (backgroundMusic != null && musicEnabled) {
            backgroundMusic.play();
        }
    }
}
