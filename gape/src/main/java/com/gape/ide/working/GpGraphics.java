package com.gape.ide.working;
/*
  绘图为-2级
  类表示圆角矩形，不可见类可以用图片表示，类中变量处于其中，静态变量处于其外与边壁相连接，方法为处于边壁上的切角矩形，思维线处于其中
  基本变量表示为矩形
  思维线带有箭头，指示下一步的执行位置
  循环为圆弧加判断框
  判断框暂定位檐角形，switch从檐角形分更多偏枝
 */

import com.gape.cyandr.opengl.GLHelper;

import javax.microedition.khronos.opengles.GL10;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class GpGraphics {
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
