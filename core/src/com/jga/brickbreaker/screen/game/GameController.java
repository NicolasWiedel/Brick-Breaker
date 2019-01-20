package com.jga.brickbreaker.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.jga.brickbreaker.common.ScoreController;
import com.jga.brickbreaker.config.GameConfig;
import com.jga.brickbreaker.entity.Ball;
import com.jga.brickbreaker.entity.Brick;
import com.jga.brickbreaker.entity.EntityFactory;
import com.jga.brickbreaker.entity.Paddle;
import com.jga.brickbreaker.entity.Pickup;
import com.jga.brickbreaker.input.PaddleInputController;
import com.jga.brickbreaker.script.BallSlowDownScript;
import com.jga.brickbreaker.script.BallSpeedUpScript;
import com.jga.brickbreaker.script.PaddleExpandScript;
import com.jga.brickbreaker.script.PaddleShrinkScript;
import com.jga.shape.RectangelUtils;

public class GameController {

    // == attributes ==
    private final GameWorld gameWorld;
    private final GameRenderer renderer;

    // == constructor ==
    public GameController(GameWorld gameWorld, GameRenderer renderer) {
        this.gameWorld = gameWorld;
        this.renderer = renderer;
    }

    // == public methoods =
    public void update(float delta) {
        // handle debug input
        handleDebugInput();

        if (gameWorld.isGameOver()){
            return;
        }

        Ball ball = gameWorld.getBall();
        if(ball.isNotActive() && Gdx.input.justTouched()){
            gameWorld.activateBall();
        }

        gameWorld.update(delta);
    }

    public Vector2 screenToWorld( Vector2 screenCoordinates){
        return renderer.screenToWorld(screenCoordinates);
    }

    // == private methods ==
    private void handleDebugInput(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.F5)){
            gameWorld.toggleDrawGrid();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.F6)){
            gameWorld.toggleDebug();
        }
    }
}
