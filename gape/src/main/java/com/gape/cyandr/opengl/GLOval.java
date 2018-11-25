package com.gape.cyandr.opengl;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GLOval {

    private static void drawOval(GL10 gl, float x, float y, float width, float height, int mode, int startAngle, int endAngle) {
        GLHelper.openVertex(gl);
        while (endAngle < startAngle) {
            endAngle += 360;
        }
        int length = endAngle - startAngle + 1;
        length = length * 2;
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = byteBuffer.asFloatBuffer();
        float[] vertex = new float[length];
        float a = width / 2;
        float b = height / 2;
        for (int i = 0; i < length; i += 2) {
            vertex[i] = (float) (a * Math.cos(Math.toRadians(i / 2 + startAngle))) + x;
            vertex[i + 1] = (float) (b * Math.sin(Math.toRadians(i / 2 + startAngle))) + y;
        }
        vertexBuffer.put(vertex);
        vertexBuffer.position(0);

        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glDrawArrays(mode, 0, length / 2);

        GLHelper.disableVertex(gl);
    }

    public static void drawOval(GL10 gl, float x, float y, float width, float height) {
        drawOval(gl, x, y, width, height, GL10.GL_LINE_LOOP, 0, 360);
    }

    public static void fillOval(GL10 gl, float x, float y, float width, float height) {
        drawOval(gl, x, y, width, height, GL10.GL_TRIANGLE_FAN, 0, 360);
    }

    public static void drawArc(GL10 gl, float x, float y, float width, float height, int startAngle, int endAngle) {
        drawOval(gl, x, y, width, height, GL10.GL_LINE_STRIP, startAngle, endAngle);
    }

    public static void fillArc(GL10 gl, float x, float y, float width, float height, int startAngle, int endAngle) {
        GLHelper.openVertex(gl);
        while (endAngle < startAngle) {
            endAngle += 360;
        }
        int length = endAngle - startAngle + 2;
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(length * 2 * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer vertexBuffer = byteBuffer.asFloatBuffer();
        float[] vertex = new float[length * 2];
        float a = width / 2;
        float b = height / 2;
        for (int i = 2; i < length * 2; i += 2) {
            vertex[i] = (float) (a * Math.cos(Math.toRadians(i / 2 - 1 + startAngle))) + x;
            vertex[i + 1] = (float) (b * Math.sin(Math.toRadians(i / 2 - 1 + startAngle))) + y;
        }
        vertex[0] = x;
        vertex[1] = y;
        vertexBuffer.put(vertex);
        vertexBuffer.position(0);
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, length);
        GLHelper.disableVertex(gl);
    }
}
