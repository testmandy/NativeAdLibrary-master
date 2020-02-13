package me.dt.nativeadlibary.util;


import android.os.Build;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;


public class RootUtil {
    public static final String TAG = "RootUtil";

    private static class RootUtilHolder {
        private static final RootUtil INSTANCE = new RootUtil();
    }

    public static RootUtil getInstance() {
        return RootUtilHolder.INSTANCE;
    }

    private RootUtil() {
        isDeviceRooted = checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
    }

    private boolean isDeviceRooted = false;

    public boolean isDeviceRooted() {
        return isDeviceRooted;
    }

    private static boolean checkRootMethod1() {
        String buildTags = android.os.Build.TAGS;
        return buildTags != null && buildTags.contains("test-keys");
    }

    private static boolean checkRootMethod2() {
        String[] paths = {"/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    private static boolean checkRootMethod3() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(new String[]{"/system/xbin/which", "su"});
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            return in.readLine() != null;
        } catch (Throwable t) {
            return false;
        } finally {
            if (process != null) process.destroy();
        }
    }

    public static boolean isRunningOnEmulator() {

        boolean result =
                Build.FINGERPRINT.startsWith("generic")
//	            ||Build.FINGERPRINT.startsWith("unknown") // Meizu Mx Pro will return unknown so comment it
                        || Build.MODEL.contains("google_sdk")
                        || Build.MODEL.contains("Emulator")
                        || Build.MODEL.contains("Android SDK built for x86")
                        || Build.MANUFACTURER.contains("Genymotion");

        if (result)
            return true;
        result |= Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic");
        if (result)
            return true;
        result |= "google_sdk".equals(Build.PRODUCT);
        return result;
    }
}
