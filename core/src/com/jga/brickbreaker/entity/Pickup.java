package com.jga.brickbreaker.entity;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.jga.util.entity.EntityBase;

public class Pickup extends EntityBase implements Pool.Poolable{

    // == attributes ==
    private PickupType type;
    private Vector2 velocity = new Vector2();

    // == constructor ==
    public Pickup(){
        type = PickupType.random();
    }

    // == public methods ==
    public void update(float delta){
        setY(y + velocity.y * delta);
    }

    public void setType(PickupType type){
        this.type = type;
    }

    public void setVelocityY(float velocityY){
        velocity.y = velocityY;
    }

    public boolean isExpand(){
        return type.isExpand();
    }
    public boolean isShrink(){
        return type.isShrink();
    }
    public boolean isSlowDown(){
        return type.isSlowDown();
    }
    public boolean isSpeedUp(){
        return type.isSpeedUp();
    }

    public PickupType getType() {
        return type;
    }
    
    @Override
    public void reset() {
        type = null;
        velocity.setZero();
    }

}
