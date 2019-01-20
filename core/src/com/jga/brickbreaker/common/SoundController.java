package com.jga.brickbreaker.common;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.jga.brickbreaker.assets.AssetDescriptors;

/**
 * Created by Nicolas Wiedel on 20.01.2019.
 */
public class SoundController {

    // == attributes ==
    private final AssetManager assetManager;

    private Sound hit;
    private Sound lost;
    private Sound pickup;

    // == constructor ==

    public SoundController(AssetManager assetManager) {
        this.assetManager = assetManager;
        init();
    }

    // == init ==
    private void init(){
        hit = assetManager.get(AssetDescriptors.HIT);
        lost = assetManager.get(AssetDescriptors.LOST);
        pickup = assetManager.get(AssetDescriptors.PICKUP);
    }

    // == public methods
    public void hit(){
        hit.play();
    }

    public void lost(){
        lost.play();
    }

    public void pickup(){
        pickup.play();
    }
}
