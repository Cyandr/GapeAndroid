package com.gape.cyandr.opengl;
/*
  @author cyandr
 */

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GLHelper {

    public static void clear(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
    }

    public static void openTexture(GL10 gl) {
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }

    public static void openTransparent(GL10 gl) {
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
        gl.glEnable(GL10.GL_BLEND);
    }

    public static void openVertex(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }

    public static void disableTexture(GL10 gl) {
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_TEXTURE_2D);
    }

    public static void disableVertex(GL10 gl) {
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }

    public static void disableBlend(GL10 gl) {
        gl.glDisable(GL10.GL_BLEND);
    }

    public static FloatBuffer getVertex(float x, float y, float width, float height) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(8 * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(new float[]{
                x, y,
                x + width, y,
                x, y + height,
                x + width, y + height

        });
        floatBuffer.position(0);
        return floatBuffer;
    }

    public static FloatBuffer getCoord(float maxW, float maxH) {
        return getCoord(0, 0, maxW, maxH);
    }

    public static FloatBuffer getCoord(float x, float y, float maxW, float maxH) {
//		System.out.println(x+"  " + y + " " +maxW + "  "+ maxH );
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(8 * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
        floatBuffer.put(new float[]{
                x, y,
                maxW, y,
                x, maxH,
                maxW, maxH

        });
        floatBuffer.position(0);
        return floatBuffer;
    }
}
