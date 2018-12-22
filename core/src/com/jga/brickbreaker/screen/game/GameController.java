package com.jga.brickbreaker.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jga.brickbreaker.config.GameConfig;
import com.jga.brickbreaker.entity.Ball;
import com.jga.brickbreaker.entity.Brick;
import com.jga.brickbreaker.entity.EntityFactory;
import com.jga.brickbreaker.entity.Paddle;
import com.jga.brickbreaker.input.PaddleInputController;
import com.jga.shape.RectangelUtils;

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

        checkCollision();
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

    private void checkCollision(){
        checkBallWithPaddleCollision();
        checkBallWithBrickCollision();
    }

    private void checkBallWithPaddleCollision(){
        Circle ballBounds = ball.getBounds();
        Rectangle paddleBounds = paddle.getBounds();

        if(Intersector.overlaps(ballBounds, paddleBounds)){
            float ballCenterX = ball.getX() + ball.getWidth() / 2f;
            float percent =(ballCenterX - paddle.getX()) / paddle.getWidth(); // 0f - 1f
            // interpolate angle between 150 and 30
            float bounceAngle = 150 - percent * 120;
            ball.setVelocity(bounceAngle, ball.getSpeed());
        }
    }

    private void checkBallWithBrickCollision(){
        Circle ballBounds = ball.getBounds();

        for(int i = 0; i < bricks.size; i++){
            Brick brick = bricks.get(i);
            Rectangle brickBounds = brick.getBounds();

            if(!Intersector.overlaps(ballBounds, brickBounds)){
                continue;
            }

            Vector2 center = new Vector2(ballBounds.x, ballBounds.y);
            float squareRadius = ballBounds.radius * ballBounds.radius;

            Vector2 bottomLeft = RectangelUtils.getBottomLeft(brickBounds);
            Vector2 bottomRight = RectangelUtils.getBottomRight(brickBounds);
            Vector2 topLeft = RectangelUtils.getTopLeft(brickBounds);
            Vector2 topRight = RectangelUtils.getTopRight(brickBounds);

            boolean bottomHit = Intersector.intersectSegmentCircle(
                  bottomLeft, bottomRight, center, squareRadius
            );
            boolean topHit = Intersector.intersectSegmentCircle(
                    topLeft, topRight, center, squareRadius
            );
            boolean leftHit = Intersector.intersectSegmentCircle(
                    bottomLeft, topLeft, center, squareRadius
            );
            boolean rightHit = Intersector.intersectSegmentCircle(
                    bottomRight, topRight, center, squareRadius
            );

            // left - right
            if(ball.getVelocity().x > 0 && leftHit){
                ball.multiplyVelocityX(-1);
            }
            else if(ball.getVelocity().x < 0 && rightHit){
                ball.multiplyVelocityX(-1);
            }

            // top - bottom
            if(ball.getVelocity().y > 0 && bottomHit){
                ball.multiplyVelocityY(-1);
            }
            else if(ball.getVelocity().y < 0 && topHit){
                ball.multiplyVelocityY(-1);
            }

            bricks.removeIndex(i);
        }
    }
}
