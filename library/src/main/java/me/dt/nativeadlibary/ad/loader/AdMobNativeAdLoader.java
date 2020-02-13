package me.dt.nativeadlibary.ad.loader;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import me.dt.nativeadlibary.util.AdProviderType;
import me.dt.nativeadlibary.util.L;
import me.dt.nativeadlibary.ad.AdCallbackListener;
import me.dt.nativeadlibary.ad.ErrorMsg;
import me.dt.nativeadlibary.ad.data.AdMobNativeAdData;
import me.dt.nativeadlibary.config.NativeAdConfig;

import static com.google.android.gms.ads.formats.NativeAdOptions.ADCHOICES_TOP_LEFT;

public class AdMobNativeAdLoader extends BaseNativeAdLoader {

    private AdMobNativeAdLoader() {
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
        return "AdMobNativeAdLoader";
    }

    @Override
    protected ErrorMsg getErrorMsg() {
        return new ErrorMsg("admob no ad cache");
    }

    @Override
    protected boolean shouldLoadInBackground() {
        return false;
    }

    @Override
    protected void loadDirectly(final boolean isDownloadType, final AdCallbackListener adCallbackListener) {
        L.d(getLoaderName(), " loadDirectly");
        AdLoader.Builder builder = null;
        try {
            builder = new AdLoader.Builder(mContext, mNativeConfig.key);
        } catch (OutOfMemoryError e) {
            Log.e(getLoaderName(), e.toString());
            return;
        }

        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(final UnifiedNativeAd unifiedNativeAd) {
                if (unifiedNativeAd != null) {
                    L.d(getLoaderName(), " getHeadline = " + unifiedNativeAd.getHeadline());
                    L.d(getLoaderName(), " call to action  = " + unifiedNativeAd.getCallToAction());
                } else {
                    L.d(getLoaderName(), " unifiedNativeAd = null");
                }
                onLoadSuccess(packData(unifiedNativeAd), isDownloadType, adCallbackListener);
            }
        });

        builder.withNativeAdOptions(new NativeAdOptions.Builder().setAdChoicesPlacement(ADCHOICES_TOP_LEFT).build());

        AdLoader adLoader = builder.withAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdFailedToLoad(final int errorCode) {

                L.d(getLoaderName(), " onAdFailedToLoad:" + errorCode);
                onLoadFailed(isDownloadType, adCallbackListener, new ErrorMsg("errorCode : " + errorCode));
            }

            @Override
            public void onAdLeftApplication() {
            }

            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdClicked() {
                adCallbackListener.onClick(AdProviderType.AD_PROVIDER_TYPE_ADMOB_NATIVE);
                L.d(getLoaderName(), " onAdClicked admob");
            }

            @Override
            public void onAdImpression() {

                adCallbackListener.onImpression(AdProviderType.AD_PROVIDER_TYPE_ADMOB_NATIVE);

                L.d(getLoaderName(), " impression admob onImpressionLogged");

            }
        }).build();

        adLoader.loadAd(new AdRequest.Builder()
                .addTestDevice("E08EA76F253B23AB102A49E8DFBB80FD")
                .addTestDevice("BE26451445BA71C9D40DA02FBB544C75")
                .addTestDevice("194E9693F7ECD2D751E99AEB00F92765")
                .addTestDevice("107D8890B4521114DC00C40BB5FEC399")
                .addTestDevice("A364298F4D9B644B1971045393ACB0E2")
                .addTestDevice("5B768382953723E3C64E0DDF2C609FC4")
                .addTestDevice("3DD04F6F44424A9CEE6DB45A4BDBFEA8")
                .build());

        super.loadDirectly(isDownloadType, adCallbackListener);
    }

    @Override
    protected AdMobNativeAdData packData(Object originData) {
        return new AdMobNativeAdData((UnifiedNativeAd) originData);
    }

}
