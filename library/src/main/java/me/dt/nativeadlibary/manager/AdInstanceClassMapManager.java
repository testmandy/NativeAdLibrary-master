package me.dt.nativeadlibary.manager;

import android.util.SparseArray;

import me.dt.nativeadlibary.util.AdProviderType;
import me.dt.nativeadlibary.ad.loader.AdMobNativeAdLoader;
import me.dt.nativeadlibary.ad.loader.BaseNativeAdLoader;
import me.dt.nativeadlibary.ad.loader.FacebookNativeAdLoader;
import me.dt.nativeadlibary.ad.loader.FlurryNativeAdLoader;
import me.dt.nativeadlibary.ad.loader.InmobiNativeAdLoader;
import me.dt.nativeadlibary.ad.loader.MopubNativeAdLoader;
import me.dt.nativeadlibary.ad.loader.SmaatoNativeAdLoader;


public class AdInstanceClassMapManager {
    private static SparseArray<Class<? extends BaseNativeAdLoader>> sAdClassMap = new SparseArray<>();

    static {
        sAdClassMap.put(AdProviderType.AD_PROVIDER_TYPE_ADMOB_NATIVE, AdMobNativeAdLoader.class);
        sAdClassMap.put(AdProviderType.AD_PROVIDER_TYPE_FLURRY_NATIVE, FlurryNativeAdLoader.class);
        sAdClassMap.put(AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE, MopubNativeAdLoader.class);
        sAdClassMap.put(AdProviderType.AD_PROVIDER_TYPE_FB_NATIVE, FacebookNativeAdLoader.class);
        sAdClassMap.put(AdProviderType.AD_PROVIDER_TYPE_INMOBI_NATIVE, InmobiNativeAdLoader.class);
        sAdClassMap.put(AdProviderType.AD_PROVIDER_TYPE_SMAATO_NATIVE, SmaatoNativeAdLoader.class);
    }

    public static Class getAdInstanceClassWithAdProviderType(int adProviderType) {
        return sAdClassMap.get(adProviderType);
    }
}
