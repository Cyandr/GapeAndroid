package com.gape.ide.working;

/**
 * Created by cyandr on 2016/9/17 0017.
 */
public class CoordManager {
    private Point Currentpoint;
    private Point Nextpoint;
    private boolean NOFIRST = false;
    private Point ScreenSize;


    CoordManager(Point point, Point screen) {

        Currentpoint = point;
        ScreenSize = screen;

    }

    public Point getNextPoint() {
        if (!NOFIRST) return new Point(0, 0);
        return Nextpoint;
    }

    public Point getCurrentpoint() {
        if (!NOFIRST) return new Point(0, 0);
        return Currentpoint;
    }

    public void Addins(Point po) {
        Currentpoint = po;
        NOFIRST = true;
    }

}
