package com.gape.cyandr.game;


import com.gape.cyandr.game.geom.RectBox;
import com.gape.cyandr.opengl.GLEx;
import com.gape.cyandr.opengl.LTexture;

public class Sprite implements ISprite {

    private float x, y, width, height;
    private LTexture image;
    private int layer;
    private boolean visible = true;
    private Animation animation;
    private RectBox rectBox;

    public Sprite(String filename) {
        image = new LTexture(filename);
    }

    public void setLocation(float x, float y) {
        // TODO Auto-generated method stub
        this.x = x;
        this.y = y;
    }

    public void setSize(float width, float height) {
        // TODO Auto-generated method stub
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        // TODO Auto-generated method stub
        return this.width;
    }

    public float getHeight() {
        // TODO Auto-generated method stub
        return this.height;
    }

    public float getX() {
        // TODO Auto-generated method stub
        return this.x;
    }

    public float getY() {
        // TODO Auto-generated method stub
        return this.y;
    }

    public int getLayer() {
        // TODO Auto-generated method stub
        return this.layer;
    }

    public void setLayer(int layer) {
        // TODO Auto-generated method stub
        this.layer = layer;
    }

    public void draw(GLEx gl) {
        // TODO Auto-generated method stub
        if (!visible)
            return;
        if (animation == null) {
            gl.drawTexture(this.image, x, y, width, height);
        } else {
            gl.drawTexture(animation.getImage(), x, y, width, height);
        }
    }

    public void update(long elapsedTime) {
        // TODO Auto-generated method stub
        if (animation != null)
            animation.update(elapsedTime);
    }

    public LTexture getImage() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setImage(LTexture image) {
        // TODO Auto-generated method stub
        this.image = image;
    }

    public RectBox getCollided() {
        // TODO Auto-generated method stub
        if (rectBox == null)
            rectBox = new RectBox(x, y, width, height);
        rectBox.moveTO(x, y);
        return rectBox;
    }

    public Animation getAnimation() {
        // TODO Auto-generated method stub
        return animation;
    }

    public void setAnimation(String filename, int width, int height) {
        animation = new Animation(filename, width, height, 150);
        AnimationManager.add(animation);
    }

    public boolean isVisible() {
        // TODO Auto-generated method stub
        return visible;
    }

    public void setVisible(boolean visible) {
        // TODO Auto-generated method stub
        this.visible = visible;
    }

    public boolean isCollided(RectBox box) {
        // TODO Auto-generated method stub
        return getCollided().intersect(box);
    }
}
