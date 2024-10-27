package com.badlogic.hassubhoi;

public class UIManager {
    private AngryBirdGames game;
    private String selectedBackground;

    public UIManager(AngryBirdGames game) {
        this.game = game;
        this.selectedBackground = "Level2.png"; // default background

    }

    public void showMainMenu() {
        game.setScreen(new MainMenuScreen(this));
    }

    public void showSplashScreen() {
        game.setScreen(new SplashScreen(game));
    }

    public void showGamePlayScreen(int level) {
        game.setScreen(new GamePlayScreen(this, level));
    }

    public void showLevelSelectScreen() {
        game.setScreen(new LevelSelectScreen(this));
    }

    public void showLevelCompleteScreen(int level) {
        game.setScreen(new LevelCompleteScreen(this, level));
    }

    public void showGameOverScreen(int level) {
        game.setScreen(new GameOverScreen(this, level));
    }

    public void showSettingsScreen() {
        game.setScreen(new SettingsScreen(this));
    }

    public String getSelectedBackground() {
        return selectedBackground;
    }

    public void setSelectedBackground(String background) {
        this.selectedBackground = background;
    }
}
