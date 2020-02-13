package me.dt.nativeadlibary.ad.loader;

import android.content.Context;
import android.util.Log;

import com.flurry.android.FlurryAgent;
import com.flurry.android.FlurryConsent;
import com.flurry.android.ads.FlurryAdErrorType;
import com.flurry.android.ads.FlurryAdNative;
import com.flurry.android.ads.FlurryAdNativeListener;

import java.util.HashMap;
import java.util.Map;

import me.dt.nativeadlibary.util.AdProviderType;
import me.dt.nativeadlibary.util.L;
import me.dt.nativeadlibary.ad.AdCallbackListener;
import me.dt.nativeadlibary.ad.ErrorMsg;
import me.dt.nativeadlibary.ad.data.FlurryNativeAdData;
import me.dt.nativeadlibary.config.NativeAdConfig;

public class FlurryNativeAdLoader extends BaseNativeAdLoader {

    private FlurryNativeAdLoader() {
    }

    @Override
    public void initialized(Context context, NativeAdConfig nativeConfig) {
        if (!mHasInited) {
            super.initialized(context, nativeConfig);
            new FlurryAgent.Builder()
                    .withLogEnabled(true)
                    .withCaptureUncaughtExceptions(true)
                    .withConsent(getFlurryConsentForGdpr())
                    .withLogLevel(Log.VERBOSE)
                    .build(context, mNativeConfig.key);

            mHasInited = true;
        }
    }

    @Override
    protected String getLoaderName() {
        return "FlurryNativeAdLoader";
    }

    @Override
    protected boolean shouldLoadInBackground() {
        return true;
    }

    @Override
    protected void loadDirectly(final boolean isDownloadType, final AdCallbackListener adCallbackListener) {

        FlurryAdNative nativeAd = new FlurryAdNative(mContext, mNativeConfig.placementId);
        L.d(getLoaderName(), " loadDirectly");
        nativeAd.setListener(new FlurryAdNativeListener() {
            @Override
            public void onFetched(FlurryAdNative adNative) {
                L.d(getLoaderName(), " onFetched : " + adNative);
                onLoadSuccess(packData(adNative), isDownloadType, adCallbackListener);
            }

            @Override
            public void onError(FlurryAdNative adNative, FlurryAdErrorType adErrorType, int errorCode) {
                L.d(getLoaderName(), " onError adErrorType : " + adErrorType + " errorCode = " + errorCode);
                onLoadFailed(isDownloadType, adCallbackListener, new ErrorMsg("adErrorType : " + adErrorType + " errorCode :" + errorCode));
            }

            @Override
            public void onShowFullscreen(FlurryAdNative adNative) {

            }

            @Override
            public void onCloseFullscreen(FlurryAdNative adNative) {

            }

            @Override
            public void onClicked(FlurryAdNative adNative) {
                adCallbackListener.onClick(AdProviderType.AD_PROVIDER_TYPE_FLURRY_NATIVE);
            }

            @Override
            public void onAppExit(FlurryAdNative adNative) {

            }

            @Override
            public void onImpressionLogged(FlurryAdNative arg0) {
                adCallbackListener.onImpression(AdProviderType.AD_PROVIDER_TYPE_FLURRY_NATIVE);
            }

            @Override
            public void onExpanded(FlurryAdNative flurryAdNative) {

            }

            @Override
            public void onCollapsed(FlurryAdNative flurryAdNative) {

            }
        });
        nativeAd.fetchAd();

        super.loadDirectly(isDownloadType, adCallbackListener);
    }


    @Override
    protected FlurryNativeAdData packData(Object originData) {
        return new FlurryNativeAdData((FlurryAdNative) originData);
    }

    public static FlurryConsent getFlurryConsentForGdpr() {

        Map<String, String> consentStrings = new HashMap<>();
        String iab = "BOUqqV-OUqqV-AOABAENBp____AhfAAA";
        consentStrings.put("IAB", iab);
        FlurryConsent flurryConsent = new FlurryConsent(true, consentStrings);
        return flurryConsent;
    }

    @Override
    protected ErrorMsg getErrorMsg() {
        return new ErrorMsg("flurry no ad cache");
    }
}
