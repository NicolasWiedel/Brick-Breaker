package com.jga.brickbreaker.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.jga.brickbreaker.config.GameConfig;
import com.jga.brickbreaker.entity.Paddle;
import com.jga.brickbreaker.screen.game.GameController;

public class PaddleInputController {

    // == attributes ==
    private final Paddle paddle;
    private final GameController controller;

    // == constructor ==

    public PaddleInputController(Paddle paddle, GameController controller) {
        this.paddle = paddle;
        this.controller = controller;
    }

    // == public methods ==
    public void update(float delta){
        float velocityX = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            velocityX = -GameConfig.PADDLE_VELOCITY_X;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            velocityX = GameConfig.PADDLE_VELOCITY_X;
        }

        paddle.setVelocityX(velocityX);
    }
}
