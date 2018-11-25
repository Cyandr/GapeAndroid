package com.gape.cyandr.game.geom;

public class RectBox {

    public float x, y, width, height;

    public RectBox() {
    }

    public RectBox(float x, float y, float width, float height) {
        setBound(x, y, width, height);
    }

    public void setBound(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * �ƶ��ľ���
     *
     * @param x
     * @param y
     */
    public void move(float x, float y) {
        this.x += x;
        this.y += y;
    }

    /**
     * �ƶ���X��Y
     *
     * @param x
     * @param y
     */
    public void moveTO(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public boolean intersect(RectBox box) {
        return intersect(box.x, box.y, box.width, box.height);
    }

    public boolean intersect(float x2, float y2, float width2, float height2) {
        if (x2 > x + width)
            return false;
        if (x2 + width2 < x)
            return false;
        if (y2 > y + height)
            return false;
        return y2 + height2 >= y;
        // ((x<x2&&(x+width)>x2)||
        // (x<(x2+width2)&&(x+width)>(x2+width2)))
        // &&((y<(y2+height2)&&(y+height)>(y2+height2))
        // ||(y<y2&&y+height>y2));
    }

    public boolean contain(RectBox box) {
        return contain(box.x, box.y, box.width, box.height);
    }

    public boolean contain(float x2, float y2, float width2, float height2) {
        return x < x2 && x + width > x2 + width2 && y < y2
                && y + height > y2 + width2;
    }

    public boolean contain(float x, float y) {
        return contain(x, y, 0, 0);
    }
}
