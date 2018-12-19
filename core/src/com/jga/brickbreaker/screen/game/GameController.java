package com.jga.brickbreaker.screen.game;

import com.jga.brickbreaker.config.GameConfig;
import com.jga.brickbreaker.entity.EntityFactory;
import com.jga.brickbreaker.entity.Paddle;
import com.jga.brickbreaker.input.PaddleInputController;

public class GameController {

    // == attributes ==
    private EntityFactory factory;
    private Paddle paddle;
    private PaddleInputController paddleInputController;

    // == constructor ==
    public GameController() {
        init();
    }

    // == init ==
    private void init(){
        factory = new EntityFactory();
        paddle = factory.createPaddle();
        paddleInputController = new PaddleInputController(paddle);
    }

    // == public methoods =
    public void update(float delta) {
        paddleInputController.update(delta);
        paddle.update(delta);

        // blocking paddle from leaving world
        if(paddle.getX() <= 0){
            paddle.setX(0);
        }

        float paddleRightX = paddle.getX() + paddle.getWidth();
        if(paddleRightX >= GameConfig.WORLD_WIDTH){
            paddle.setX(GameConfig.WORLD_WIDTH - paddle.getWidth());
        }
    }

    public Paddle getPaddle() {
        return paddle;
    }
}
