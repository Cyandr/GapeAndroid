package com.gape.cyandr.opengl;

import android.graphics.Color;

public class LColor {
    public static final LColor white = new LColor(1.0f, 1.0f, 1.0f, 1.0f);
    public static final LColor yellow = new LColor(1.0f, 1.0f, 0.0f, 1.0f);
    public static final LColor red = new LColor(1.0f, 0.0f, 0.0f, 1.0f);
    public static final LColor blue = new LColor(0.0f, 0.0f, 1.0f, 1.0f);
    public static final LColor cornFlowerBlue = new LColor(0.4f, 0.6f, 0.9f,
            1.0f);
    public static final LColor green = new LColor(0.0f, 1.0f, 0.0f, 1.0f);
    public static final LColor black = new LColor(0.0f, 0.0f, 0.0f, 1.0f);
    public static final LColor gray = new LColor(0.5f, 0.5f, 0.5f, 1.0f);
    public static final LColor cyan = new LColor(0.0f, 1.0f, 1.0f, 1.0f);
    public static final LColor darkGray = new LColor(0.3f, 0.3f, 0.3f, 1.0f);
    public static final LColor lightGray = new LColor(0.7f, 0.7f, 0.7f, 1.0f);
    public final static LColor pink = new LColor(1.0f, 0.7f, 0.7f, 1.0f);
    public final static LColor orange = new LColor(1.0f, 0.8f, 0.0f, 1.0f);
    public final static LColor magenta = new LColor(1.0f, 0.0f, 1.0f, 1.0f);
    public float r, g, b, a;

    public LColor(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public int getColor() {

        return Color.argb((int) (a * 255), (int) (r * 255), (int) (g * 255), (int) (b * 255));
    }

}
