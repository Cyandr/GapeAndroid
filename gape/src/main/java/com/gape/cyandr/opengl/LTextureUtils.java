package com.gape.cyandr.opengl;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import com.gape.cyandr.game.core.Resources;

public class LTextureUtils {
    public static LTexture[] getSplitTexture(String filename, int width, int height) {
        return getSplitTexture(new LTexture(filename), width, height);
    }

    public static LTexture[] getSplitTexture(LTexture texture, int width, int height) {
        if (texture == null)
            return null;
        int col = texture.getWidth() / width;
        int row = texture.getHeight() / height;
        LTexture[] textures = new LTexture[row * col];
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                textures[r * col + c] = texture.getSubTexture(c * width, r * height, width, height);
            }
        }
        return textures;
    }

    public static LTexture[][] getSplit2Texture(String filename, int width, int height) {
        return getSplit2Texture(new LTexture(filename), width, height);
    }

    public static LTexture[][] getSplit2Texture(LTexture texture, int width, int height) {
        if (texture == null)
            return null;
        int col = texture.getWidth() / width;
        int row = texture.getHeight() / height;
        LTexture[][] textures = new LTexture[row][col];
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                textures[r][c] = texture.getSubTexture(c * width, r * height, width, height);
            }
        }
        return textures;
    }

    public static LTexture fiterColor(String filename, LColor color) {
        int c = color.getColor();
        Bitmap bitmap = Resources.getBitmap(filename);
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        canvas.drawBitmap(bitmap, 0, 0, null);
        bitmap.recycle();

        int[] pixels = new int[bmp.getWidth() * bmp.getHeight()];
        bmp.getPixels(pixels, 0, 0, 0, 0, bmp.getWidth(), bmp.getHeight());
        for (int i = 0; i < pixels.length; i++) {
            if (pixels[i] == c) {
                pixels[i] = 0xffffff;
            }
        }
        bmp.setPixels(pixels, 0, 0, 0, 0, bmp.getWidth(), bmp.getHeight());
        LTexture texture = new LTexture(bmp);
        return texture;
    }
}
