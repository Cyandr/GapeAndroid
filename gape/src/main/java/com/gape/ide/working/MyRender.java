package com.gape.ide.working;

import android.graphics.Point;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.opengl.Matrix;
import android.os.Handler;
import android.util.Log;
import com.gape.cyandr.game.core.SystemTimer;
import com.gape.cyandr.game.geom.RectBox;
import com.gape.cyandr.gapeandroid.gape.GpeApplication;
import com.gape.cyandr.opengl.GLEx;
import com.gape.cyandr.opengl.GLHelper;
import com.gape.ide.working.instructions.ThinkLine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRender implements Renderer {
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private final float[] mMMatrix = new float[16];
    GLEx glex;
    float CURRENT_X = 0, CURRRNT_Y = 0;
    int herescale = 8;
    float hereAngle = 0f;
    float[] mRotationMatrix = new float[16];
    ThinkLine thinkingLine;
    Point nowPoint;
    int nowdirection = 0;
    int mWidth, mheight;
    GPView.OPERATION_LAYER operation_layer;
    private GpeApplication myapp;
    private Handler myHandler;

    MyRender(GpeApplication app) {
        myapp = app;
        myHandler = myapp.getMyAppHandler();
    }

    public void onDrawFrame(GL10 gl) {

        GLHelper.clear(gl);
        GLHelper.openTransparent(gl);
        SystemTimer.run();
        GLHelper.disableBlend(gl);
        Log.i("绘图顺序", "surface draw frame!》》》》》》》》》");


        GLU.gluLookAt(gl, -CURRENT_X, -CURRRNT_Y, 1, -CURRENT_X, -CURRRNT_Y, 0f, 0f, 1.0f, 0f);


        // gl.glRotatef(hereAngle, 0, 0, 1.0f);

        gl.glScalef(GpeSystem.Scale, GpeSystem.Scale, 0);
        GpeSystem.BackGround();
        GpeSystem.PaintINS();
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // TODO Auto-generated method stub
        mWidth = width;
        mheight = height;
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        //gl.glOrthof(0, width, height, 0, 1, -100);
        GLU.gluOrtho2D(gl, 0, width, height, 0);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GpeSystem.screenBox = new RectBox(0, 0, width, height);
        setScale(16);
        float ratio = (float) width / height;
        herescale = 16;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
        // TODO Auto-generated method stub
        glex = new GLEx(gl);
        gl.glClearColor(255, 255, 255, 1);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearDepthf(1);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        Log.i("执行顺序", "surface created");
        nowPoint = new Point(0, 0);

        GpeSystem.setGlEx(glex);
        operation_layer = GPView.OPERATION_LAYER.OPERATION_IN_GLOBAL;
    }

    public void setScale(float angle) {
        GpeSystem.Scale = angle;

    }

    public void setAngle(float angle) {
        hereAngle = angle;
    }

    public void setMove(float x, float y) {
        CURRENT_X += x;
        CURRRNT_Y += y;
    }

    public void clearNove() {
        CURRENT_X = 0;
        CURRRNT_Y = 0;
    }

    public void setOperationLayer(GPView.OPERATION_LAYER layer) {
        operation_layer = layer;
    }

}
