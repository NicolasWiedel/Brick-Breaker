package com.jga.brickbreaker.screen.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jga.brickbreaker.assets.AssetDescriptors;
import com.jga.brickbreaker.assets.RegionNames;
import com.jga.brickbreaker.config.GameConfig;
import com.jga.brickbreaker.entity.Ball;
import com.jga.brickbreaker.entity.Brick;
import com.jga.brickbreaker.entity.Paddle;
import com.jga.brickbreaker.entity.Pickup;
import com.jga.util.GdxUtils;
import com.jga.util.Validate;
import com.jga.util.ViewportUtils;
import com.jga.util.debug.DebugCameraController;
import com.jga.util.debug.ShapeRendererUtils;
import com.jga.util.entity.EntityBase;
import com.sun.corba.se.impl.orb.ParserTable;

public class GameRenderer implements Disposable {

    // == attributes ==
    private final GameController controller;
    private final SpriteBatch batch;
    private final AssetManager assetManager;
    private final GlyphLayout layout = new GlyphLayout();

    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer renderer;
    private DebugCameraController debugCameraController;

    private BitmapFont scoreFont;

    private TextureRegion backgroundRegion;
    private TextureRegion paddleRegion;
    private TextureRegion ballRegion;
    private TextureRegion brickRegion;

    private TextureRegion expandRegion;
    private TextureRegion shrinkRegion;
    private TextureRegion slowDownRegion;
    private TextureRegion speedUpRegion;

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
        hudViewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        renderer = new ShapeRenderer();

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition(GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y);

        scoreFont = assetManager.get(AssetDescriptors.FONT);

        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);
        paddleRegion = gamePlayAtlas.findRegion(RegionNames.PADDLE);
        ballRegion = gamePlayAtlas.findRegion(RegionNames.BALL);
        brickRegion = gamePlayAtlas.findRegion(RegionNames.BRICK);

        expandRegion = gamePlayAtlas.findRegion(RegionNames.EXPAND);
        shrinkRegion = gamePlayAtlas.findRegion(RegionNames.SHRINK);
        slowDownRegion = gamePlayAtlas.findRegion(RegionNames.SLOW_DOWN);
        speedUpRegion = gamePlayAtlas.findRegion(RegionNames.SPEED_UP);
    }

    // == public methods ==
    public void render(float delta){
        debugCameraController.handleDebugInput(delta);
        debugCameraController.applyTo(camera);

        // clear screen
        GdxUtils.clearScreen();

        renderGamePlay();

        renderDebug();

        renderHud();
    }

    public void resize(int width, int height){
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
        ViewportUtils.debugPixelsPerUnit(viewport);
    }

    @Override
    public void dispose() {

    }

    // == private methods
    private void renderGamePlay(){
        viewport.apply();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        drawGamePlay();

        batch.end();
    }

    private void drawGamePlay(){
        // background
        batch.draw(backgroundRegion, 0,0, GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT);

        //paddle
        Paddle paddle = controller.getPaddle();
        drawEntity(batch, paddleRegion, paddle);

        // ball
        Ball ball = controller.getBall();
        batch.draw(ballRegion, ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());

        // bricks
        Array<Brick> bricks = controller.getBricks();
        for(int i = 0; i < bricks.size; i++){
            Brick brick = bricks.get(i);
            drawEntity(batch, brickRegion, brick);
        }

        // pickups
        Array<Pickup> pickups = controller.getPickups();

        for(int i = 0; i < pickups.size; i++){
            Pickup pickup = pickups.get(i);
            TextureRegion pickupRegion = findPickupRegion(pickup);
            drawEntity(batch, pickupRegion, pickup);
        }

        // effects
        Array<ParticleEffectPool.PooledEffect> effects = controller.getEffects();

        for(int i = 0; i < effects.size; i++){
            ParticleEffectPool.PooledEffect effect = effects.get(i);
            effect.draw(batch);
        }
    }

    private void renderDebug(){
        viewport.apply();

        if(controller.isDrawGrid()) {
            ViewportUtils.drawGrid(viewport, renderer);
        }

        if(controller.isDrawDebug()) {
            renderer.setProjectionMatrix(camera.combined);
            renderer.begin(ShapeRenderer.ShapeType.Line);

            drawDebug();

            renderer.end();
        }
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

        // pickups
        Array<Pickup> pickups = controller.getPickups();
        for(int i = 0; i < pickups.size; i++){
            Pickup pickup = pickups.get(i);
            Rectangle pickupBounds = pickup.getBounds();
            ShapeRendererUtils.rect(renderer, pickupBounds);
        }

        // revert to old color
        renderer.setColor(oldColor);
    }

    private void renderHud(){
        hudViewport.apply();

        batch.setProjectionMatrix(hudViewport.getCamera().combined);
        batch.begin();

        drawHud();

        batch.end();
    }

    private void drawHud(){
        String  scoreString = "SCORE: " + controller.getScoreString();
        layout.setText(scoreFont, scoreString);
        scoreFont.draw(batch, layout,
                0, GameConfig.HUD_HEIGHT - layout.height);
    }

    private TextureRegion findPickupRegion(Pickup pickup){
        TextureRegion region = null;

        if(pickup.isExpand()){
            region = expandRegion;
        } else if(pickup.isShrink()){
            region = shrinkRegion;
        } else if(pickup.isSpeedUp()){
            region = speedUpRegion;
        } else if(pickup.isSlowDown()){
            region = slowDownRegion;
        }

        if(region == null){
            throw new IllegalArgumentException("Can not find region for pickupType " + pickup.getType());
        }

       return region;
    }

    // == static methods ==
    private static void drawEntity(SpriteBatch batch, TextureRegion region, EntityBase entity){
        Validate.notNull(batch);
        Validate.notNull(region);
        Validate.notNull(entity);

        batch.draw(region, entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
    }
}
