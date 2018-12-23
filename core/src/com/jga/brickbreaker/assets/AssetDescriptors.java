package com.jga.brickbreaker.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public final class AssetDescriptors {

    // == constants ==
    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.SCORE_FONT, BitmapFont.class);

    // == constructor ==
    private AssetDescriptors(){

    }
}
