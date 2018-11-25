package com.gape.cyandr.opengl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import com.gape.cyandr.game.core.GameSystem;
import com.gape.cyandr.game.core.Resources;

import java.util.HashMap;

public class LTexture {

    private int width, height, pow2Width, pow2Height;
    private Bitmap mBitmap;

    private float MaxW, MaxH;
    private int TextureID;

    private LTexture parent = null;
    private float offsetX = 0, offsetY = 0;
    private int TexWidth, TexHeight;

    private HashMap<Integer, LTexture> subs = new HashMap<Integer, LTexture>();

    private int Unused = 0;

    private LTexture() {
    }

    public LTexture(Bitmap bmp) {
        this.width = bmp.getWidth();
        this.height = bmp.getHeight();
        TexHeight = height;
        TexWidth = width;
        pow2Width = pow2(width);
        pow2Height = pow2(height);
        MaxW = width / (float) pow2Width;
        MaxH = height / (float) pow2Height;
        Bitmap bitmap = Bitmap.createBitmap(pow2Width, pow2Height, bmp
                .hasAlpha() ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(bmp, 0, 0, null);

        bmp.recycle();
        bmp = null;
        this.mBitmap = bitmap;
    }

    public LTexture(String filename) {
        this(Resources.getBitmap(filename));
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addUnused() {
        Unused++;
    }

    public void bind(GLEx gl) {
        gl.glBind(this);
    }

    public void dispose() {
        if (this.parent == null) {
            for (LTexture sub : subs.values()) {
                sub.dispose(true);
            }
            if (GameSystem.getIsDrawing()) {
                GLEx.glex.delete(this.TextureID);
            } else {
                GameSystem.addDrawingRunnable(new Runnable() {
                    int id = TextureID;

                    public void run() {
                        // TODO Auto-generated method stub
                        GLEx.glex.delete(id);
                    }
                });
            }
        }
        this.recycle();
        TextureID = 0;
        subs.clear();
    }

    public void dispose(boolean isRemoveSub) {
        for (LTexture sub : subs.values()) {
            sub.dispose();
        }
        TextureID = 0;
        subs.clear();
    }

    public Bitmap getBitmap() {
        if (this.parent != null) {
            return parent.getBitmap();
        }
        return this.mBitmap;
    }

    public int getHeight() {
        return height;
    }

    public float getMaxH() {
        return MaxH;
    }

    public float getMaxW() {
        return MaxW;
    }

    public float getOX() {
        return offsetX / pow2Width;
    }

    public float getOY() {
        return offsetY / pow2Height;
    }

    public int getPow2Height() {
        return pow2Height;
    }

    public int getPow2Width() {
        return pow2Width;
    }

    public LTexture getSubTexture(int x, int y, int width, int height) {

        if (x < 0)
            x = 0;
        if (y < 0)
            y = 0;
        if (width > this.width)
            width = this.width;
        if (height > this.height)
            height = this.height;
        LTexture sub = subs.get(x * 100 + y + width + height);
        if (sub != null)
            return sub;

        sub = new LTexture();
        sub.parent = LTexture.this;
        if (TextureID != 0)
            sub.TextureID = this.TextureID;

        sub.pow2Width = this.pow2Width;
        sub.pow2Height = this.pow2Height;
        sub.width = width;
        sub.height = height;
        sub.offsetX = offsetX + x;
        sub.offsetY = offsetY + y;
        sub.TexWidth = TexWidth;
        sub.TexHeight = TexHeight;
        sub.MaxW = ((sub.offsetX + sub.width) / (float) this.pow2Width);
        sub.MaxH = ((sub.offsetY + sub.height) / (float) this.pow2Height);
        subs.put(x * 100 + y + width + height, sub);
        return sub;
    }

    public int getTextureID() {
        Unused = 0;
        return this.TextureID;
    }

    public void setTextureID(int id) {
        if (parent == null) {
            this.TextureID = id;
            for (LTexture sub : subs.values()) {
                sub.setTextureID(id);
            }
        } else if (this.parent.TextureID == 0) {
            this.parent.setTextureID(id);
        } else {
            this.TextureID = id;
            for (LTexture sub : subs.values()) {
                sub.setTextureID(id);
            }
        }
    }

    public boolean isUnused() {
        return this.parent == null && Unused > 20;
    }

    public int getWidth() {
        return width;
    }

    int pow2(int size) {
        int small = (int) (Math.log(size) / Math.log(2));
        if ((1 << small) >= size)
            return 1 << small;
        else
            return 1 << (small + 1);
    }

    public void recycle() {
        if (this.mBitmap != null) {
            if (this.mBitmap.isRecycled())
                this.mBitmap.recycle();
            this.mBitmap = null;
        }
    }

}
