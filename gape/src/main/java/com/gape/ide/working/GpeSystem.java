package com.gape.ide.working;

import android.content.Context;
import com.gape.cyandr.game.geom.RectBox;
import com.gape.cyandr.opengl.GLEx;
import com.gape.cyandr.opengl.LColor;
import com.gape.ide.working.instructions.ThinkLine;
import com.gape.ide.working.instructions.Variables;

import java.io.File;
import java.util.ArrayList;

/**
 * @author cyandr
 * Created by cyandr on 2016/9/17 0017.
 */
public class GpeSystem {
    public static RectBox screenBox;
    public static float Scale;
    static Context MContext;
    static StringBuilder MainLog;
    static GLEx glEx;
    static File outputPfile;
    static ArrayList<Integer> InsRecord;
    static ThinkLine line;
    static Variables variables;
    static CoordManager CM;
    static Stack mystack = new Stack();

    public static void Initializing() {
        MainLog = new StringBuilder();
        Point po = new Point(0, 0);

        line = new ThinkLine(MainLog);
        variables = new Variables();
    }

    public static void setGlEx(GLEx gl) {
        glEx = gl;
    }

    public static void addThinkLine() {
        Point point = CM.getCurrentpoint();
        Point point1 = CM.getNextPoint();
        line.line(point, point1);
        line.DrawShape(glEx, point1);
        CM.Addins(point1);
        mystack.add(line, point1);
    }

    public static void addVariable(String type, String name) {
        Point point = CM.getCurrentpoint();
        variables.reInitialized(point, name);
        CM.Addins(point);
        mystack.add(variables, point);
    }

    public static void PaintINS() {
        mystack.get(glEx);

    }

    public static void BackGround() {

        int bScale = 64;
        float S_x = screenBox.width / bScale;
        float S_y = screenBox.height / bScale;

        CM = new CoordManager(new Point((int) S_x / 2, 10), new Point((int) S_x, (int) S_y));

        float gap_x = S_x / 50;
        float gap_y = S_y / 50;
        glEx.setColor(LColor.green);
        for (int i = 0; i <= bScale; i++) {
            glEx.drawLine(S_x * i, 0, S_x * i, screenBox.height);

        }
        for (int i = 0; i <= bScale; i++) {
            glEx.drawLine(0, S_y * i, screenBox.width, S_y * i);
        }
    }


}
