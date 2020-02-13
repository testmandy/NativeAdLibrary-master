package me.dt.nativeadlibary.util;

import android.util.Log;

import me.dt.nativeadlibary.config.DTConstant;

/**
 * Created by joybar on 2017/5/17.
 */

public class L {

    private static int LEVEL = Log.DEBUG;//默认level
    private static String TAG = "print";//默认tag
    private static boolean IS_DEBUG = DTConstant.DEVELOP_MODE;//默认显示log


    public static void v(Object msg) {
        performPrint(Log.VERBOSE, TAG, msg.toString());
    }

    public static void d( Object msg) {
        performPrint(Log.DEBUG, TAG, msg.toString());
    }

    public static void i( Object msg) {
        performPrint(Log.INFO, TAG, msg.toString());
    }

    public static void w( Object msg) {
        performPrint(Log.WARN, TAG, msg.toString());
    }

    public static void e(Object msg) {
        performPrint(Log.ERROR, TAG, msg.toString());
    }

    public static void a(Object msg) {
        performPrint(Log.ASSERT, TAG, msg.toString());
    }




    public static void v(String tag, Object msg) {
        performPrint(Log.VERBOSE, DTConstant.TAG + " " + tag, msg.toString());
    }

    public static void d(String tag, Object msg) {
        performPrint(Log.DEBUG, DTConstant.TAG + " " + tag, msg.toString());
    }

    public static void i(String tag, Object msg) {
        performPrint(Log.INFO, DTConstant.TAG + " " + tag, msg.toString());
    }

    public static void w(String tag, Object msg) {
        performPrint(Log.WARN, DTConstant.TAG + " " + tag, msg.toString());
    }

    public static void e(String tag, Object msg) {
        performPrint(Log.ERROR, DTConstant.TAG + " " + tag, msg.toString());
    }


    public static void a(String tag, Object msg) {
        performPrint(Log.ASSERT, DTConstant.TAG + " " + tag, msg.toString());
    }




    //设置默认的Level
    public static void setDefaultLevel(int level) {
        L.LEVEL = level;
    }

    //设置默认的TAG
    public static void setDefaultTag(String tag) {
        L.TAG = tag;
    }

    //设置是否是Debug模式
    public static void setIsDebug(boolean isDebug) {
        L.IS_DEBUG = isDebug;
    }

    //打印
    public static void print(String msg) {
        performPrint(LEVEL, TAG, msg);
    }

    //打印-自定义Tag
    public static void print(String tag, String msg) {
        performPrint(LEVEL, tag, msg);
    }

    //打印-自定义Level
    public static void print(int level, String msg) {
        performPrint(level, TAG, msg);
    }

    //打印-自定义Tag,自定义Level
    public static void print(int level, String tag, String msg) {
        performPrint(level, tag, msg);
    }

    //执行打印
    private static void performPrint(int level, String tag, String msg) {
        //非Debug版本，则不打印日志
        if (!IS_DEBUG) {
            return;
        }
        String threadName = Thread.currentThread().getName();
        String lineIndicator = getLineIndicator();
        Log.println(level, tag, threadName + " " + lineIndicator + " " + msg);
    }

    //获取行所在的方法指示
    //获取行所在的方法指示
    private static String getLineIndicator() {
        //3代表方法的调用深度：0-getLineIndicator，1-performPrint，2-print，3-调用该工具类的方法位置
        StackTraceElement element = ((new Exception()).getStackTrace())[3];
        StringBuffer sb = new StringBuffer("(")
                .append(element.getFileName()).append(":")
                .append(element.getLineNumber()).append(").")
                .append(element.getMethodName()).append(":");
        return sb.toString();
    }

}
