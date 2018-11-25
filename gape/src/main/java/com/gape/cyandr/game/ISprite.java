package com.gape.cyandr.game;


import com.gape.cyandr.game.geom.RectBox;
import com.gape.cyandr.opengl.GLEx;
import com.gape.cyandr.opengl.LTexture;

public interface ISprite {

    void setLocation(float x, float y);

    void setSize(float width, float height);

    float getWidth();

    float getHeight();

    float getX();

    float getY();

    int getLayer();

    void setLayer(int layer);

    void draw(GLEx gl);

    boolean isVisible();

    void setVisible(boolean visible);

    void update(long elapsedTime);

    LTexture getImage();

    void setImage(LTexture image);

    RectBox getCollided();

    boolean isCollided(RectBox box);
}	
