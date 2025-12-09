package com.codex.pong_redux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuScreen implements Screen {

    private final PongGame game;
    private final SettingsScreen settings;

    public MenuScreen(PongGame game, SettingsScreen settings) {
        this.game = game;
        this.settings = settings;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        float worldWidth = game.viewport.getWorldWidth();
        float worldHeight = game.viewport.getWorldHeight();

        game.batch.begin();

        GlyphLayout layout = new GlyphLayout();

        String title = "PONG";
        game.font.getData().setScale(6f);
        layout.setText(game.font, title);
        float titleX = worldWidth / 2f - layout.width / 2f;
        float titleY = worldHeight * 2f / 3f + layout.height / 2f;
        game.font.draw(game.batch, title, titleX, titleY);

        
        String startText = "Start Game";
        game.font.getData().setScale(4f);
        layout.setText(game.font, startText);
        float startX = worldWidth / 2f - layout.width / 2f;
        float startY = titleY - 150;
        game.font.draw(game.batch, startText, startX, startY);

        String settingsText = "Settings";
        layout.setText(game.font, settingsText);
        float settingsX = worldWidth / 2f - layout.width / 2f;
        float settingsY = startY - 80;
        game.font.draw(game.batch, settingsText, settingsX, settingsY);

        game.batch.end();

        if (Gdx.input.justTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = worldHeight - Gdx.input.getY();

            if (touchX > startX && touchX < startX + layout.width &&
                touchY > startY - layout.height && touchY < startY) {
                game.setScreen(new GameScreen(game));
                dispose();
            }

            if (touchX > settingsX && touchX < settingsX + layout.width &&
                touchY > settingsY - layout.height && touchY < settingsY) {
                game.setScreen(settings);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override public void show() {

    }

    @Override public void hide() {

    }

    @Override public void pause() {

    }

    @Override public void resume() {

    }

    @Override public void dispose() {

    }
}
