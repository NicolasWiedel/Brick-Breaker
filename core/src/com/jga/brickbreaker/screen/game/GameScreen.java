package com.jga.brickbreaker.screen.game;

import com.badlogic.gdx.ScreenAdapter;
import com.jga.util.game.GameBase;

public class GameScreen extends ScreenAdapter {

    // == attributes ==
    private final GameBase game;

    // == constructor ==
    public GameScreen(GameBase game){
        this.game = game;
    }
}
