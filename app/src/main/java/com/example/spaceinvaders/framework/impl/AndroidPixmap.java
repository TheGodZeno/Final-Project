package com.example.spaceinvaders.framework.impl;

import android.graphics.Bitmap;

import com.example.spaceinvaders.framework.Graphics.PixmapFormat;
import com.example.spaceinvaders.framework.Pixmap;

/*
 * This class represents a bitmap. On Android a bitmap is managed by a Bitmap class.
 * Each bitmap contains also the format on how its colors are stored (PixmapFormat).
 *
 * מחלקה זו מייצגת bitmap. בתוך Android ה-bitmap מנוהלת על ידי מחלקת Bitmap.
 * כל bitmap מכילה גם את התבנית על איך הצבעים מסודרים (PixmapFormat).
 */
public class AndroidPixmap implements Pixmap {
    Bitmap bitmap;
    PixmapFormat format;

    /*
     * Initializes the bitmap.
     *
     * מאתחל את ה-bitmap
     */
    public AndroidPixmap(Bitmap bitmap, PixmapFormat format) {
        this.bitmap = bitmap;
        this.format = format;
    }

    /*
     * Gets the width of the bitmap.
     *
     * מקבל את ה-width של ה-bitmap.
     */
    public int getWidth() {
        return bitmap.getWidth();
    }

    /*
     * Gets the height of the bitmap.
     *
     * מקבל את ה-height של ה-bitmap.
     */
    public int getHeight() {
        return bitmap.getHeight();
    }

    /*
     * Gets the format of the bitmap. Possible values are: ARGB8888, ARGB4444, RGB565.
     *
     * מקבל את התבנית של ה-bitmap.
     * ערכים אפשריים הם:ARGB8888, ARGB4444, RGB565.
     */
    public PixmapFormat getFormat() {
        return format;
    }

    /*
     * Disposes a bitmap.
     *
     * מסיר bitmap
     */
    public void dispose() {
        bitmap.recycle();
    }

    /*
     * Gets the color of the pixel in (x, y) position.
     *
     * מקבל את הצבע של הפיקסל בפוזיציה (x, y).
     */
    public int getColor(int x, int y) {
        return bitmap.getPixel(x, y);
    }

    /*
     * Sets the color of the pixel in (x, y) position.
     *
     * מגדיר את הצבע של הפיקסל בפוזיציה (x, y).
     */
    public void setColor(int x, int y, int color) {
        bitmap.setPixel(x, y, color);
    }
}
