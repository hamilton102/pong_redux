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
    private final int WIN_INT = 5;

    // Ball
    private float ballX, ballY;
    private float ballVelX, ballVelY;
    private final float ballRadius = 10;
    private final int BALL_SPEED = 400;

    // Paddles
    private float paddle1Y, paddle2Y, paddle1X, paddle2X;
    private final float paddleWidth = 20;
    private final float paddleHeight = 100;
    private final float paddleSpeed = 300; // fixed speed

    // players
    private final Player player1;
    private final Player player2;
    private int p1score = 0;
    private int p2score = 0;

    public GameScreen(PongGame game, Player player1, Player player2) {
        this.game = game;
        this.player1 = player1;
        this.player2 = player2;
    }

    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        pauseLayout = new GlyphLayout();

        // Initialize ball
        ballX = game.viewport.getWorldWidth() / 2f;
        ballY = game.viewport.getWorldHeight() / 2f;
        ballVelX = BALL_SPEED;
        ballVelY = BALL_SPEED;

        // Initialize paddles
        paddle1Y = game.viewport.getWorldHeight() / 2f - paddleHeight / 2f;
        paddle2Y = game.viewport.getWorldHeight() / 2f - paddleHeight / 2f;
        paddle1X = 30;
        paddle2X = game.viewport.getWorldWidth();
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
            if (Gdx.input.isKeyPressed(Input.Keys.D)) paddle1X += paddleSpeed * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.A)) paddle1X -= paddleSpeed * delta;

            if (Gdx.input.isKeyPressed(Input.Keys.UP)) paddle2Y += paddleSpeed * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) paddle2Y -= paddleSpeed * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) paddle2X += paddleSpeed * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) paddle2X -= paddleSpeed * delta;

            // Keep paddles in screen
            paddle1X = Math.max(0, Math.min((game.viewport.getWorldWidth() / 2) - paddleWidth, paddle1X));
            paddle2X = Math.max(game.viewport.getWorldWidth() / 2, Math.min(game.viewport.getWorldWidth() - paddleWidth, paddle2X));

            paddle1Y = Math.max(0, Math.min(game.viewport.getWorldHeight() - paddleHeight, paddle1Y));
            paddle2Y = Math.max(0, Math.min(game.viewport.getWorldHeight() - paddleHeight, paddle2Y));

            // Paddle 1 collision
            if (ballX - ballRadius < paddle1X + paddleWidth &&
                ballX > paddle1X &&
                ballY > paddle1Y && ballY < paddle1Y + paddleHeight) {
                ballVelX *= -1;
                ballX = paddle1X + paddleWidth + ballRadius;
            }

// Paddle 2 collision
            if (ballX + ballRadius > paddle2X &&
                ballX < paddle2X + paddleWidth &&
                ballY > paddle2Y && ballY < paddle2Y + paddleHeight) {
                ballVelX *= -1;
                ballX = paddle2X - ballRadius;
            }

            System.out.println("P1X=" + paddle1X + " P2X=" + paddle2X + " BallX=" + ballX);

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
        shapeRenderer.rect(paddle1X, paddle1Y, paddleWidth, paddleHeight);
        shapeRenderer.rect(paddle2X, paddle2Y, paddleWidth, paddleHeight);
        shapeRenderer.end();

        // Draw score & pause
        game.batch.begin();
        game.font.draw(game.batch, player1.name + ": " +p1score, game.viewport.getWorldWidth() / 4f, game.viewport.getWorldHeight() - 20);
        game.font.draw(game.batch, player2.name + ": " + p2score, game.viewport.getWorldWidth() * 3f / 4f, game.viewport.getWorldHeight() - 20);

        if (paused) {
            String pauseText = "PAUSED";
            pauseLayout.setText(game.font, pauseText);
            game.font.draw(game.batch, pauseText,
                game.viewport.getWorldWidth() / 2f - pauseLayout.width / 2f,
                game.viewport.getWorldHeight() / 2f + pauseLayout.height / 2f);
        }

        checkIfOver();

        game.batch.end();
    }

    private void resetBall() {
        ballX = game.viewport.getWorldWidth() / 2f;
        ballY = game.viewport.getWorldHeight() / 2f;
        ballVelX = (Math.random() > 0.5 ? BALL_SPEED : -BALL_SPEED);
        ballVelY = (Math.random() > 0.5 ? BALL_SPEED : -BALL_SPEED);
    }

    private void checkIfOver() {
        if (p1score >= WIN_INT) endGame(player1, player2, p2score);
        else if (p2score >= WIN_INT) endGame(player2, player1, p2score);
    }

    public void endGame(Player winner, Player loser, int loserScore) {
        game.leaderboard.updateWin(winner.id, WIN_INT);
        game.leaderboard.updateLoss(loser.id, loserScore);
        game.setScreen(new EndGameScreen(game, winner, loser));
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
