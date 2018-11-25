package com.gape.ide.working;

import com.gape.cyandr.opengl.GLEx;
import com.gape.cyandr.opengl.LColor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyandr on 2016/10/12 0012.
 */
public class Stack {
    static int IINDEXS = 0;
    static GpeSystem gpeSystem;
    static List<Point> CMS = new ArrayList<>();
    static List<Instructions> INSRS = new ArrayList<>();
    static int Pages;
    static int CurrentPage;

    Stack() {

    }

    void add(Instructions instructions, Point point) {
        IINDEXS++;
        CMS.add(point);
        INSRS.add(instructions);
    }

    void get(GLEx glEx) {
        if (IINDEXS == 0) return;
        glEx.setColor(LColor.red);
        for (int i = 0; i <= IINDEXS - 1; i++) {
            INSRS.get(i).DrawShape(glEx, CMS.get(i));

        }
        glEx.setColor(LColor.white);
    }
}
