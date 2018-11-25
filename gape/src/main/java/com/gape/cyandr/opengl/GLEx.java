package com.gape.cyandr.opengl;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.opengl.GLUtils;
import com.gape.cyandr.opengl.command.GLFont;

import javax.microedition.khronos.opengles.GL10;
import java.nio.FloatBuffer;

public class GLEx {
    public static GLEx glex;
    private GL10 gl = null;
    private  LColor color =  LColor.white;
    public GLEx(GL10 gl) {
        if (gl == null)
            throw new IllegalArgumentException("GL10 is null!");
        this.gl = gl;
        glex = this;
    }

    public void resetColor() {
        color =  LColor.white;
        gl.glColor4f(color.r, color.g, color.b, color.a);
    }

    public  LColor getColor() {
        return this.color;
    }

    public void setColor( LColor color) {
        this.color = color;
        gl.glColor4f(color.r, color.g, color.b, color.a);
    }

    public void glBind(LTexture texture) {
        if (texture.getTextureID() == 0) {
            Bitmap image = texture.getBitmap();
            if (image == null)
                return;
            int[] textures = new int[1];
            gl.glGenTextures(1, textures, 0);
            texture.setTextureID(textures[0]);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.getTextureID());

            gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
                    GL10.GL_LINEAR);
            gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
                    GL10.GL_LINEAR);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
//			GL10.GL_CLAMP_TO_EDGE ���ظ�

            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, image, 0);
            image = null;
            texture.recycle();
        }

        gl.glBindTexture(GL10.GL_TEXTURE_2D, texture.getTextureID());
    }

    public void drawString(String text, float x, float y) {
        drawString(text, x, y, 14, getColor());
    }

    public void drawString(String text, float x, float y, LColor color) {
        drawString(text, x, y, 14, color);
    }

    public void drawString(String text, float x, float y, int fontSize, LColor color) {
        drawString(text, x, y, 10000, fontSize, color, null, Typeface.NORMAL);
    }

    public void drawString(String text, float x, float y, int maxWidth, int fontSize, LColor color, String fmilyName, int style) {
        Paint paint = new Paint();
        paint.setTextSize(fontSize);
        if (color == null) {
            color = getColor();
        }
        paint.setColor(color.getColor());
        if (fmilyName != null) {
            paint.setTypeface(Typeface.create(fmilyName, style));
        }
        GLFont.drawString(this, text, x, y, paint, maxWidth);
    }

    public void delete(LTexture texture) {
        delete(texture.getTextureID());
        texture.dispose();
    }

    public void delete(int textureID) {
        if (textureID != 0) {
            gl.glDeleteTextures(1, new int[]{textureID}, 0);
            System.out.println("Delete Texture ��" + textureID);
        }
    }

    public void drawTexture(LTexture texture, float x, float y) {
        drawTexture(texture, x, y, texture.getWidth(), texture.getHeight());
    }

    public void drawTexture(LTexture texture, float x, float y, float width, float height) {
        openTexture();
        glBind(texture);

        FloatBuffer pointer = GLHelper.getVertex(x, y, width, height);
        FloatBuffer coord = GLHelper.getCoord(texture.getOX(), texture.getOY(), texture.getMaxW(), texture.getMaxH());
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, pointer);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, coord);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        disableTexture();
    }

    public void drawRect(float x, float y, float width, float height) {
        GLPolygon.drawRect(gl, x, y, width, height);
    }

    public void drawTriangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        GLPolygon.drawTriangle(gl, x1, y1, x2, y2, x3, y3);
    }

    public void drawLine(float x1, float y1, float x2, float y2) {
        GLPolygon.drawLine(gl, x1, y1, x2, y2);
    }

    public void drawArc(float x, float y, float width, float height, int startAngle, int endAngle) {
        GLOval.drawArc(gl, x, y, width, height, startAngle, endAngle);
    }

    public void drawCircular(float x, float y, float r) {
        GLOval.drawOval(gl, x, y, r, r);
    }

    public void drawOval(float x, float y, float width, float height) {
        GLOval.drawOval(gl, x, y, width, height);
    }

    public void fillArc(float x, float y, float width, float height,
                        int startAngle, int endAngle) {
        GLOval.fillArc(gl, x, y, width, height, startAngle, endAngle);
    }

    public void fillCircular(float x, float y, float r) {
        GLOval.fillOval(gl, x, y, r, r);
    }

    public void fillOval(float x, float y, float width, float height) {
        GLOval.fillOval(gl, x, y, width, height);
    }

    public void fillRect(float x, float y, float width, float height) {
        GLPolygon.fillRect(gl, x, y, width, height);
    }

    public void fillTriangle(float x1, float y1, float x2, float y2, float x3,
                             float y3) {
        GLPolygon.fillTriangle(gl, x1, y1, x2, y2, x3, y3);

    }

    public void openTexture() {
        GLHelper.openTexture(gl);
    }

    public void openVertex() {
        GLHelper.openVertex(gl);
    }

    public void disableVertex() {
        GLHelper.disableVertex(gl);
    }

    public void disableTexture() {
        GLHelper.disableTexture(gl);
    }
}
