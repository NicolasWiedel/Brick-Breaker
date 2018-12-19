package com.jga.brickbreaker.config;

public final class GameConfig {

    // == constants ==
    // only for desktop
    public static final int WIDTH = 960;   // pixels
    public static final int HEIGHT = 768;  // pixels

    public static final float WORLD_WIDTH = 30f;   // world units
    public static final float WORLD_HEIGHT = 24f;  // world units

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f;  // world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f; // world units

    public static final float PADDLE_WIDTH = 3f;   // world units
    public static final float PADDLE_HEIGHT = 1f;  // world units

    public static final float PADDLE_START_X = (WORLD_WIDTH - PADDLE_WIDTH) / 2f;  // world units
    public static final float PADDLE_START_Y = 1f;  // world units

    public static final float PADDLE_VELOCITY_X = 15f;

    // == constructor ==
    private GameConfig(){

    }
}
