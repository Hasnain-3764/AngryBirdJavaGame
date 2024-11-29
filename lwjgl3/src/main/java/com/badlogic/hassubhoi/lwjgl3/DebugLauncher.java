package com.badlogic.hassubhoi.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.hassubhoi.*;
//import com.badlogic.hassubhoi;


/** Launches the desktop (LWJGL3) application for debugging purposes. */

// sets splashscreen
//
//public class DebugLauncher {
//    private AngryBirdGames game;
//    public static void main (String[] arg) {
//        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//        config.setTitle("AngryBirdGames - Debug");
//        config.setWindowedMode(800, 480);
//        config.useVsync(true);
//        config.setForegroundFPS(60);
//
//        new Lwjgl3Application(new AngryBirdGames() {
//            @Override
//            public void create() {
//                this.setScreen(new SplashScreen(this));
//
//                // or start with any other screen for debugging
//                // setScreen(new SettingsScreen(this.getUIManager()));
//            }
//        }, config);
//    }
//}

//// sets mainmenuscreen

//public class DebugLauncher {
//    private AngryBirdGames game;
//    public static void main (String[] arg) {
//        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//        config.setTitle("AngryBirdGames - Debug");
//        config.setWindowedMode(800, 480);
//        config.useVsync(true);
//        config.setForegroundFPS(60);
//
//        new Lwjgl3Application(new AngryBirdGames() {
//            @Override
//            public void create() {
//                this.setScreen(new MainMenuScreen(getUIManager()));
//
//                // setScreen(new SettingsScreen(this.getUIManager()));
//            }
//        }, config);
//    }
//}


// settings screen

//public class DebugLauncher {
//    private AngryBirdGames game;
//    public static void main (String[] arg) {
//        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//        config.setTitle("AngryBirdGames - Debug");
//        config.setWindowedMode(800, 480);
//        config.useVsync(true);
//        config.setForegroundFPS(60);
//
//        new Lwjgl3Application(new AngryBirdGames() {
//            @Override
//            public void create() {
//
//                 setScreen(new SettingsScreen(this.getUIManager()));
//            }
//        }, config);
//    }
//}


//// levelx screen

// level select screen launcher

//public class DebugLauncher {
//    static AngryBirdGames game;
//    public DebugLauncher(){
//        this.game = game;
//    }
//    public static void main (String[] arg) {
//        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//        config.setTitle("AngryBirdGames - Debug");
//        config.setWindowedMode(800, 480);
//        config.useVsync(true);
//        config.setForegroundFPS(60);
//
//        new Lwjgl3Application(new AngryBirdGames() {
//            @Override
//            public void create() {
//                // Initialize the UIManager
//                UIManager uiManager = new UIManager(game);
//
//                setScreen(new LevelSelectScreen(uiManager));
//            }
//        }, config);
//    }
//}

// LevelCompleteScreen

//public class DebugLauncher {
//    static AngryBirdGames game;
//    public DebugLauncher(){
//        this.game = game;
//    }
//    public static void main (String[] arg) {
//        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//        config.setTitle("AngryBirdGames - Debug");
//        config.setWindowedMode(800, 480);
//        config.useVsync(true);
//        config.setForegroundFPS(60);
//
//        new Lwjgl3Application(new AngryBirdGames() {
//            @Override
//            public void create() {
//                // Initialize the UIManager
//                UIManager uiManager = new UIManager(game);
//
//                setScreen(new LevelCompleteScreen(uiManager,2));
//            }
//        }, config);
//    }
//}

    // gameplayscreen
//public class DebugLauncher {
//    static AngryBirdGames game;
//    public DebugLauncher(){
//        this.game = game;
//    }
//    public static void main (String[] arg) {
//        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
//        config.setTitle("AngryBirdGames - Debug");
//        config.setWindowedMode(800, 480);
//        config.useVsync(true);
//        config.setForegroundFPS(60);
//
//        new Lwjgl3Application(new AngryBirdGames() {
//            @Override
//            public void create() {
//                // Initialize the UIManager
//                UIManager uiManager = new UIManager(game);
//                setScreen(new GamePlayScreen(game, uiManager));
//            }
//        }, config);
//    }
//}
//
