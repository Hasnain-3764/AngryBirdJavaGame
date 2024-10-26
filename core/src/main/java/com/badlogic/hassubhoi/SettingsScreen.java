// File: SettingsScreen.java
package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SettingsScreen extends ScreenAdapter {
    private UIManager uiManager;
    private Stage stage;
    private Skin skin;

    // UI Elements
    private ImageButton soundToggleButton;
    private ImageButton iButton;
    private ImageButton backButton;
//    private SelectBox<String> backgroundSelectBox;

    public SettingsScreen(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.pad(20);

        Texture soundOnTexture = new Texture(Gdx.files.internal("buttonPro/soundOn.png"));
        Texture soundOffTexture = new Texture(Gdx.files.internal("buttonPro/soundOff.png"));

        TextureRegionDrawable soundOnDrawable = new TextureRegionDrawable(soundOnTexture);
        TextureRegionDrawable soundOffDrawable = new TextureRegionDrawable(soundOffTexture);

        // Create a new button style for the toggle button
        ImageButton.ImageButtonStyle toggleStyle = new ImageButton.ImageButtonStyle();
        toggleStyle.up = soundOnDrawable;
        toggleStyle.checked = soundOffDrawable;

        soundToggleButton = new ImageButton(toggleStyle);

        // Set the button to be a toggle button
        soundToggleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean isChecked = soundToggleButton.isChecked();
                SoundManager.getInstance().setMusicEnabled(!isChecked);
                SoundManager.getInstance().setSoundsEnabled(!isChecked);
            }
        });

        Texture iTexture = new Texture(Gdx.files.internal("buttonPro/i.png"));
        iButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(iTexture)));
        iButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){

            }
        });


        // Back Button as ImageButton
        Texture backButtonTexture = new Texture(Gdx.files.internal("buttonPro/backward.png"));
        backButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(backButtonTexture)));

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showMainMenu();
            }
        });

        updateButtonSizes();
        stage.addActor(table);
    }

    private void updateButtonSizes() {
        float buttonSize = Math.min(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()) * 0.15f;
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.pad(20);

        table.add(soundToggleButton).size(buttonSize).padBottom(20);
        table.row();
        table.add(iButton).size(buttonSize).padBottom(20);
        table.row();
        table.add(backButton).size(buttonSize);

        stage.clear(); // Clear previous elements
        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        updateButtonSizes();

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();

    }
}
