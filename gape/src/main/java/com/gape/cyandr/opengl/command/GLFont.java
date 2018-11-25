package com.gape.cyandr.opengl.command;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.gape.cyandr.opengl.GLEx;
import com.gape.cyandr.opengl.LTexture;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class GLFont {

    public static HashMap<String, LTexture> data = new HashMap<String, LTexture>();
    private static ArrayList<String> UnusedList = new ArrayList<String>();

    public static Bitmap getImage(String str, int fontsize, Paint paint,
                                  int maxWidth) {

        String[] text = StringFormat(str, maxWidth, fontsize);
        int[] count = getLinesMaxLength(text);
        Bitmap bitmap = Bitmap.createBitmap(count[0] * (fontsize / 2)
                        + count[1] * fontsize + 5, (text.length) * fontsize,
                Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        paint.setTextSize(fontsize);
        for (int i = 0; i < text.length; i++) {
            canvas.drawText(text[i], 0, (i + 1) * fontsize - 3, paint);
        }
        return bitmap;
    }

    public static void drawString(GLEx gl, String str, float x, float y, Paint paint, int maxWidth) {

        if (str == null || str.trim() == "")
            return;
        String key = str.trim().toLowerCase();
        LTexture texture = data.get(key);
        if (texture == null) {
            Bitmap bitmap = getImage(str, (int) paint.getTextSize(), paint, maxWidth);
            texture = new LTexture(bitmap);
            data.put(key, texture);
        }
        gl.drawTexture(texture, x, y);
    }

    public static void update() {
        for (String key : data.keySet()) {
            LTexture texture = data.get(key);
            if (texture.isUnused()) {
                UnusedList.add(key);
            } else {
                texture.addUnused();
            }
        }
        for (String key : UnusedList) {
            GLEx.glex.delete(data.remove(key));
        }
        UnusedList.clear();
    }

    /**
     * �������������Ǹ�������Ӣ�ĵ�����
     *
     * @param lines
     * @return introduction[0] Ӣ�ĵ����� introduction[1] ���ĵ�����
     */
    public static int[] getLinesMaxLength(String[] lines) {
        int max = 0, index = 0;
        for (int i = 0; i < lines.length; i++) {
            if (max < lines[i].getBytes().length) {
                max = lines[i].getBytes().length;
                index = i;
            }
        }
        int[] count = new int[2];
        for (int i = 0; i < lines[index].length(); i++) {
            if (lines[index].charAt(i) > 255) {
                count[1]++;
            } else {
                count[0]++;
            }
        }
        return count;
    }

    public static String[] StringFormat(String text, int maxWidth, int fontSize) {

        String[] result = null;
        Vector<String> tempR = new Vector<String>();
        int lines = 0;
        int len = text.length();
        int index0 = 0;
        int index1 = 0;
        boolean wrap;
        while (true) {
            int widthes = 0;
            wrap = false;
            for (index0 = index1; index1 < len; index1++) {
                if (text.charAt(index1) == '\n') {
                    index1++;
                    wrap = true;
                    break;
                }
                widthes = fontSize + widthes;
                if (widthes > maxWidth) {
                    break;
                }
            }
            lines++;
            if (wrap) {
                tempR.addElement(text.substring(index0, index1 - 1));
            } else {
                tempR.addElement(text.substring(index0, index1));
            }
            if (index1 >= len) {
                break;
            }
        }
        result = new String[lines];
        tempR.copyInto(result);
        return result;
    }

}
