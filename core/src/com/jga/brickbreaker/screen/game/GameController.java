package com.jga.brickbreaker.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.jga.brickbreaker.config.GameConfig;
import com.jga.brickbreaker.entity.Ball;
import com.jga.brickbreaker.entity.Brick;
import com.jga.brickbreaker.entity.EntityFactory;
import com.jga.brickbreaker.entity.Paddle;
import com.jga.brickbreaker.input.PaddleInputController;

public class GameController {

    // == attributes ==
    private EntityFactory factory;
    private Paddle paddle;
    private PaddleInputController paddleInputController;
    private Array<Brick> bricks = new Array<Brick>();
    private Ball ball;

    private boolean drawGrid = true;

    // == constructor ==
    public GameController() {
        init();
    }

    // == init ==
    private void init(){
        factory = new EntityFactory();
        paddle = factory.createPaddle();
        paddleInputController = new PaddleInputController(paddle);

        bricks.addAll(factory.createBricks());
        ball = factory.createBall();
    }

    // == public methoods =
    public void update(float delta) {
        // handle debug input
        handleDebugInput();

        // handle paddle input
        paddleInputController.update(delta);

        // updet paddle
        paddle.update(delta);

        // blocking paddle from leaving world
        if(paddle.getX() <= 0){
            paddle.setX(0);
        }

        float paddleRightX = paddle.getX() + paddle.getWidth();
        if(paddleRightX >= GameConfig.WORLD_WIDTH){
            paddle.setX(GameConfig.WORLD_WIDTH - paddle.getWidth());
        }

        // updet ball
        ball.update(delta);

        // blocking ball from leaving world
        // bottom
        if (ball.getY() <= 0){
            ball.setY(0);
            ball.multiplyVelocityY(-1);
        }

        // top
        float ballTop = ball.getY() + ball.getHeight();
        if(ballTop >= GameConfig.WORLD_HEIGHT){
            ball.setY(GameConfig.WORLD_HEIGHT - ball.getHeight());
            ball.multiplyVelocityY(-1);
        }

        // left
        if(ball.getX() <= 0){
            ball.setX(0);
            ball.multiplyVelocityX(-1);
        }

        // right
        float ballRight = ball.getX() + ball.getWidth();
        if(ballRight >= GameConfig.WORLD_WIDTH){
            ball.setX(GameConfig.WORLD_WIDTH - ball.getWidth());
            ball.multiplyVelocityX(-1);
        }
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public Array<Brick> getBricks() {
        return bricks;
    }

    public Ball getBall() {
        return ball;
    }

    public boolean isDrawGrid() {
        return drawGrid;
    }

    // == private methods ==
    private void handleDebugInput(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.F5)){
            drawGrid = !drawGrid;
        }
    }
}
