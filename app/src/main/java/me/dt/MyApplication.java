package me.dt;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import me.dt.nativeadlibary.config.NativeAdLibManager;

public class MyApplication extends Application {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

        NativeAdLibManager.Builder builder = new NativeAdLibManager.Builder(this);
        builder.setCountryCode("cn")
                .setDebug(true)
                .start();
    }
}
