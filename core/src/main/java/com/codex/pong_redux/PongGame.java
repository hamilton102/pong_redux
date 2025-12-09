package com.codex.pong_redux;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PongGame extends Game {

    public FitViewport viewport;
    public SpriteBatch batch;
    public BitmapFont font;

    @Override
    public void create() {
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

        viewport = new FitViewport(1920, 1080); // choose resolution
        batch = new SpriteBatch();

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        font.getData().setScale(5f);

//	    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/arcade.ttf"));
//	    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//	    parameter.size = 128; // desired pixel size
//	    BitmapFont font = generator.generateFont(parameter);
//	    generator.dispose();

        // Start with the menu
        SettingsScreen settings = new SettingsScreen(this);
        setScreen(new MenuScreen(this, settings));
    }
}
