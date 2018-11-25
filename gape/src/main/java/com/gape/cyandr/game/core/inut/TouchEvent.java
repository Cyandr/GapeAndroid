package com.gape.cyandr.game.core.inut;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.gape.ide.working.MyRender;

public class TouchEvent implements View.OnTouchListener {
    public float x, y;
    public float x2, y2;
    Context mContext;
    MyRender myRender;
    private int TOUCHING_NUM = 0;
    private float oldDist;
    private int SCALE_ZOOM = 8;


    TouchEvent(Context mContext, MyRender render) {
        mContext = mContext;
        myRender = render;

    }


    @Override
    public boolean onTouch(View view, MotionEvent e) {
        switch (e.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                TOUCHING_NUM = 1;
                break;
            case MotionEvent.ACTION_UP:
                TOUCHING_NUM = 0;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                TOUCHING_NUM += 1;
                oldDist = spacing(e);
                Log.i("指尖滑动", oldDist + "现在第一次滑动");
                break;
            case MotionEvent.ACTION_POINTER_UP:
                if (TOUCHING_NUM >= 2) {

                    float newDist = spacing(e);
                    Log.i("指尖滑动", oldDist + "现在" + newDist);
                    if (newDist - oldDist > 1) {
                        //zoom(newDist / oldDist);

                        SCALE_ZOOM /= 2;
                        myRender.setScale(SCALE_ZOOM);

                    }
                    if (newDist - oldDist < -1) {
                        // zoom(newDist / oldDist);

                        SCALE_ZOOM *= 2;

                        myRender.setScale(SCALE_ZOOM);

                    }

                }

                TOUCHING_NUM -= 1;
                break;
            case MotionEvent.ACTION_MOVE:


                break;
        }

        return true;
    }

    public void setMyRender(MyRender myRender) {
        this.myRender = myRender;
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
}
