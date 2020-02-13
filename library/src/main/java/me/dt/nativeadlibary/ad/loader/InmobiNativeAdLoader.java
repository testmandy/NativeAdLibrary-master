package me.dt.nativeadlibary.ad.loader;

import android.content.Context;
import android.util.Log;

import com.inmobi.ads.InMobiAdRequestStatus;
import com.inmobi.ads.InMobiNative;
import com.inmobi.ads.listeners.NativeAdEventListener;
import com.inmobi.ads.listeners.VideoEventListener;
import com.inmobi.sdk.InMobiSdk;

import org.json.JSONException;
import org.json.JSONObject;

import me.dt.nativeadlibary.util.AdProviderType;
import me.dt.nativeadlibary.util.L;
import me.dt.nativeadlibary.ad.AdCallbackListener;
import me.dt.nativeadlibary.ad.ErrorMsg;
import me.dt.nativeadlibary.ad.data.InmobiNativeAdData;
import me.dt.nativeadlibary.config.DTConstant;
import me.dt.nativeadlibary.config.NativeAdConfig;

public class InmobiNativeAdLoader extends BaseNativeAdLoader {


    private NativeAdEventListener mNativeAdEventListener;

    private InmobiNativeAdLoader() {
    }

    @Override
    public void initialized(Context context, NativeAdConfig nativeConfig) {
        if (!mHasInited) {
            super.initialized(context, nativeConfig);
            InMobiSdk.setLogLevel(InMobiSdk.LogLevel.DEBUG);
            JSONObject consent = new JSONObject();
            try {
                // Provide correct consent value to sdk which is obtained by User
                consent.put(InMobiSdk.IM_GDPR_CONSENT_AVAILABLE, true);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            InMobiSdk.init(context, mNativeConfig.key, consent);

            mHasInited = true;
        }
    }

    @Override
    protected String getLoaderName() {
        return "InmobiNativeAdLoader";
    }

    @Override
    protected boolean shouldLoadInBackground() {
        return false;
    }

    @Override
    protected void loadDirectly(final boolean isDownloadType, final AdCallbackListener adCallbackListener) {
        L.d(getLoaderName(), " loadDirectly");
        mNativeAdEventListener = new NativeAdEventListener() {
            @Override
            public void onAdReceived(InMobiNative inMobiNative) {
                super.onAdReceived(inMobiNative);
                L.d(getLoaderName(), " onAdReceived : " + inMobiNative);
                onLoadSuccess(packData(inMobiNative), isDownloadType, adCallbackListener);
            }

            @Override
            public void onAdLoadSucceeded(InMobiNative inMobiNative) {
                super.onAdLoadSucceeded(inMobiNative);
                L.d(DTConstant.TAG, getLoaderName() + " onAdLoadSucceeded : " + inMobiNative);
            }

            @Override
            public void onAdLoadFailed(InMobiNative inMobiNative, InMobiAdRequestStatus inMobiAdRequestStatus) {
                super.onAdLoadFailed(inMobiNative, inMobiAdRequestStatus);
                L.d(getLoaderName(), " onAdLoadFailed : " + inMobiAdRequestStatus.getMessage());
                onLoadFailed(isDownloadType, adCallbackListener, new ErrorMsg("error: " + inMobiAdRequestStatus.getMessage()));
            }

            @Override
            public void onAdImpressed(InMobiNative inMobiNative) {
                super.onAdImpressed(inMobiNative);
                adCallbackListener.onImpression(AdProviderType.AD_PROVIDER_TYPE_INMOBI_NATIVE);
            }

            @Override
            public void onAdClicked(InMobiNative inMobiNative) {
                super.onAdClicked(inMobiNative);
                adCallbackListener.onClick(AdProviderType.AD_PROVIDER_TYPE_INMOBI_NATIVE);
            }
        };

        InMobiNative mCurrentNativeAd = new InMobiNative(mContext, Long.parseLong(mNativeConfig.placementId), mNativeAdEventListener);
        mCurrentNativeAd.setVideoEventListener(new VideoEventListener() {
            @Override
            public void onVideoCompleted(InMobiNative inMobiNative) {
                super.onVideoCompleted(inMobiNative);
                Log.d(getLoaderName(), "Video completed");
            }

            @Override
            public void onVideoSkipped(InMobiNative inMobiNative) {
                super.onVideoSkipped(inMobiNative);
                Log.d(getLoaderName(), "Video skipped");
            }

            @Override
            public void onAudioStateChanged(InMobiNative inMobiNative, boolean b) {
                super.onAudioStateChanged(inMobiNative, b);
                Log.d(getLoaderName(), "Audio state changed");
            }
        });
        mCurrentNativeAd.load();

        super.loadDirectly(isDownloadType, adCallbackListener);
    }

    @Override
    protected InmobiNativeAdData packData(Object originData) {
        return new InmobiNativeAdData((InMobiNative) originData);
    }

    @Override
    protected ErrorMsg getErrorMsg() {
        return new ErrorMsg("inmobi no ad cache");
    }
}
