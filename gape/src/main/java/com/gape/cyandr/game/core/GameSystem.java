package com.gape.cyandr.game.core;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import com.gape.cyandr.game.AnimationManager;
import com.gape.cyandr.game.geom.RectBox;
import com.gape.cyandr.opengl.GLEx;
import com.gape.cyandr.opengl.LColor;
import com.gape.cyandr.opengl.command.GLFont;

import java.util.LinkedList;

public class GameSystem {
    public static Context context;
    public static RectBox screenBox;
    public static AssetManager assetManager;
    public static boolean ShowFPS = false;
    public static boolean ShowMemory = false;
    public static Screen CurrentScreen = null;
    public static LinkedList<Screen> screenList = new LinkedList<Screen>();
    private static int scale = 8;
    private static boolean isDrawing = false;
    private static LinkedList<Runnable> DrawingHandle = new LinkedList<Runnable>();

    public static void Initializing() {

    }

    public static void setScreen(Screen screen) {
        if (CurrentScreen != null) {
            CurrentScreen.dispose();
        }
        screen.setSize((int) screenBox.width, (int) screenBox.height);
        CurrentScreen = screen;
        screenList.add(screen);
    }

    public static void setDrawingStart() {
        runDrawingRunnable();
    }

    public static void setDrawingEnd() {
        isDrawing = false;
    }

    public static boolean getIsDrawing() {
        return isDrawing;
    }

    public static void update(long elapsedTime) {
        AnimationManager.update(elapsedTime);
        GLFont.update();
        if (CurrentScreen != null)
            CurrentScreen.update(elapsedTime);
    }

    public static void paint(GLEx g) {
        if (CurrentScreen != null) {
            g.setColor(LColor.white);
            g.drawString("图形比例" + scale, 0, 60);
            isDrawing = true;
            CurrentScreen.paint(g, scale);

        } else {
            Log.i("当前屏幕", "*****************为空");
        }
        Log.i("当前屏幕", "huihuihuihuihuihui");
        if (ShowFPS) {
            g.setColor(LColor.white);
            g.drawString(SystemTimer.getFPS(), 0, 0);
        }
        if (ShowMemory) {
            g.setColor(LColor.white);
            g.drawString(SystemTimer.getMemory(), 0, 20);
        }
    }

    public static void addDrawingRunnable(Runnable runnable) {
        DrawingHandle.add(runnable);
    }

    private static void runDrawingRunnable() {

        for (Runnable runnable : DrawingHandle) {
            runnable.run();
        }
        DrawingHandle.clear();
    }

    public static void removeDrawingRunnable(Runnable runnable) {
        DrawingHandle.remove(runnable);
    }

    public static void setScale(int ascale) {
        scale = ascale;
    }
}
