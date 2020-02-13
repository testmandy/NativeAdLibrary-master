package me.dt.nativeadlibary.view.producer;

import android.content.Context;
import android.view.View;

import me.dt.nativeadlibary.ad.data.BaseNativeAdData;

public interface IProducer {

    View createBannerAdView(Context context, BaseNativeAdData data);

    View createLoadingAdView(Context context, BaseNativeAdData data);

    View createInterstialAdView(Context context, BaseNativeAdData data);

    View createInsSdkAdView(Context context, BaseNativeAdData data);

    View createSplashAdView(Context context, BaseNativeAdData data);

    View createLuckyBoxAdView(Context context, BaseNativeAdData data);

    View createVideoOfferAdView(Context context, BaseNativeAdData data);

    View createSpecialOfferAdView(Context context, BaseNativeAdData data);
}
