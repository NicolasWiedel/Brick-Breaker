package com.jga.brickbreaker.screen.game;

import com.jga.brickbreaker.config.GameConfig;
import com.jga.brickbreaker.entity.EntityFactory;
import com.jga.brickbreaker.entity.Paddle;

public class GameController {

    // == attributes ==
    private EntityFactory factory;
    private Paddle paddle;



    // == constructor ==
    public GameController() {
        init();
    }

    // == init ==
    private void init(){
        factory = new EntityFactory();
        paddle = factory.createPaddle();
    }

    // == public methoods =
    public void update(float delta) {
    }

    public Paddle getPaddle() {
        return paddle;
    }
}
