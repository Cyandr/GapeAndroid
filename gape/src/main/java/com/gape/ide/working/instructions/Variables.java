package com.gape.ide.working.instructions;

import com.gape.cyandr.opengl.GLEx;
import com.gape.cyandr.opengl.LColor;
import com.gape.ide.working.Instructions;
import com.gape.ide.working.Point;


/**
 * Created by cyandr on 2016/9/18 0018.
 */
public class Variables extends Instructions {


    private float X0, Y0, VWidth = 100, VHeight = 50;
    private int PID = 45486;


    public Variables() {

    }

    @Override
    public void DrawShape(GLEx glEx, Point anchorpoint) {
        glEx.setColor(LColor.orange);
        glEx.drawRect(anchorpoint.x, anchorpoint.y, VWidth, VHeight);
        glEx.drawString(getJava(), X0 + VWidth / 2, Y0 + VHeight / 2);
        setreleasePoint(new Point(X0 + VWidth / 2, Y0 + VHeight));
    }

    @Override
    public void Write2Exe(StringBuilder stringBuilder) {
        stringBuilder.append(";").append(getI_Para()).append(getJava());
    }

    public void setType(VARIABLE_TYPE by) {

    }

    public void reInitialized(Point begin, String name) {
        X0 = begin.x;
        Y0 = begin.y;
        VWidth = name.length() * 40;
        setJava(name);
        anchorPoint = new Point(X0 + VWidth / 2, Y0 + VHeight);
    }

    public enum VARIABLE_TYPE {
        GP_Int("int"),
        GP_String("String"),
        GP_byte("byte"),
        GP_char("char"),
        GP_long("long"),
        GP_float("float"),
        GP_double("double");
        private String type;

        VARIABLE_TYPE(String wtype) {
            type = wtype;
        }
    }
}
