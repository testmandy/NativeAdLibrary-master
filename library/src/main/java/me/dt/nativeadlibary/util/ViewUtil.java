package me.dt.nativeadlibary.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

public class ViewUtil {

    public static final float AD_MAIN_VIEW_RATIO = 1.91f;
    public static final float DEFAULT_BANNER_CTA_WIDTH_RATIO = 0.27f;
    public static final float DEFAULT_VIDEO_OFFER_CTA_WIDTH_RATIO = 0.68f;

    private static int SCREEN_WIDTH;
    private static int SCREEN_HEIGHT;
    private static float DENSITY;

    public static int getScreenWidth(Context context) {
        if (SCREEN_WIDTH == 0) {
            Resources resources = context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            SCREEN_WIDTH = dm.widthPixels;
            SCREEN_HEIGHT = dm.heightPixels;
            DENSITY = dm.density;
            Log.d("ViewUtil", "SCREEN_WIDTH = " + SCREEN_WIDTH);
            Log.d("ViewUtil", "SCREEN_HEIGHT = " + SCREEN_HEIGHT);
        }
        return SCREEN_WIDTH;
    }

    public static int getScreenHeight(Context context) {
        if (SCREEN_HEIGHT == 0) {
            Resources resources = context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Log.d("ViewUtil", "dm = " + dm);
            SCREEN_WIDTH = dm.widthPixels;
            SCREEN_HEIGHT = dm.heightPixels;
            DENSITY = dm.density;
        }
        return SCREEN_HEIGHT;
    }

    public static float getDensity(Context context) {
        if (DENSITY == 0) {
            Resources resources = context.getResources();
            DisplayMetrics dm = resources.getDisplayMetrics();
            Log.d("ViewUtil", "dm = " + dm);
            SCREEN_WIDTH = dm.widthPixels;
            SCREEN_HEIGHT = dm.heightPixels;
            DENSITY = dm.density;
        }
        return DENSITY;
    }
}
