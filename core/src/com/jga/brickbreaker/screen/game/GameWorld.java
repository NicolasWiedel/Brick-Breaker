package com.jga.brickbreaker.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jga.brickbreaker.common.ScoreController;
import com.jga.brickbreaker.common.SoundController;
import com.jga.brickbreaker.config.GameConfig;
import com.jga.brickbreaker.entity.Ball;
import com.jga.brickbreaker.entity.Brick;
import com.jga.brickbreaker.entity.EntityFactory;
import com.jga.brickbreaker.entity.Paddle;
import com.jga.brickbreaker.entity.Pickup;
import com.jga.brickbreaker.input.PaddleInputController;
import com.jga.brickbreaker.script.BallSlowDownScript;
import com.jga.brickbreaker.script.BallSpeedUpScript;
import com.jga.brickbreaker.script.PaddleExpandScript;
import com.jga.brickbreaker.script.PaddleShrinkScript;
import com.jga.shape.RectangelUtils;
import com.jga.util.paralax.ParallaxLayer;

public class GameWorld {

    // == attributes ==
    private final SoundController soundController;
    private final ScoreController scoreController;
    private final EntityFactory factory;

    private Paddle paddle;
    private Array<Brick> bricks = new Array<Brick>();
    private Ball ball;
    private ParallaxLayer background;

    private boolean drawGrid = true;
    private boolean drawDebug = true;

    private Array<ParticleEffectPool.PooledEffect> effects = new Array<ParticleEffectPool.PooledEffect>();
    private Array<Pickup> pickups = new Array<Pickup>();

    private int lives = GameConfig.LIVES_START;

    // == constructor ==
    public GameWorld(SoundController soundController,
                     ScoreController scoreController,
                     EntityFactory factory) {

        this.soundController = soundController;
        this.scoreController = scoreController;
        this.factory = factory;
        init();
    }

    // == init ==
    private void init(){
        background = factory.createBackground();
        paddle = factory.createPaddle();
        ball = factory.createBall();
        startLevel();
    }

    // == public methoods =
    public void update(float delta) {
        background.update(delta);

        if(ball.isNotActive()){
            return;
        }

        // update paddle
        paddle.update(delta);

        // blocking paddle from leaving world
        blockPaddleFromLeavingWorld();


        // update ball
        ball.update(delta);

        // blocking ball from leaving world
        blockBallFromLeavingWorld();

        // update pickups
        updatePickups(delta);

        // update effects
        updateEffects(delta);

        checkCollision();

        if(bricks.size == 0){
            startLevel();
        }
    }

