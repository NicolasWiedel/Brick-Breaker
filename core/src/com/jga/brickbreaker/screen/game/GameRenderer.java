package com.jga.brickbreaker.screen.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jga.brickbreaker.config.GameConfig;
import com.jga.brickbreaker.entity.Brick;
import com.jga.util.GdxUtils;
import com.jga.util.ViewportUtils;
import com.jga.util.debug.DebugCameraController;
import com.jga.util.debug.ShapeRendererUtils;

public class GameRenderer implements Disposable {

    // == attributes ==
    private final GameController controller;
    private final SpriteBatch batch;
    private final AssetManager assetManager;

    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private DebugCameraController debugCameraController;

    // == constructor ==

    public GameRenderer(GameController controller, SpriteBatch batch, AssetManager assetManager) {
        this.controller = controller;
        this.batch = batch;
        this.assetManager = assetManager;
        init();
    }

    // == init ==
    private void init(){
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera);
        renderer = new ShapeRenderer();

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);
    }

    // == public methods ==
    public void render(float delta){
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        // clear screen
        GdxUtils.clearScreen();

        renderDebug();
    }

    public void resize(int width, int height){
        viewport.update(width, height, true);
        ViewportUtils.debugPixelsPerUnit(viewport);
    }

    @Override
    public void dispose() {

    }

    // == private methods
    private void renderDebug(){
        viewport.apply();
        ViewportUtils.drawGrid(viewport, renderer);

        renderer.setProjectionMatrix(camera.combined);
        renderer.begin(ShapeRenderer.ShapeType.Line);

        drawDebug();

        renderer.end();
    }

    private void drawDebug(){
        //save old color
        Color oldColor = renderer.getColor().cpy();
        renderer.setColor(Color.RED);

        // paddle
        Rectangle paddleBounds = controller.getPaddle().getBounds();
        ShapeRendererUtils.rect(renderer, paddleBounds);

        // bricks
        for(Brick brick : controller.getBricks()){
            ShapeRendererUtils.rect(renderer, brick.getBounds());
        }

        // ball
        Circle ballBounds = controller.getBall().getBounds();
        ShapeRendererUtils.circle(renderer, ballBounds);

        // revert to old color
        renderer.setColor(oldColor);
    }
}
