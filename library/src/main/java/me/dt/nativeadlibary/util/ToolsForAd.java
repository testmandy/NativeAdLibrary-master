package me.dt.nativeadlibary.util;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.util.Date;

import me.dt.nativeadlibary.config.NativeAdLibManager;

public class ToolsForAd {

    public static boolean containInstallWords(String callToActionString) {
        return containsIgnoreCase(callToActionString, "Instala")
                || containsIgnoreCase(callToActionString, "Installent")
                || containsIgnoreCase(callToActionString, "Instalam")
                || containsIgnoreCase(callToActionString, "下载")
                || containsIgnoreCase(callToActionString, "安装")
                || containsIgnoreCase(callToActionString, "下載")
                || containsIgnoreCase(callToActionString, "install")
                || containsIgnoreCase(callToActionString, "Download")
                || containsIgnoreCase(callToActionString, "Get App")
                || containsIgnoreCase(callToActionString, "Play Now")
                || containsIgnoreCase(callToActionString, "Get it now")
                || containsIgnoreCase(callToActionString, "Play Game")
                || containsIgnoreCase(callToActionString, "kurmak")
                || containsIgnoreCase(callToActionString, "下載安裝")
                || containsIgnoreCase(callToActionString, "下载安装")
                || containsIgnoreCase(callToActionString, "learn more")
                || containsIgnoreCase(callToActionString, "Learn More")
                || containsIgnoreCase(callToActionString, "Open");
    }


    public static boolean containsIgnoreCase(String str, String searchStr) {
        if (str != null && searchStr != null) {
            int len = searchStr.length();
            int max = str.length() - len;

            for (int i = 0; i <= max; ++i) {
                if (str.regionMatches(true, i, searchStr, 0, len)) {
                    return true;
                }
            }

            return false;
        } else {
            return false;
        }
    }

    /**
     * 判断给定两个日期是否为: 同一天
     *
     * @param old_date 旧的时间
     * @param new_date 新的时间
     * @return false 非同一天
     */
    public static boolean getDifferedDayFromDay(long old_date, long new_date) {
        if (new_date == 0 || old_date == 0) {
            return false;
        }

        long diff = new_date - old_date;
        if (diff < 0)
            return false;
        long days = diff / (1000 * 60 * 60 * 24);

        if (days == 0) {
            Date oldDate = LongToDate(old_date);
            Date newDate = LongToDate(new_date);
            int yearOld = oldDate.getYear();
            int yearNew = newDate.getYear();
            if (yearOld != yearNew) {
                return false;
            }
            int monthOld = oldDate.getMonth();
            int monthNew = newDate.getMonth();
            if (monthOld != monthNew) {
                return false;
            }
            int dateOld = oldDate.getDate();
            int dateNew = newDate.getDate();
            int date_diff = dateNew - dateOld;
            if (date_diff < 0)
                return false;

            if (date_diff > 0) {
                return false;
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * 将数据库中的Long格式的Time，转为Date格式
     */
    private static Date LongToDate(long milliseconds) {
        Date date = new Date();
        date.setTime(milliseconds);

        return date;
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static synchronized String getVersionName() {
        try {
            Context context = NativeAdLibManager.getInstance().getContext();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static synchronized int getVersionCode() {
        try {
            Context context = NativeAdLibManager.getInstance().getContext();
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
