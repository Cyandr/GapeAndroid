package com.gape.ide.working;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import com.gape.cyandr.gapeandroid.gape.GpeApplication;

import static  com.gape.ide.working.MessageConst.VARIABLE_POSITION_COFIRMED;

/**
 * Created by cyandr on 2016/9/9 0009.
 */
public class GPView extends GLSurfaceView {

    public WOEKINGMODE GpViewMode = WOEKINGMODE.MOVE_SCREEN;
    int iy = 0;
    float[] X, Y;
    float LAST_x = 0, LAST_y = 0;
    private GpeApplication myApp;
    private Handler myHandler;
    private MyRender myRender;
    private int mode = 0, single = 1, multi = 2;// 设置点击的模式，是单点触摸还是多点

    private float startDistance; // 两点开始的距离
    private float startRotation;// 开始的角度
    private Point midPoint; // 两点的中心位置
    private OPERATION_LAYER current_OP_layer = OPERATION_LAYER.OPERATION_IN_GLOBAL;
    private long double_tap_time = 0;
    private int TOUCHING_NUM = 0;
    private float oldDist;
    private int SCALE_ZOOM = 8;
    private Point touchPoint;
    private int tapTime = 0;
    private Point setVarPoint;

    public GPView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public GPView(Context context) {
        super(context);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        switch (e.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:           //第一个手指按下
                TOUCHING_NUM = 1;
                X = new float[100];
                Y = new float[100];
                X[0] = e.getX();
                Y[0] = e.getY();
                LAST_x = X[0];
                LAST_y = Y[0];
                Log.i("指尖滑动", "触摸开始");
                Log.i("指尖滑动", "单指触摸" + X[0] + " " + Y[0]);
                tapTime++;

                if (tapTime == 1) {
                    double_tap_time = System.currentTimeMillis();
                } else {
                    long be_minus = System.currentTimeMillis();
                    if ((be_minus - double_tap_time) / 1e3 < 0.2) {


                        switchOperation();
                        Log.i("指尖滑动**************", String.valueOf((be_minus - double_tap_time) / 1e3));
                        tapTime = 0;
                    } else {
                        tapTime = 0;
                    }
                }

                if (GpViewMode == WOEKINGMODE.PLACE_VARIABLE) {

                    setVarPoint = new Point(e.getX(), e.getY());
                    GpViewMode = WOEKINGMODE.MOVE_SCREEN;
                    Message msg = Message.obtain();
                    msg.what = VARIABLE_POSITION_COFIRMED;
                    myHandler.sendMessage(msg);
                }
                break;
            case MotionEvent.ACTION_UP:  //第一个手指抬起
                TOUCHING_NUM = 0;
                X = null;
                Y = null;
                Log.i("指尖滑动", "释放触摸动作");

                break;
            case MotionEvent.ACTION_POINTER_DOWN://第er个手指按下
                TOUCHING_NUM += 1;
                oldDist = spacing(e);
                X[1] = e.getX();
                Y[1] = e.getY();
                Log.i("指尖滑动", oldDist + "现在第一次滑动");
                startDistance = spacing(e);
                startRotation = getRotation(e);
                midPoint = getMidPoint(e);

                break;
            case MotionEvent.ACTION_POINTER_UP://第er个手指抬起
                if (TOUCHING_NUM >= 2) {
                    float newDist = spacing(e);
                    Log.i("指尖滑动", oldDist + "现在" + newDist);
                    if (newDist - oldDist > 1) {
                        //zoom(newDist / oldDist);
                        SCALE_ZOOM /= 2;
                        myRender.setScale(newDist / oldDist);
                        requestRender();
                    }
                    if (newDist - oldDist < -1) {
                        // zoom(newDist / oldDist);

                        SCALE_ZOOM *= 2;

                        myRender.setScale(newDist / oldDist);
                        requestRender();
                    }

                }

                TOUCHING_NUM -= 1;
                break;
            case MotionEvent.ACTION_MOVE:
                if (TOUCHING_NUM == 1) {
                    float x = e.getX() - LAST_x;
                    float y = e.getY() - LAST_y;
                    Log.i("指尖滑动", "单指触摸");
                    if (Math.abs(x) > 10 || Math.abs(y) > 10) {
                        move(x, y);
                    }
                    LAST_x = e.getX();
                    LAST_y = e.getY();
                } else if (TOUCHING_NUM == 2) {
                    float endDistance = spacing(e);
                    float endRotate = getRotation(e);
                    if (endDistance > 10f) {
                        float sclace = endDistance / startDistance;
                        float rotate = endRotate - startRotation;
                        myRender.setAngle(rotate);
                    }

                    break;
                }

        }
        return true;
    }

    public Point getVariableCoordinate() {


        return setVarPoint;

    }

    public void setMyRender(MyRender myRender) {
        this.myRender = myRender;
        super.setRenderer(myRender);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void move(float x, float y) {

        myRender.setMove(x, y);
        requestRender();

    }

    private float getRotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private Point getMidPoint(MotionEvent event) {
        float midx = (event.getX(0) + event.getX(1)) / 2;
        float midy = (event.getY(0) + event.getY(1)) / 2;
        return new Point(midx, midy);
    }

    private void switchOperation() {
        if (current_OP_layer == OPERATION_LAYER.OPERATION_IN_GLOBAL) {
            current_OP_layer = OPERATION_LAYER.OPERATION_IN_ELEMEN;

        } else {
            current_OP_layer = OPERATION_LAYER.OPERATION_IN_GLOBAL;
        }
        myRender.setOperationLayer(current_OP_layer);
        Message msg = Message.obtain();
        msg.what = MessageConst.QUIT_RIGHT_EDIT;
        myHandler.sendMessage(msg);
    }

    public GpeApplication getMyApp() {
        return myApp;
    }

    void setMyApp(GpeApplication myApp) {
        this.myApp = myApp;
        myHandler = myApp.getMyAppHandler();
    }

    enum WOEKINGMODE {
        PLACE_VARIABLE,
        MOVE_SCREEN,
        MOVE_VARIABLE
    }

    enum OPERATION_LAYER {
        OPERATION_IN_GLOBAL, OPERATION_IN_ELEMEN
    }
}
