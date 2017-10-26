package com.webapp.xg.circleviewlib.util;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;

/**
 * Created by XG on 2017/10/25.
 * author by liuchao
 */

public class Util {
    /**
     * Drawable转bitmap
     *
     * @param drawableId
     * @return
     */
    public static Bitmap Drawable2Bitmap(Context c, int drawableId) {
        Resources r = c.getResources();
        Drawable drawable = r.getDrawable(drawableId);
        BitmapDrawable bd_b = (BitmapDrawable) drawable;
        Bitmap icon = bd_b.getBitmap();
        return icon;
    }

    public static int getDeviceWith(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        return width;
    }

    public static int getDeviceHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        return height;
    }
}
