package me.dt.nativeadlibary.util;


import android.content.Context;
import android.content.SharedPreferences;

import me.dt.nativeadlibary.config.NativeAdLibManager;

/**
 * Created by Shine on 2016/3/15.
 */
public class SharedPreferencesUtil {


    public static final String SHAREPREFERENCE_NAME = "native_ad_lib_sp";
    private static final String LAST_SHOW_NATIVE_AD_TIME = "last_show_native_ad_time";
    private static final String SHOW_NATIVE_AD_TIMES_TODAY = "show_native_ad_times_today";


    public static int getShowAdTimesToday(int adType) {
        SharedPreferences sharedPreferences = NativeAdLibManager.getInstance().getContext().getSharedPreferences(SHAREPREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(SHOW_NATIVE_AD_TIMES_TODAY + adType, 0);
    }

    public static void setShowAdTimesToday(int adType, int times) {
        SharedPreferences sharedPreferences = NativeAdLibManager.getInstance().getContext().getSharedPreferences(SHAREPREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SHOW_NATIVE_AD_TIMES_TODAY + adType, times);
        editor.apply();
    }


    public static long getLastShowAdTime(int adType) {
        SharedPreferences sharedPreferences = NativeAdLibManager.getInstance().getContext().getSharedPreferences(SHAREPREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(LAST_SHOW_NATIVE_AD_TIME + adType, 0);
    }

    public static void setLastShowAdTime(int adType, long curTime) {
        SharedPreferences sharedPreferences = NativeAdLibManager.getInstance().getContext().getSharedPreferences(SHAREPREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(LAST_SHOW_NATIVE_AD_TIME + adType, curTime);
        editor.apply();
    }
}
