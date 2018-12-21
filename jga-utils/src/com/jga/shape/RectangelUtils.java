package com.jga.shape;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.jga.util.Validate;

public class RectangelUtils {

    // == constructor ==
    private RectangelUtils(){

    }

    // == pulic methods ==
    public static Vector2 getTopLeft(Rectangle rectangle){
        Validate.notNull(rectangle);

        return new Vector2(rectangle.x, rectangle.y + rectangle.height);
    }

    public static Vector2 getTopRight(Rectangle rectangle){
        Validate.notNull(rectangle);

        return new Vector2(rectangle.x + rectangle.width, rectangle.y + rectangle.height);
    }


}