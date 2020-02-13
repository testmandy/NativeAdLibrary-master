package me.dt.nativeadlibary.ad.loader;

import android.content.Context;

import me.dt.nativeadlibary.ad.AdCallbackListener;
import me.dt.nativeadlibary.ad.data.BaseNativeAdData;
import me.dt.nativeadlibary.config.NativeAdConfig;

/**
 * Created by Joy on 2019-10-17
 */
public interface INativeAdLoader {

    void initialized(Context context, NativeAdConfig nativeConfig);

    void startLoad(AdCallbackListener adCallbackListener);

    void startLoad(boolean isDownloadType, AdCallbackListener adCallbackListener);

    void addCacheAd(BaseNativeAdData baseNativeAdData);

    BaseNativeAdData getCacheAd(boolean isDownloadType);

}
