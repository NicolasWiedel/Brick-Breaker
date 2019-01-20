package com.jga.shape;

import com.badlogic.gdx.math.MathUtils;

/**
 * created by Nicolas Wiedel on 20.01.2019
 */
public final class ShapeUtils {

    // == constructor ==
    private ShapeUtils(){

    }

    // == public methods ==
    public static float[] createRectangle(float width, float height){
        return createRectangle(0, 0, width, height);
    }

    public static float[] createRectangle(float x, float y, float width, float height){
        return new float[]{
                x, y, // bottom left corner
                x + width, y, // bottom right corner
                x + width, y + height, // tor right corner
                x, y + height // top left corner
        };
    }

    public static float[] createOctagon(float radius){
        return createOctagon(0, 0, radius);
    }

    public static float[] createOctagon(float originX, float originY, float radius){
        return createPolygon(originX, originY, radius, 8);
    }

    public static float[] createPolygon(float originX, float originY, float radius, int vertexCount){
        float[] ret = new float[vertexCount * 2];

        for(int i = 0, j = 0; i < vertexCount; i++){
            ret[j++] = originX + radius * MathUtils.cos(360 * i / vertexCount); // x position
            ret[j++] = originX + radius * MathUtils.sin(360 * i / vertexCount); // y position
        }

        return ret;
    }
}
