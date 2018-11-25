package com.gape.ide.working;


import android.graphics.Rect;

/**
 * Created by yjk on 2016/11/15.
 */
public class VertexManager {
    int SCREEN_TOP, SCREEN_BOTTOM, SCREEN_LEFT, SCREEN_RIGHT;
    private int Map_Scale = 16;
    private double Beauty_Fraction = 0.3;
    private Rect MYRECT;

    public int getMap_Scale() {
        return Map_Scale;
    }

    public void setMap_Scale(int map_Scale) {
        Map_Scale = map_Scale;
    }

    public void VertexManager(Point left_bottom, Point right_top) {
    }

    private void calcSize(VERTEX_SHAPE shape) {
        switch (shape) {
            case NUMBER_RECT:
                double bainchang = (SCREEN_RIGHT - SCREEN_LEFT) * Beauty_Fraction;
                MYRECT = new Rect();
                break;
            case STRING_RECT:
                break;
            case CLASS_POLYLINE:
                break;
            case THINKLINE_LINE:
                break;
            case LOOP_CIRCLE:
                break;
            case JUDGEMENT_TRIANGLE:
                break;
            case FRAMEWORK_RECT:
                break;

            default:
                break;
        }
    }

    public void setBeauty(double size) {

        Beauty_Fraction = size;
    }

    public enum VERTEX_SHAPE {
        NUMBER_RECT,
        STRING_RECT,
        CLASS_POLYLINE,
        THINKLINE_LINE,
        LOOP_CIRCLE,
        JUDGEMENT_TRIANGLE,
        FRAMEWORK_RECT,
    }
}
