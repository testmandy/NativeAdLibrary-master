package me.dt.nativeadlibary.ad.loader;

import android.content.Context;
import android.util.Log;

import com.smaato.soma.AdDownloaderInterface;
import com.smaato.soma.AdListenerInterface;
import com.smaato.soma.ReceivedBannerInterface;
import com.smaato.soma.nativead.NativeAd;

import me.dt.nativeadlibary.util.L;
import me.dt.nativeadlibary.ad.AdCallbackListener;
import me.dt.nativeadlibary.ad.ErrorMsg;
import me.dt.nativeadlibary.ad.data.SmaatoNativeData;
import me.dt.nativeadlibary.config.NativeAdConfig;
import me.dt.nativeadlibary.view.ViewFactory;

public class SmaatoNativeAdLoader extends BaseNativeAdLoader {

    private int mAdViewType;

    private SmaatoNativeAdLoader() {
    }

    @Override
    public void initialized(Context context, NativeAdConfig nativeConfig) {
        if (!mHasInited) {
            super.initialized(context, nativeConfig);
            Log.i(getLoaderName(), "initialized");
            mHasInited = true;
        }
    }


    public void setAdViewType(int adViewType) {
        mAdViewType = adViewType;
    }

    @Override
    protected String getLoaderName() {
        return "SmaatoNativeAdLoader";
    }

    @Override
    protected boolean shouldLoadInBackground() {
        return false;
    }

    @Override
    protected void loadDirectly(final boolean isDownloadType, final AdCallbackListener adCallbackListener) {
        L.d(getLoaderName(), " loadDirectly");
        final NativeAd nativeAd = new NativeAd(mContext);
        nativeAd.getAdSettings().setPublisherId(Long.parseLong(mNativeConfig.key));  // replace with your PublisherId
        nativeAd.getAdSettings().setAdspaceId(Long.parseLong(mNativeConfig.placementId));

        final SmaatoNativeData smaatoNativeData = packData(nativeAd);
        smaatoNativeData.setAdView(ViewFactory.produce(mContext, smaatoNativeData, mAdViewType));
        nativeAd.setAdListener(new AdListenerInterface() {
            @Override
            public void onReceiveAd(AdDownloaderInterface adDownloaderInterface, ReceivedBannerInterface receivedBannerInterface) {
                L.d(getLoaderName(), " onReceiveAd : " + receivedBannerInterface);
                onLoadSuccess(smaatoNativeData, isDownloadType, adCallbackListener);
            }
        });
        nativeAd.asyncLoadNewBanner();

        super.loadDirectly(isDownloadType, adCallbackListener);
    }

    @Override
    protected SmaatoNativeData packData(Object originData) {
        return new SmaatoNativeData((NativeAd) originData);
    }

    @Override
    protected ErrorMsg getErrorMsg() {
        return new ErrorMsg("smaato no ad cache");
    }
}
