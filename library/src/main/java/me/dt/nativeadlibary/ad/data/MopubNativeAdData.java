package me.dt.nativeadlibary.ad.data;

import com.mopub.nativeads.MoPubStreamAdPlacer;
import com.mopub.nativeads.NativeAd;
import com.mopub.nativeads.StaticNativeAd;

import me.dt.nativeadlibary.util.AdProviderType;
import me.dt.nativeadlibary.ad.OfferType;
import me.dt.nativeadlibary.view.producer.MopubViewProducer;

public class MopubNativeAdData extends BaseNativeAdData {

    private MoPubStreamAdPlacer streamAdPlacer;

    public MopubNativeAdData(NativeAd nativeAd) {
        super(nativeAd);
    }

    public void setStreamAdPlacer(MoPubStreamAdPlacer streamAdPlacer) {
        this.streamAdPlacer = streamAdPlacer;
    }

    public MoPubStreamAdPlacer getStreamAdPlacer() {
        return streamAdPlacer;
    }

    @Override
    public void doPack() {
        StaticNativeAd data = (StaticNativeAd) getAdData().getBaseNativeAd();
        title = data.getTitle();
        content = data.getText();
        logoUrl = data.getIconImageUrl();
        bigImgUrl = data.getMainImageUrl();
        callToAction = data.getCallToAction();
        offerType = OfferType.getOfferType(callToAction);
        adProviderType = AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE;
    }

    @Override
    public String getAdName() {
        return "Mopub";
    }

    @Override
    public int getAdType() {
        return AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE;
    }

    @Override
    public NativeAd getAdData() {
        return (NativeAd) originData;
    }

    @Override
    public Class getViewProducerClass() {
        return MopubViewProducer.class;
    }
}
