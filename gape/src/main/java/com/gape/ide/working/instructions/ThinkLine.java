package com.gape.ide.working.instructions;


import com.gape.cyandr.opengl.GLEx;
import com.gape.cyandr.opengl.LColor;
import com.gape.ide.working.Instructions;
import com.gape.ide.working.Point;

/**
 * Created by cyandr on 2016/9/13 0013.
 */
public class ThinkLine extends Instructions {

    private float X0, X1, Y0, Y1;
    private int PID = 45485;

    public ThinkLine(StringBuilder stringBuilder) {
        setI_Shape("LINE");
        setInsName("ThinkingLine");
        setJava("Enter");

    }

    @Override
    public void DrawShape(GLEx glEx, Point point) {
        glEx.setColor(LColor.orange);
        glEx.drawLine(point.x, point.y, Y0, Y1);
    }

    @Override
    public void Write2Exe(StringBuilder stringBuilder) {
        stringBuilder.append(getJava());

    }

    public void line(Point begin, Point end) {
        X0 = begin.x;
        Y0 = begin.y;
        X1 = end.x;
        Y1 = end.y;

    }
}
