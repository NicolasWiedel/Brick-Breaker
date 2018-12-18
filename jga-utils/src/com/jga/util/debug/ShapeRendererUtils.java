package com.jga.util.debug;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.jga.util.Validate;

public class ShapeRendererUtils {

    // == constructor ==
    private ShapeRendererUtils(){

    }

    public static void rect(ShapeRenderer renderer, Rectangle rectangle){
        Validate.notNull(renderer);
        Validate.notNull(rectangle);

        renderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
