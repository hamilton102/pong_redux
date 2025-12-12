package com.codex.pong_redux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;

public class SettingsScreen implements Screen {

    private final PongGame game;

    private Stage stage;
    private Skin skin;

    private Slider p1Slider, p2Slider;
    private Label p1Label, p2Label;
    
    private final GlyphLayout layout = new GlyphLayout;

    //added separate paddle speeds for players 1 and 2
    public float paddle1Speed = 300;
    public float paddle2Speed = 300;

    public SettingsScreen(PongGame game) {
        this.game = game;
        
        stage = new Stage(game.viewport);
        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        // Sliders
        p1Slider = new Slider(50, 1000, 1, false, skin);
        p2Slider = new Slider(50, 1000, 1, false, skin);

        p1Slider.setValue(paddle1Speed);
        p2Slider.setValue(paddle2Speed);

        p1Label = new Label("Player 1 Speed:", skin);
        p2Label = new Label("Player 2 Speed:", skin);

        p1Slider.addListener(event -> {
            paddle1Speed = p1Slider.getValue();
            return false;
        });

        p2Slider.addListener(event -> {
            paddle2Speed = p2Slider.getValue();
            return false;
        });

        // Layout with Table
        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(20);

        table.add(p1Label).row();
        table.add(p1Slider).width(600).row();

        table.add(p2Label).row();
        table.add(p2Slider).width(600).row();

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.DARK_GRAY);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        String text = "SETTINGS (Press ESC to return)";
        layout.setText(game.font, text);
        game.font.getData().setScale(3f);
        game.font.draw(
            game.batch, 
            text,
            game.viewport.getWorldWidth() / 2f - layout.width / 2f,
            game.viewport.getWorldHeight() / 2f
        );
        
        game.batch.end();

        // Draw UI
        stage.act(delta);
        stage.draw();

        // Return to menu on ESC
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            game.setScreen(new MenuScreen(game, this)); // pass this as SettingsScreen
            dispose();
        }
    }

    @Override
    public void show() {
        // Must give back input to settings stage
        Gdx.input.setInputProcessor(stage);
        
        paddle1Speed = game.settings.paddle1Speed;
        paddle2Speed = game.settings.paddle2Speed;
    }

    @Override public void resize(int width, int height) {
        game.viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    @Override public void hide() {

    }

    @Override public void pause() {

    }

    @Override public void resume() {

    }

    @Override public void dispose() {
        stage.dispose();
        skin.dispose();
    }
}

