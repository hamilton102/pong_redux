package com.codex.pong_redux;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen {

    private ShapeRenderer shapeRenderer;
    private final PongGame game;
    private boolean paused = false;
    private GlyphLayout pauseLayout;

    // Ball
    private float ballX, ballY;
    private float ballVelX, ballVelY;
    private final float ballRadius = 10;

    // Paddles
    private float paddle1Y, paddle2Y;
    private final float paddleWidth = 20;
    private final float paddleHeight = 100;
    private final float paddleSpeed = 300; // fixed speed

    // Scores
    private int p1score = 0;
    private int p2score = 0;

    public GameScreen(PongGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        pauseLayout = new GlyphLayout();

        // Initialize ball
        ballX = game.viewport.getWorldWidth() / 2f;
        ballY = game.viewport.getWorldHeight() / 2f;
        ballVelX = 200;
        ballVelY = 200;

        // Initialize paddles
        paddle1Y = game.viewport.getWorldHeight() / 2f - paddleHeight / 2f;
        paddle2Y = game.viewport.getWorldHeight() / 2f - paddleHeight / 2f;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        game.viewport.apply();
        shapeRenderer.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        // Pause toggle
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) paused = !paused;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();

        if (!paused) {
            ballX += ballVelX * delta;
            ballY += ballVelY * delta;

            // Bounce off top/bottom
            if (ballY - ballRadius < 0 || ballY + ballRadius > game.viewport.getWorldHeight()) ballVelY *= -1;

            // Paddle controls
            if (Gdx.input.isKeyPressed(Input.Keys.W)) paddle1Y += paddleSpeed * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.S)) paddle1Y -= paddleSpeed * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) paddle2Y += paddleSpeed * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) paddle2Y -= paddleSpeed * delta;

            // Keep paddles in screen
            paddle1Y = Math.max(0, Math.min(game.viewport.getWorldHeight() - paddleHeight, paddle1Y));
            paddle2Y = Math.max(0, Math.min(game.viewport.getWorldHeight() - paddleHeight, paddle2Y));

            // Paddle collision
            if (ballX - ballRadius < 50 && ballY > paddle1Y && ballY < paddle1Y + paddleHeight) {
                ballVelX *= -1;
                ballX = 50 + ballRadius;
            }
            if (ballX + ballRadius > game.viewport.getWorldWidth() - 50 &&
                ballY > paddle2Y && ballY < paddle2Y + paddleHeight) {
                ballVelX *= -1;
                ballX = game.viewport.getWorldWidth() - 50 - ballRadius;
            }

            // Score reset
            if (ballX < 0) {
                p2score++;
                resetBall();
            }
            if (ballX > game.viewport.getWorldWidth()) {
                p1score++;
                resetBall();
            }
        }

        // Draw game
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(ballX, ballY, ballRadius);
        shapeRenderer.rect(30, paddle1Y, paddleWidth, paddleHeight);
        shapeRenderer.rect(game.viewport.getWorldWidth() - 50, paddle2Y, paddleWidth, paddleHeight);
        shapeRenderer.end();

        // Draw score & pause
        game.batch.begin();
        game.font.draw(game.batch, "Player 1: " + p1score, game.viewport.getWorldWidth() / 4f, game.viewport.getWorldHeight() - 20);
        game.font.draw(game.batch, "Player 2: " + p2score, game.viewport.getWorldWidth() * 3f / 4f, game.viewport.getWorldHeight() - 20);

        if (paused) {
            String pauseText = "PAUSED";
            pauseLayout.setText(game.font, pauseText);
            game.font.draw(game.batch, pauseText,
                game.viewport.getWorldWidth() / 2f - pauseLayout.width / 2f,
                game.viewport.getWorldHeight() / 2f + pauseLayout.height / 2f);
        }

        game.batch.end();
    }

    private void resetBall() {
        ballX = game.viewport.getWorldWidth() / 2f;
        ballY = game.viewport.getWorldHeight() / 2f;
        ballVelX = (Math.random() > 0.5 ? 200 : -200);
        ballVelY = (Math.random() > 0.5 ? 200 : -200);
    }

    @Override public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override public void pause() {

    }

    @Override public void resume() {

    }

    @Override public void hide() {

    }

    @Override public void dispose() {
        if (shapeRenderer != null) shapeRenderer.dispose();
    }
}
