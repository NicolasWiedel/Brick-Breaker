package com.jga.brickbreaker.screen.game;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jga.util.game.GameBase;

public class GameScreen extends ScreenAdapter {

    // == attributes ==
    private final GameBase game;
    private final AssetManager assetManager;

    private GameController controller;
    private GameRenderer renderer;
    private SpriteBatch batch;

    // == constructor ==
    public GameScreen(GameBase game){
        this.game = game;
        assetManager = game.getAssetManager();
        batch = game.getBatch();
    }

    // == public methods

    @Override
    public void show() {
        controller = new GameController();
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
