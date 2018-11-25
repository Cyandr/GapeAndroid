package com.gape.cyandr.opengl;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GLPolygon {
    public static void drawPolygon(GL10 gl, float[] vertex) {
        drawPolygon(gl, vertex, GL10.GL_LINE_LOOP);
    }

    private static void drawPolygon(GL10 gl, float[] vertex, int mode) {
        GLHelper.openVertex(gl);
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertex.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer vertexs = byteBuffer.asFloatBuffer();
        vertexs.put(vertex);
        vertexs.position(0);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexs);
        gl.glDrawArrays(mode, 0, vertex.length / 2);

        GLHelper.disableVertex(gl);
    }

    public static void fillPolygon(GL10 gl, float[] vertex) {
        drawPolygon(gl, vertex, GL10.GL_TRIANGLE_STRIP);
    }

    public static void drawLine(GL10 gl, float x1, float y1, float x2, float y2) {
        drawPolygon(gl, new float[]{x1, y1, x2, y2});
    }

    public static void drawRect(GL10 gl, float x, float y, float width, float height) {
        drawPolygon(gl, new float[]{x, y, x, y + height, x + width, y + height, x + width, y});
    }

    public static void drawTriangle(GL10 gl, float x1, float y1, float x2, float y2, float x3, float y3) {
        drawPolygon(gl, new float[]{x1, y1, x2, y2, x3, y3});
    }

    public static void fillTriangle(GL10 gl, float x1, float y1, float x2, float y2, float x3, float y3) {
        fillPolygon(gl, new float[]{x1, y1, x2, y2, x3, y3});
    }

    public static void fillRect(GL10 gl, float x, float y, float width, float height) {
        fillPolygon(gl, new float[]{x, y, x, y + height, x + width, y + height, x + width, y});
    }
}
