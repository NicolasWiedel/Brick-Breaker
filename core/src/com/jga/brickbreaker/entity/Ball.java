package com.jga.brickbreaker.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Ball {

    // == attributes ==
    private float x;
    private float y;
    private float size;

    private Vector2 velocity = new Vector2();

    private Circle bounds;

    // == constructor ==
    public Ball() {
        bounds = new Circle(x, y, size / 2);
    }

    // == public methods ==
    public void update(float update){

    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocityXY(float velocityX, float velocityY){
        velocity.set(velocityX, velocityY);
    }

    public void setVelocity(float angleDeg, float value){
        velocity.x = value * MathUtils.cosDeg(angleDeg);
        velocity.y = value * MathUtils.sinDeg(angleDeg);
    }

    public void multiplyVelocityX(float xAmount){
        velocity.x *= xAmount;
    }

    public void multipyVelocityY(float yAmount){
        velocity.y *= yAmount;
    }

    public void setPosition(float x, float y){
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        updateBounds();
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        updateBounds();
    }

    public float getSize() {
        return size;
    }

    public void setSize(float size) {
        this.size = size;
        updateBounds();
    }

    public Circle getBounds() {
        return bounds;
    }



    // == private methods ==
    private void updateBounds(){
        float halfSize = size / 2f;
        bounds.setPosition(x + halfSize, y + halfSize);
        bounds.setRadius(halfSize);
    }
}
