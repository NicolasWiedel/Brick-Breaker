package com.jga.brickbreaker.screen.loading;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.utils.Array;
import com.jga.brickbreaker.assets.AssetDescriptors;
import com.jga.brickbreaker.config.GameConfig;
import com.jga.brickbreaker.screen.game.GameScreen;
import com.jga.screen.loading.LoadingScreenBase;
import com.jga.util.game.GameBase;

public class LoadingScreen extends LoadingScreenBase {

    // == constructor ==
    public LoadingScreen(GameBase game) {
        super(game);
    }

    // == protected methods ==
    @Override
    protected Array<AssetDescriptor> getAssetDescriptors() {
        return AssetDescriptors.ALL;
    }

    @Override
    protected void onLoadDone() {
        game.setScreen(new GameScreen(game));
    }

    @Override
    protected float getHudWidth() {
        return GameConfig.HUD_WIDTH;
    }

    @Override
    protected float getHudHeight() {
        return GameConfig.HUD_HEIGHT;
    }
}
