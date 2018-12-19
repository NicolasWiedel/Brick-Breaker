package com.jga.brickbreaker.entity;

import com.badlogic.gdx.math.Vector2;
import com.jga.util.entity.EntityBase;

public class Paddle extends EntityBase {

    // == attributes ==
    private Vector2 velocity = new Vector2();

    // == constructor ==
    public Paddle() {
    }

    // public methods ==
    public void update(float delta){
        setX(x + velocity.x * delta);
    }

    public float getVelocityX(){
        return velocity.x;
    }

    public void setVelocityX(float velocityX){
        velocity.x = velocityX;
    }
}