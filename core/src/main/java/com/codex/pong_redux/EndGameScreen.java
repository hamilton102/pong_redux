package com.codex.pong_redux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.ScreenUtils;

public class EndGameScreen implements Screen {
    private final PongGame game;
    private final Player winner;
    private final Player loser;

    public EndGameScreen(PongGame game, Player winner, Player loser) {
        this.game = game;
        this.winner = winner;
        this.loser = loser;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        game.batch.begin();
        game.font.draw(game.batch, "Winner: " + winner.name, 200, 400);
        game.font.draw(game.batch, "Loser: " + loser.name, 200, 350);
        game.font.draw(game.batch, "Press ENTER to return to Menu", 200, 300);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new MenuScreen(game, new SettingsScreen(game)));
        }
    }
    @Override
    public void show() {

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

    @Override
    public void dispose() {

    }
}
