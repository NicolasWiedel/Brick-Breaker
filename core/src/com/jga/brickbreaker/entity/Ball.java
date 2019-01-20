package com.jga.brickbreaker.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jga.shape.ShapeUtils;
import com.jga.util.entity.EntityBase;

public class Ball extends EntityBase {

    // == attributes ==


    // == constructor ==
    public Ball() {

    }

    // == protected methods ==
    @Override
    protected float[] createVertices() {
        return ShapeUtils.createOctagon(
                width / 2, // originX or centerX
                height / 2, // originY or centerY
                width / 2 // radius
        );
    }
}