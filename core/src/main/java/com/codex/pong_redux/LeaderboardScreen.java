package com.codex.pong_redux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class LeaderboardScreen implements Screen {
    private final PongGame game;
    private Stage stage;
    private Skin skin;
    private Leaderboard board;

    public LeaderboardScreen(PongGame game, Leaderboard board) {
        this.game = game;
        this.board = board;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(Gdx.files.internal("uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label title = new Label("Leaderboard", skin);
        table.add(title).colspan(3).padBottom(20);
        table.row();

        table.add(new Label("Player", skin)).pad(10);
        table.add(new Label("Wins", skin)).pad(10);
        table.add(new Label("Losses", skin)).pad(10);
        table.row();

        for (PlayerStats stats : board.getAllPlayers().values()) {
            table.add(new Label(stats.getPlayerId(), skin)).pad(10);
            table.add(new Label(String.valueOf(stats.getWins()), skin)).pad(10);
            table.add(new Label(String.valueOf(stats.getLosses()), skin)).pad(10);
            table.row();
        }

        TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // game.setScreen(new MenuScreen(game, game.getSettings()));
            }
        });
        table.add(backButton).colspan(3).padTop(20);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override public void dispose() { stage.dispose(); skin.dispose(); }
}
