package com.codex.pong_redux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
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


        // START GAME
        String startText = "Start Game";
        game.font.getData().setScale(4f);

        // set text size
        layout.setText(game.font, startText);

        // center the text
        float startX = worldWidth / 2f - layout.width / 2f;
        float startY = titleY - 150;

        // draw the text
        game.font.draw(game.batch, startText, startX, startY);

        // define collision rectangle
        float startWidth = layout.width;
        float startHeight = layout.height;

        // SETTINGS
        String settingsText = "Settings";

        // set text size
        layout.setText(game.font, settingsText);

        // center text
        float settingsX = worldWidth / 2f - layout.width / 2f;
        float settingsY = startY - 80;

        // draw the text
        game.font.draw(game.batch, settingsText, settingsX, settingsY);

        // define collision rectangle
        float settingsWidth = layout.width;
        float settingsHeight = layout.height;

        game.batch.end();

        // mouse definitions
        Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.viewport.getCamera().unproject(mousePosition);
        float mouseX = mousePosition.x;
        float mouseY = mousePosition.y;

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            if (mouseX >= startX && mouseX <= startX + startWidth &&
            mouseY <= startY && mouseY >= startY - startHeight) {
                System.out.println("Mouse clicked settings button!");
                game.setScreen(new GameScreen(game));
                dispose();
            }

            if (mouseX >= settingsX && mouseX <= settingsX + settingsWidth &&
                mouseY <= settingsY && mouseY >= settingsY - settingsHeight) {
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
