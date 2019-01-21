package com.jga.brickbreaker.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jga.brickbreaker.assets.AssetDescriptors;
import com.jga.brickbreaker.assets.RegionNames;
import com.jga.brickbreaker.config.GameConfig;
import com.jga.brickbreaker.screen.game.GameScreen;
import com.jga.util.GdxUtils;
import com.jga.util.game.GameBase;

/**
 * Created by goran on 12/12/2016.
 */

public class MenuScreen extends ScreenAdapter {

    // == attributes ==
    private final GameBase game;
    private final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;

    // == constructors ==
    public MenuScreen(GameBase game) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    // == public methods ==
    @Override
    public void show() {
        viewport = new FitViewport(GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT);
        stage = new Stage(viewport, game.getBatch());
        stage.setDebugAll(true);

        Skin skin = assetManager.get(AssetDescriptors.SKIN);
        TextureAtlas gamePlayAtlas = assetManager.get(AssetDescriptors.GAME_PLAY);
        TextureRegion backgroundRegion = gamePlayAtlas.findRegion(RegionNames.BACKGROUND);

        Table table = new Table();
        table.defaults().space(20);
        table.setBackground(new TextureRegionDrawable(backgroundRegion));

        TextButton playButton = new TextButton("PLAY", skin);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                play();
            }
        });

        TextButton quitButton = new TextButton("QUIT", skin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                quit();
            }
        });

        table.add(playButton).row();
        table.add(quitButton).row();

        table.center();
        table.setFillParent(true);
        table.pack();

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        GdxUtils.clearScreen();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        stage.dispose();
    }

    // == private methods ==
    private void play() {
        game.setScreen(new GameScreen(game));
    }

    private void quit() {
        Gdx.app.exit();
    }
}
