package me.dt.nativeadlibary.ad.data;

import com.facebook.ads.NativeAd;

import me.dt.nativeadlibary.util.AdProviderType;
import me.dt.nativeadlibary.ad.OfferType;
import me.dt.nativeadlibary.view.producer.FacebookViewProducer;

public class FacebookNativeAdData extends BaseNativeAdData {

    public FacebookNativeAdData(NativeAd nativeAd) {
        super(nativeAd);
    }

    @Override
    public void doPack() {
        NativeAd data = getAdData();
        title = data.getAdvertiserName();
        content = data.getAdBodyText();
        callToAction = data.getAdCallToAction();
        offerType = OfferType.getOfferType(data.getAdCallToAction());
        adProviderType = AdProviderType.AD_PROVIDER_TYPE_FB_NATIVE;
    }

    @Override
    public String getAdName() {
        return getAdData().getAdvertiserName();
    }

    @Override
    public int getAdType() {
        return AdProviderType.AD_PROVIDER_TYPE_FB_NATIVE;
    }

    @Override
    public NativeAd getAdData() {
        return (NativeAd) originData;
    }

    @Override
    public Class getViewProducerClass() {
        return FacebookViewProducer.class;
    }

}
