package me.dt.nativeadlibary.view;

import android.content.Context;
import android.view.View;

import me.dt.nativeadlibary.ad.data.BaseNativeAdData;
import me.dt.nativeadlibary.view.producer.IProducer;

public class ViewFactory {

    public static View produce(Context context, BaseNativeAdData data, int adViewType) {

        IProducer producer = ProducerGenerator.generate(data.getAdType(), data.getViewProducerClass());
        if (producer == null) {
            return null;
        }

        View view;
        switch (adViewType) {
            case AdViewType.BANNER:
                view = producer.createBannerAdView(context, data);
                break;

            case AdViewType.LOADING:
                view = producer.createLoadingAdView(context, data);
                break;

            case AdViewType.INTERSTITIAL:
                view = producer.createInterstialAdView(context, data);
                break;

            case AdViewType.INS_SDK:
                view = producer.createInsSdkAdView(context, data);
                break;

            case AdViewType.SPLASH:
                view = producer.createSplashAdView(context, data);
                break;

            case AdViewType.LUCKY_BOX:
                view = producer.createLuckyBoxAdView(context, data);
                break;

            case AdViewType.VIDEO_OFFER:
                view = producer.createVideoOfferAdView(context, data);
                break;

            case AdViewType.SPECIAL_OFFER:
                view = producer.createSpecialOfferAdView(context, data);
                break;

            default:
                view = null;
        }

        return view;
    }
}
