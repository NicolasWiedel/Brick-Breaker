package com.jga.brickbreaker.script;

import com.jga.brickbreaker.config.GameConfig;
import com.jga.brickbreaker.entity.Ball;
import com.jga.util.entity.script.EntityScriptBase;

/**
 * created by Nicolas Wiedel on 20.01.2019
 */
public class BallSpeedUpScript extends EntityScriptBase<Ball> {

    // == attributes
    private float finalSpeed;

    // == public methods ==

    @Override
    public void added(Ball entity) {
        super.added(entity);

        float currentSpeed = entity.getSpeed();
        finalSpeed = currentSpeed + GameConfig.BALL_START_SPEED * GameConfig.BALL_SPEED_FACTOR;

        if(finalSpeed <= GameConfig.BALL_MAX_SPEED){
            finalSpeed = GameConfig.BALL_MAX_SPEED;
        }
    }

    @Override
    public void update(float delta) {
        float angleDeg = entity.getAngleDeg();
        entity.setVelocity(angleDeg, finalSpeed);
        finish();
    }
}
