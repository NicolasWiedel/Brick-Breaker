package com.jga.brickbreaker.config;

public final class GameConfig {

    // == constants ==
    // only for desktop
    public static final int WIDTH = 1024;   // pixels
    public static final int HEIGHT = 768;  // pixels

    public static final float HUD_WIDTH = 1024f;   // world units
    public static final float HUD_HEIGHT = 768f;   // world units

    public static final float WORLD_WIDTH = 32f;   // world units
    public static final float WORLD_HEIGHT = 24f;  // world units

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f;  // world units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f; // world units

    public static final float PADDLE_MIN_WIDTH = 1.2f;
    public static final float PADDLE_START_WIDTH = 3f;   // world units
    public static final float PADDLE_MAX_WIDTH = 4.8f;
    public static final float PADDLE_HEIGHT = 1f;  // world units
    public static final float PADDLE_START_X = (WORLD_WIDTH - PADDLE_START_WIDTH) / 2f;  // world units
    public static final float PADDLE_START_Y = 1f;  // world units
    public static final float PADDLE_VELOCITY_X = 15f;   // world units
    public static final float PADDLE_RESIZE_FACTOR = 0.15f;

    public static final float BRICK_WIDTH = 2.125f;   // world units
    public static final float BRICK_HEIGHT = 1f;    // world units

    public static final float LEFT_PAD = 0.5f;   // world units
    public static final float TOP_PAD = 2.5f;    // world units

    public static final float COLLUMN_SPACING = 0.5f;    // world units
    public static final int COLLUMN_COUNT = 12; // 12

    public static final float ROW_SPACING = 0.5f;   // world units
    public static final int ROW_COUNT = 6; // 6

    public static final float BALL_SIZE = 0.8f;    // world units
    public static final float BALL_HALF_SIZE = BALL_SIZE / 2f;   // world units
    public static final float BALL_START_X = PADDLE_START_X +(PADDLE_START_WIDTH - BALL_SIZE) / 2f;
    public static final float BALL_START_Y = PADDLE_START_Y + PADDLE_HEIGHT;
    public static final float BALL_VELOCITY = 15f;
    public static final float BALL_START_ANGLE = 60F;

    public static final int BRICK_SCORE = 10;

    public static final float PICKUP_SIZE = 1f;
    public static final float PICKUP_SPAWN_TIME = 2f;
    public static final float PICKUP_VELOCITY_Y = -6f;

    // == constructor ==
    private GameConfig(){

    }
}
