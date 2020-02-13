package me.dt.nativeadlibary;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.dt.client.android.analytics.DTEventManager;
import com.facebook.ads.AudienceNetworkAds;
import com.mopub.common.MoPub;
import com.mopub.common.SdkConfiguration;
import com.mopub.common.SdkInitializationListener;
import com.mopub.common.logging.MoPubLog;

import me.dt.nativeadlibary.util.L;

public class NativeAdProvider extends ContentProvider {

    private static final String TAG = "NativeAdProvider";

    @Override
    public boolean onCreate() {

        L.d(TAG, "onCreate");
        initialized();

        initDTEvent();
        return true;
    }

    private void initialized() {

        //Facebook SDK init
        if (!AudienceNetworkAds.isInitialized(getContext())) {
            AudienceNetworkAds.buildInitSettings(getContext())
                    .withInitListener(new AudienceNetworkAds.InitListener() {
                        @Override
                        public void onInitialized(AudienceNetworkAds.InitResult initResult) {
                            Log.d(TAG, initResult.getMessage());
                        }
                    })
                    .initialize();
        }

        SdkConfiguration.Builder configBuilder = new SdkConfiguration.Builder("11a17b188668469fb0412708c3d16813");
        configBuilder.withLogLevel(MoPubLog.LogLevel.DEBUG);
        MoPub.initializeSdk(getContext(), configBuilder.build(), new SdkInitializationListener() {
            @Override
            public void onInitializationFinished() {
                Log.i(TAG, "initialized");
            }
        });
    }

    private void initDTEvent() {
        try {
            DTEventManager.Builder builder = new DTEventManager.Builder((Application) getContext().getApplicationContext());
            builder.setPushUrl("https://dt-apigateway-log.dt-pn1.com/report/log")
                    .setAppName("Dingtone")
                    .setDebug(true)//是否是debug
                    .setCountryCode("cn")
                    .setDeviceId("111111111")
                    .setUserId(1111111)
                    .setPushLimitNum(10)
                    .setPushTime(1)
                    .start();
        } catch (Exception e) {
        }
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
