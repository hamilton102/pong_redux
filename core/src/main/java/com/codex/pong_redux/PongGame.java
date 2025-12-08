package com.codex.pong_redux;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PongGame extends ApplicationAdapter {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private BitmapFont font;

    // ball ball ball in
    // my granny called she said Travvy you work too hard
    // I'm worried you forget about me
    // ball ball ball
    private float ballX, ballY;
    private float ballVelX, ballVelY;
    private final float ballRadius = 10;

    // paddles
    private float paddle1Y, paddle2Y;
    private final float paddleWidth = 20;
    private final float paddleHeight = 100;
    private final float paddleSpeed = 300; // pixels per second

    // Scoring system
    private int p1score = 0;
    private int p2score = 0;


    @Override
    public void create() {
        // shapes, scoring, bitmap font
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        font = new BitmapFont();

        // ball vars
        ballX = Gdx.graphics.getWidth() / 2f;
        ballY = Gdx.graphics.getHeight() / 2f;
        ballVelX = 200;
        ballVelY = 200;

        // paddle vars
        paddle1Y = Gdx.graphics.getHeight() / 2f - paddleHeight / 2f;
        paddle2Y = Gdx.graphics.getHeight() / 2f - paddleHeight / 2f;

    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ballX += ballVelX * delta;
        ballY += ballVelY * delta;

        // Bounce off top/bottom
        if (ballY - ballRadius < 0 || ballY + ballRadius > Gdx.graphics.getHeight()) {
            ballVelY *= -1;
        }

        // Paddle controls
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.W)) {
            paddle1Y += paddleSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.S)) {
            paddle1Y -= paddleSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.UP)) {
            paddle2Y += paddleSpeed * delta;
        }
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.DOWN)) {
            paddle2Y -= paddleSpeed * delta;
        }

        // paddles stop at top / bottom
        paddle1Y = Math.max(0, Math.min(Gdx.graphics.getHeight() - paddleHeight, paddle1Y));
        paddle2Y = Math.max(0, Math.min(Gdx.graphics.getHeight() - paddleHeight, paddle2Y));

        // paddle collision
        if (ballX - ballRadius < 50 && ballY > paddle1Y && ballY < paddle1Y + paddleHeight) {
            ballVelX *= -1;
            ballX = 50 + ballRadius; // prevent sticking
        }
        if (ballX + ballRadius > Gdx.graphics.getWidth() - 50 &&
            ballY > paddle2Y && ballY < paddle2Y + paddleHeight) {
            ballVelX *= -1;
            ballX = Gdx.graphics.getWidth() - 50 - ballRadius;
        }

        // reset if ball goes past paddles
        if (ballX < 0) {
            p2score++;
            resetBall();
        }
        if (ballX > Gdx.graphics.getWidth()) {
            p1score++;
            resetBall();
        }

        // draw all the stuff
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.circle(ballX, ballY, ballRadius);
        shapeRenderer.rect(30, paddle1Y, paddleWidth, paddleHeight);
        shapeRenderer.rect(Gdx.graphics.getWidth() - 50, paddle2Y, paddleWidth, paddleHeight);
        shapeRenderer.end();

        // display player score
        batch.begin();
        font.draw(batch, "Player 1: " + String.valueOf(p1score), Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() - 20);
        font.draw(batch, "Player 2: " + String.valueOf(p2score), Gdx.graphics.getWidth() * 3f / 4f, Gdx.graphics.getHeight() - 20);
        batch.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        font.dispose();
    }

    // helper function: resets ball to random mid position
    private void resetBall() {
        ballX = Gdx.graphics.getWidth() / 2f;
        ballY = Gdx.graphics.getHeight() / 2f;
        ballVelX = (Math.random() > 0 ? -200 : 200);
        ballVelY = (Math.random() > 0.5 ? 200 : -200);
    }
}
