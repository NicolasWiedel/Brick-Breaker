package com.jga.brickbreaker.script;

import com.jga.brickbreaker.config.GameConfig;
import com.jga.brickbreaker.entity.Paddle;
import com.jga.util.entity.script.EntityScriptBase;

/**
 * created by Nicolas Wiedel on 20.01.2019
 */
public class PaddleExpandScript extends EntityScriptBase<Paddle> {

    // == attributes ==
    private float finalWidth;
    private boolean shouldFinish;

    // == public methods ==
    @Override
    public void added(Paddle entity) {
        super.added(entity);

        float currentWidth = entity.getWidth();
        finalWidth = currentWidth + GameConfig.PADDLE_START_WIDTH * GameConfig.PADDLE_RESIZE_FACTOR;

        if(finalWidth > GameConfig.PADDLE_MAX_WIDTH){
            finalWidth = GameConfig.PADDLE_MAX_WIDTH;
        }
    }

    @Override
    public void update(float delta) {
        if(isFinished()){
            return;
        }

        float currentWidth = entity.getWidth();
        float newWidth = currentWidth + GameConfig.PADDLE_EXPAND_SHRINK_SPEED * delta;

        if(newWidth >= finalWidth){
            newWidth = finalWidth;
            shouldFinish = true;
        }

        entity.setWidth(newWidth);

        if(shouldFinish){
            finish();
        }
    }
}
