package me.dt.nativeadlibary.ad.loader;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.mopub.nativeads.MoPubNativeAdLoadedListener;
import com.mopub.nativeads.MoPubNativeAdPositioning;
import com.mopub.nativeads.MoPubStreamAdPlacer;
import com.mopub.nativeads.NativeAd;

import me.dt.nativeadlibary.util.L;
import me.dt.nativeadlibary.ad.AdCallbackListener;
import me.dt.nativeadlibary.ad.ErrorMsg;
import me.dt.nativeadlibary.ad.data.MopubNativeAdData;
import me.dt.nativeadlibary.config.NativeAdConfig;
import me.dt.nativeadlibary.view.producer.MopubViewProducer;

public class MopubNativeAdLoader extends BaseNativeAdLoader {

    private MopubNativeAdLoader() {
    }

    @Override
    public void initialized(Context context, NativeAdConfig nativeConfig) {
        if (!mHasInited) {
            super.initialized(context, nativeConfig);

            mHasInited = true;
        }
    }


    @Override
    protected String getLoaderName() {
        return "MopubNativeAdLoader";
    }

    @Override
    protected boolean shouldLoadInBackground() {
        return false;
    }

    @Override
    protected void loadDirectly(final boolean isDownloadType, final AdCallbackListener adCallbackListener) {
        L.d(getLoaderName(), " loadDirectly");
        MoPubNativeAdPositioning.MoPubClientPositioning clientPositioning = MoPubNativeAdPositioning.clientPositioning();
        clientPositioning.enableRepeatingPositions(2);

        final MoPubStreamAdPlacer mStreamAdPlacer = new MoPubStreamAdPlacer((Activity) mContext, clientPositioning);
        mStreamAdPlacer.setItemCount(2);

        mStreamAdPlacer.registerAdRenderer(new MopubViewProducer.MPNativeAdRenderer());
        mStreamAdPlacer.setAdLoadedListener(new MoPubNativeAdLoadedListener() {
            @Override
            public void onAdLoaded(int position) {
                L.d(getLoaderName(), " onAdLoaded");
                NativeAd nativeAd = (NativeAd) mStreamAdPlacer.getAdData(position);
                MopubNativeAdData mopubNativeAdData = packData(nativeAd);
                mopubNativeAdData.setStreamAdPlacer(mStreamAdPlacer);
                onLoadSuccess(mopubNativeAdData, isDownloadType, adCallbackListener);
            }

            @Override
            public void onAdRemoved(int position) {
                L.d(getLoaderName(), " ad impression mopub");
            }
        });
        try {
            mStreamAdPlacer.loadAds(mNativeConfig.key);
        } catch (OutOfMemoryError e) {
            Log.e(getLoaderName(), e.toString());
            return;
        }

        super.loadDirectly(isDownloadType, adCallbackListener);
    }

    @Override
    protected MopubNativeAdData packData(Object originData) {
        return new MopubNativeAdData((NativeAd) originData);
    }

    @Override
    protected ErrorMsg getErrorMsg() {
        return new ErrorMsg("mopub no ad cache");
    }
}
