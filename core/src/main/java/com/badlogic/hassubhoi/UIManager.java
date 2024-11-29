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


    public void showLastSavedGame() {
        GamePlayScreen savedGameScreen = new GamePlayScreen(game, this);
        if (savedGameScreen.loadGameState()) {
            game.setScreen(savedGameScreen);
        } else {
            // Handle no saved game found (optional)
            System.out.println("No saved game found.");
        }
    }


    public void restartLevel(int level) {
        showLevel(level);
    }


    public void showLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
                game.setScreen(new Level1(game, this));
                break;
            case 2:
                game.setScreen(new Level2(game, this));
                break;
            case 3:
                game.setScreen(new Level3(game, this));
                break;
            default:
                throw new IllegalArgumentException("Invalid level number: " + levelNumber);
        }
    }

    public void showGamePlayScreen(int level) {
        switch (level) {
            case 1:
                game.setScreen(new Level1(game, this));
                break;
            case 2:
                game.setScreen(new Level2(game, this));
                break;
            case 3:
                game.setScreen(new Level3(game, this));
                break;
            // Add cases for additional levels
            default:
                // Handle invalid level numbers
                System.out.println("Invalid level number: " + level);
                break;
        }
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
