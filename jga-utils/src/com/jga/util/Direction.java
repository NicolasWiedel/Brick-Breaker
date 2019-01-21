package com.jga.util;

/**
 * created by Nicolas Wiedel on 21.01.2019
 */
public enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT;

    // == public methods ==
    public boolean isUp() {
        return this == UP;
    }

    public boolean isDown() {
        return this == DOWN;
    }

    public boolean isLeft() {
        return this == LEFT;
    }

    public boolean isRight() {
        return this == RIGHT;
    }
}
