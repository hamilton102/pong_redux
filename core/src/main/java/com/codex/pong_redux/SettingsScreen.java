package com.codex.pong_redux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

public class SettingsScreen implements Screen {

    private final PongGame game;
    private GlyphLayout layout;

    public SettingsScreen(PongGame game) {
        this.game = game;
        layout = new GlyphLayout();
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
        game.font.draw(game.batch, text,
            game.viewport.getWorldWidth() / 2f - layout.width / 2f,
            game.viewport.getWorldHeight() / 2f);
        game.batch.end();

        // Return to menu on ESC
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            game.setScreen(new MenuScreen(game, this)); // pass this as SettingsScreen
            dispose();
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
