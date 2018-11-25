package com.gape.cyandr.game.core;

import com.gape.cyandr.game.ISprite;
import com.gape.cyandr.opengl.GLEx;
import com.gape.cyandr.opengl.LColor;
import com.gape.cyandr.opengl.LTexture;

public abstract class Screen {
    static float S_x;
    static float S_y;
    static float gap_x;
    static float gap_y;
    private static int m = 16;
    public LTexture background;
    int width, height;

    public void setBackground(String filename) {
        this.background = new LTexture(filename);
    }

    public void paint(GLEx g, int Scale) {
        if (background != null)
            g.drawTexture(background, 0, 0, width, height);
        if (Scale < 1) {
            Scale = 1;
        } else if (Scale > 64) {
            Scale = 64;
        }
        S_x = width / Scale;
        S_y = height / Scale;
        gap_x = S_x / 50;
        gap_y = S_y / 50;
        g.setColor(LColor.green);
        for (int i = 0; i <= Scale; i++) {
            g.drawLine(S_x * i, 0, S_x * i, height);

        }
        for (int i = 0; i <= Scale; i++) {
            g.drawLine(0, S_y * i, width, S_y * i);
        }
        draw(g);
    }

    public void update(long elapsedTime) {
        alter(elapsedTime);
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    protected void onPressed(int keycode) {
        // TODO Auto-generated method stub

    }

    protected void onReleased(int keycode) {

    }

    public void addSprite(ISprite sprite) {

    }

    public void addSprite(ISprite sprite, int layer) {

    }

    public void dispose() {
    }

    public abstract void draw(GLEx g);

    public abstract void alter(long elapsedTime);

}
