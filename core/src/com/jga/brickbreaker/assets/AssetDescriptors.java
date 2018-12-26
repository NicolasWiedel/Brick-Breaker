package com.jga.brickbreaker.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public final class AssetDescriptors {

    // == constants ==
    public static final AssetDescriptor<BitmapFont> FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.SCORE_FONT, BitmapFont.class);

    public static final Array<AssetDescriptor> ALL = new Array<AssetDescriptor>();

    // == static init ==
    static {
        ALL.addAll(
                FONT
        );
    }

    // == constructor ==
    private AssetDescriptors(){

    }
}