    public ParallaxLayer getBackground() {
        return background;
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

    public Array<Pickup> getPickups() {
        return pickups;
    }

    public Array<ParticleEffectPool.PooledEffect> getEffects() {
        return effects;
    }

    public String getScoreString(){
        return scoreController.getScoreScring();
    }

    public boolean isDrawGrid() {
        return drawGrid;
    }

    public boolean isDrawDebug() {
        return drawDebug;
    }

    public void activateBall(){
        ball.setVelocity(GameConfig.BALL_START_ANGLE, GameConfig.BALL_START_SPEED);
    }

    public void toggleDrawGrid(){
        drawGrid = !drawGrid;
    }

    public void toggleDebug(){
        drawDebug = !drawDebug;
    }

    public boolean isGameOver(){
        return lives <= 0;
    }

    public int getLives() {
        return lives;
    }

    // == private methods ==
    private void blockPaddleFromLeavingWorld() {
        // block left
        if(paddle.getX() <= 0){
            paddle.setX(0);
        }

        // block right
        float paddleRightX = paddle.getX() + paddle.getWidth();

        if(paddleRightX >= GameConfig.WORLD_WIDTH){
            paddle.setX(GameConfig.WORLD_WIDTH - paddle.getWidth());
        }
    }

    private void blockBallFromLeavingWorld() {
        // bottom
        if (ball.getY() <= 0){
            soundController.lost();
            lives--;
            restart();
//            ball.setY(0);
//            ball.multiplyVelocityY(-1);
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

    private void updateEffects(float delta) {
        for( int i = 0; i < effects.size; i++){
            ParticleEffectPool.PooledEffect effect = effects.get(i);
            effect.update(delta);

            if(effect.isComplete()){
                effects.removeIndex(i);
                effect.free();
            }
        }
    }

    private void updatePickups(float delta) {
        for(int i = 0; i < pickups.size; i++){
            Pickup pickup = pickups.get(i);
            pickup.update(delta);

            if(pickup.getY() < -pickup.getHeight()){
                factory.freePickup(pickup);
                pickups.removeIndex(i);
            }
        }
    }

    private void checkCollision(){
        checkBallWithPaddleCollision();
        checkBallWithBrickCollision();
        checkPaddleWithPickupCollision();
    }

    private void checkBallWithPaddleCollision(){
        Polygon ballBounds = ball.getBounds();
        Polygon paddleBounds = paddle.getBounds();

        if(Intersector.overlapConvexPolygons(ballBounds, paddleBounds)){
            soundController.hit();
            float ballCenterX = ball.getX() + ball.getWidth() / 2f;
            float percent =(ballCenterX - paddle.getX()) / paddle.getWidth(); // 0f - 1f
            // interpolate angle between 150 and 30
            float bounceAngle = 150 - percent * 120;
            ball.setVelocity(bounceAngle, ball.getSpeed());
        }
    }

    private void checkBallWithBrickCollision(){
        Polygon ballPolygon = ball.getBounds();
        float ballRadius = ball.getWidth() / 2f;
        Circle ballBounds = new Circle(
                ball.getX() + ballRadius,
                ball.getY() + ballRadius,
                ballRadius
        );


        for(int i = 0; i < bricks.size; i++){
            Brick brick = bricks.get(i);
            Polygon brickPolygon = brick.getBounds();
            Rectangle brickBounds = brickPolygon.getBoundingRectangle();

            if(!Intersector.overlapConvexPolygons(ballPolygon, brickPolygon)){
                continue;
            }

            soundController.hit();

            // check wich side of brick is overlapping width ball
            Vector2 bottomLeft = RectangelUtils.getBottomLeft(brickBounds);
            Vector2 bottomRight = RectangelUtils.getBottomRight(brickBounds);
            Vector2 topLeft = RectangelUtils.getTopLeft(brickBounds);
            Vector2 topRight = RectangelUtils.getTopRight(brickBounds);

            Vector2 center = new Vector2(ballBounds.x, ballBounds.y);
            float squareRadius = ballBounds.radius * ballBounds.radius;


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

            // create fire effect
            float effectX = brick.getX() + brick.getWidth() / 2f;
            float y = brick.getY() + brick.getHeight() / 2f;

            spawnFireEffect(effectX, y);

//            if(MathUtils.random() < 0.20f){
                float pickupX = brick.getX() + (brick.getWidth() - GameConfig.PICKUP_SIZE) / 2f;
                spawnPickup(pickupX, y);
//            }

            // remove brick
            bricks.removeIndex(i);

            // add score
            scoreController.addScore(GameConfig.BRICK_SCORE);
            System.out.println("currentScore = " + scoreController.getScoreScring());
        }
    }

    private void checkPaddleWithPickupCollision(){
        Polygon paddleBounds = paddle.getBounds();

        for(int i = 0; i < pickups.size; i++){
            Pickup pickup = pickups.get(i);
            Polygon pickupBounds = pickup.getBounds();

            if(Intersector.overlapConvexPolygons(paddleBounds, pickupBounds)){
                soundController.pickup();
                float x = pickup.getX() + pickup.getWidth() / 2;
                float y = pickup.getY();

                addScript(pickup);
                spawnStarEffect(x, y);
                pickups.removeIndex(i);
                factory.freePickup(pickup);
            }
        }
    }

    private void startLevel(){
        restart();
        bricks.addAll(factory.createBricks());
    }

    private void restart() {
        for (int i = 0; i < pickups.size; i++){
            Pickup pickup = pickups.get(i);
            factory.freePickup(pickup);
            pickups.removeIndex(i);
        }

        for (int i =0; i < effects.size; i++){
            ParticleEffectPool.PooledEffect effect = effects.get(i);
            effect.free();
            effects.removeIndex(i);
        }
        paddle.setPosition(GameConfig.PADDLE_START_X, GameConfig.PADDLE_START_Y);
        ball.setPosition(GameConfig.BALL_START_X, GameConfig.BALL_START_Y);
        ball.stop();
    }

    private void spawnFireEffect(float x, float y){
        ParticleEffectPool.PooledEffect effeect = factory.createFire(x, y);
        effects.add(effeect);
    }

    private void spawnStarEffect(float x, float y){
        ParticleEffectPool.PooledEffect effect = factory.createStar(x, y);
        effects.add(effect);
    }

    private void spawnPickup(float x, float y){
        Pickup pickup = factory.createPickup(x, y);
        pickups.add(pickup);
    }

    private void addScript(Pickup pickup){
        if(pickup.isExpand()){
            paddle.addScript(new PaddleExpandScript());
        }else if(pickup.isShrink()){
            paddle.addScript(new PaddleShrinkScript());
        }else if(pickup.isSpeedUp()){
            ball.addScript(new BallSpeedUpScript());
        }else if (pickup.isSlowDown()){
            ball.addScript(new BallSlowDownScript());
        }
    }
}
