package com.codex.pong_redux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen implements Screen {

    private final PongGame game;
    private final SettingsScreen settings;

    private final Stage stage;
    private final TextField player1Field;
    private final TextField player2Field;


    public MenuScreen(PongGame game, SettingsScreen settings) {
        this.game = game;
        this.settings = settings;

        stage = new Stage(game.viewport);
        Gdx.input.setInputProcessor(stage);

        Skin skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        player1Field = new TextField("Player 1", skin);
        player1Field.setPosition(10, 60);
        player1Field.setSize(200, 40);

        player2Field = new TextField("Player 2", skin);
        player2Field.setPosition(10, 0);
        player2Field.setSize(200, 40);

        stage.addActor(player1Field);
        stage.addActor(player2Field);
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

        // Text fields (HAVE to go after batch.end()
        stage.act(delta);
        stage.draw();
        // mouse definitions
        Vector3 mousePosition = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        game.viewport.getCamera().unproject(mousePosition);
        float mouseX = mousePosition.x;
        float mouseY = mousePosition.y;

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {

            if (mouseX >= startX && mouseX <= startX + startWidth &&
            mouseY <= startY && mouseY >= startY - startHeight) {
                String player1Name = player1Field.getText();
                String player2Name = player2Field.getText();
                Player player1 = new Player("p1", player1Name);
                Player player2 = new Player("P2", player2Name);
                game.setScreen(new GameScreen(game, player1, player2));
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
