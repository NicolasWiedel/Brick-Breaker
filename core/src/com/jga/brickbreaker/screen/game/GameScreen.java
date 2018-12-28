package com.jga.brickbreaker.screen.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jga.brickbreaker.BrickbreakerGame;
import com.jga.brickbreaker.common.ScoreController;
import com.jga.brickbreaker.entity.EntityFactory;
import com.jga.util.game.GameBase;

public class GameScreen extends ScreenAdapter {

    // == attributes ==
    private final GameBase game;
    private final AssetManager assetManager;
    private final SpriteBatch batch;
    private final ScoreController scoreController;

    private GameController controller;
    private GameRenderer renderer;
    private EntityFactory factory;

    // == constructor ==
    public GameScreen(GameBase game){
        this.game = game;
        assetManager = game.getAssetManager();
        batch = game.getBatch();
        scoreController = ((BrickbreakerGame)game).getScoreController();
    }

    // == public methods

    @Override
    public void show() {
        factory = new EntityFactory(assetManager);
        controller = new GameController(scoreController, factory);
        renderer = new GameRenderer(controller, batch, assetManager);

    }
    @Override
    public void render(float delta) {
        controller.update(delta);
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
    }
}
