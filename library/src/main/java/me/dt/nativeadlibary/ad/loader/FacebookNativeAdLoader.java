package me.dt.nativeadlibary.ad.loader;

import android.content.Context;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdSettings;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdListener;

import java.util.ArrayList;
import java.util.List;

import me.dt.nativeadlibary.util.AdProviderType;
import me.dt.nativeadlibary.util.L;
import me.dt.nativeadlibary.ad.AdCallbackListener;
import me.dt.nativeadlibary.ad.ErrorMsg;
import me.dt.nativeadlibary.ad.data.FacebookNativeAdData;
import me.dt.nativeadlibary.config.NativeAdConfig;

public class FacebookNativeAdLoader extends BaseNativeAdLoader {

    private FacebookNativeAdLoader() {
    }

    @Override
    public void initialized(Context context, NativeAdConfig nativeConfig) {

        if (!mHasInited) {
            super.initialized(context, nativeConfig);
            List<String> devicesList = new ArrayList<>();
            devicesList.add("18ed7bed-7023-4a29-a971-6f2a93001c36");
            devicesList.add("7c7f7b72-47a5-456f-b57f-a71ccc209334");
            devicesList.add("5f808a20-a6d6-4a4d-a6f7-b17b4d52162a");
            devicesList.add("723dd74d-b383-4044-9575-e13edac0f84b");
            devicesList.add("8a3b97af-5ee7-4341-9fb0-396eff38947c");
            devicesList.add("57c6b739-019e-4ec8-9860-688f4b6b56ce");
            AdSettings.addTestDevices(devicesList);

            mHasInited = true;
        }
    }

    @Override
    protected String getLoaderName() {
        return "FacebookNativeAdLoader";
    }

    @Override
    protected boolean shouldLoadInBackground() {
        return false;
    }

    @Override
    protected void loadDirectly(final boolean isDownloadType, final AdCallbackListener adCallbackListener) {

        NativeAd nativeAd = new NativeAd(mContext, mNativeConfig.key);

        L.d(getLoaderName(), " loadDirectly");
        nativeAd.setAdListener(new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {

            }

            @Override
            public void onError(Ad ad, AdError adError) {
                L.d(getLoaderName(), " onError : " + adError.getErrorMessage());
                onLoadFailed(isDownloadType, adCallbackListener, new ErrorMsg(adError.getErrorMessage()));
            }

            @Override
            public void onAdLoaded(Ad ad) {
                L.d(getLoaderName(), " onAdLoaded : " + ad);
                if (ad instanceof NativeAd) {
                    NativeAd nativeLoadedAd = (NativeAd) ad;
                    onLoadSuccess(packData(nativeLoadedAd), isDownloadType, adCallbackListener);
                }
            }

            @Override
            public void onAdClicked(Ad ad) {
                adCallbackListener.onClick(AdProviderType.AD_PROVIDER_TYPE_FB_NATIVE);

                L.d(getLoaderName(), " facebookNative onAdClicked");
            }

            @Override
            public void onLoggingImpression(Ad ad) {

                adCallbackListener.onImpression(AdProviderType.AD_PROVIDER_TYPE_FB_NATIVE);

                L.d(getLoaderName(), " facebookNative onLoggingImpression");
            }
        });
        //NativeAd use Handler,so Looper should be added here
        // Initiate a request to load an ad.
        nativeAd.loadAd(NativeAd.MediaCacheFlag.ALL);

        super.loadDirectly(isDownloadType, adCallbackListener);
    }

    @Override
    protected FacebookNativeAdData packData(Object originData) {
        return originData != null ? new FacebookNativeAdData((NativeAd) originData) : null;
    }

    @Override
    protected ErrorMsg getErrorMsg() {
        return new ErrorMsg("facebook no ad cache");
    }
}
