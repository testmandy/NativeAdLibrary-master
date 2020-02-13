package me.dt.nativeadlibary.ad.data;

import com.inmobi.ads.InMobiNative;

import me.dt.nativeadlibary.util.AdProviderType;
import me.dt.nativeadlibary.ad.OfferType;
import me.dt.nativeadlibary.view.producer.InmobiViewProducer;

public class InmobiNativeAdData extends BaseNativeAdData {

    public InmobiNativeAdData(InMobiNative inMobiNative) {
        super(inMobiNative);
    }

    @Override
    public void doPack() {
        InMobiNative data = getAdData();
        title = data.getAdTitle();
        content = data.getAdDescription();
        logoUrl = data.getAdIconUrl();
        bigImgUrl = data.getAdLandingPageUrl();
        callToAction = data.getAdCtaText();
        offerType = OfferType.getOfferType(callToAction);
        adProviderType = AdProviderType.AD_PROVIDER_TYPE_INMOBI_NATIVE;
    }

    @Override
    public String getAdName() {
        return "Inmobi";
    }

    @Override
    public int getAdType() {
        return AdProviderType.AD_PROVIDER_TYPE_INMOBI_NATIVE;
    }

    @Override
    public InMobiNative getAdData() {
        return (InMobiNative) originData;
    }

    @Override
    public Class getViewProducerClass() {
        return InmobiViewProducer.class;
    }
}
