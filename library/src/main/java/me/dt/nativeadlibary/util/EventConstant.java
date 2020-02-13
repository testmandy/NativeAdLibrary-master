package me.dt.nativeadlibary.util;

import com.dt.client.android.analytics.DTEvent;

import java.util.Map;


public class EventConstant {

    public static final String CATEGORY_NATIVE = "adNativeCategory";
    public static final String ACTION_IMPRESSION = "impression";
    public static final String ACTION_CLICK = "click";

    public static String makeLabel(int adType, String adPosition) {
        return "adType" + adType + "_adPos" + adPosition;
    }

    public static void event(String category, String action, String label) {
        DTEvent.event(category, action, label);
    }

    public static void event(String category, String action, String label, Map map) {
        DTEvent.event(category, action, label, 0, map);
    }
}
