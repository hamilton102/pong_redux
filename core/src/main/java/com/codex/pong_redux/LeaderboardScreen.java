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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.List;

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
        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        Table table = new Table();
        table.setFillParent(true);
        table.center();
        stage.addActor(table);

        Label title = new Label("Leaderboard", skin);
        Label subTitle = new Label("Press ESC to return", skin);
        table.add(title).colspan(3).padBottom(20).align(Align.center);
        table.row();
        table.add(subTitle).colspan(3).padBottom(20).align(Align.center);
        table.row();


        table.add(new Label("Player", skin)).pad(10);
        table.add(new Label("Wins", skin)).pad(10);
        table.add(new Label("Losses", skin)).pad(10);
        table.add(new Label("W/L Ratio", skin)).pad(10);
        table.add(new Label("Total Score", skin)).pad(10);
        table.row();

        for (PlayerStats stats : board.getTopPlayers(5)) {
            table.add(new Label(stats.getPlayerId(), skin)).pad(10);
            table.add(new Label(String.valueOf(stats.getWins()), skin)).pad(10);
            table.add(new Label(String.valueOf(stats.getLosses()), skin)).pad(10);
            table.add(new Label(String.valueOf(stats.getWinLossRatio()), skin)).pad(10);
            table.add(new Label(String.valueOf(stats.getTotalScore()), skin)).pad(10);
            table.row();
        }

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            game.setScreen(new MenuScreen(game, game.settings)); // pass this as SettingsScreen
            dispose();
        }
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
