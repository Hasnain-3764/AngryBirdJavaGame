package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class MySkin {
    private Skin skin;

    public MySkin() {
        skin = new Skin();

        // Load BitmapFont from .fnt file
        BitmapFont angryBirdsFont = new BitmapFont(Gdx.files.internal("angryBird.fnt"));
        skin.add("angryBirdsFont", angryBirdsFont);

        // Create LabelStyle using the loaded font
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = angryBirdsFont;
        skin.add("default", labelStyle);

        // Create TextButtonStyle using the loaded font
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = angryBirdsFont;

        // Create simple up and down drawable (for example purposes)
        Texture buttonTextureUp = new Texture(Gdx.files.internal("button_up.png")); // Replace with actual texture
        Texture buttonTextureDown = new Texture(Gdx.files.internal("button_down.png")); // Replace with actual texture
        NinePatchDrawable upDrawable = new NinePatchDrawable(new NinePatch(new TextureRegion(buttonTextureUp)));
        NinePatchDrawable downDrawable = new NinePatchDrawable(new NinePatch(new TextureRegion(buttonTextureDown)));

        // Set the drawables for the TextButton
        textButtonStyle.up = upDrawable;
        textButtonStyle.down = downDrawable;
        textButtonStyle.fontColor = Color.WHITE;
        skin.add("default", textButtonStyle);
    }

    public Skin getSkin() {
        return skin;
    }

    public void dispose() {
        skin.dispose();
    }
}
