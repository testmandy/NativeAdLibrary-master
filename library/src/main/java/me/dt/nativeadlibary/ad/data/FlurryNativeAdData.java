package me.dt.nativeadlibary.ad.data;

import com.flurry.android.ads.FlurryAdNative;
import com.flurry.android.ads.FlurryAdNativeAsset;

import me.dt.nativeadlibary.util.AdProviderType;
import me.dt.nativeadlibary.ad.OfferType;
import me.dt.nativeadlibary.view.producer.FlurryViewProducer;

public class FlurryNativeAdData extends BaseNativeAdData {

    public FlurryNativeAdData(FlurryAdNative flurryAdNative) {
        super(flurryAdNative);
    }

    @Override
    public void doPack() {
        FlurryAdNative data = getAdData();
        title = data.getAsset("headline").getValue();
        content = data.getAsset("summary").getValue();
        logoUrl = data.getAsset("secHqBrandingLogo").getValue();
        String imageUrl_1200x627 = data.getAsset("secHqImage").getValue();
        if (imageUrl_1200x627 != null) {
            bigImgUrl = imageUrl_1200x627;
        } else {
            bigImgUrl = data.getAsset("secOrigImg").getValue();
        }
        callToAction = data.getAsset("callToAction").getValue();
        offerType = OfferType.getOfferType(callToAction);
        adProviderType = AdProviderType.AD_PROVIDER_TYPE_FLURRY_NATIVE;
    }

    @Override
    public String getAdName() {
        FlurryAdNativeAsset asset = getAdData().getAsset("headline");
        return asset != null ? asset.getValue() : "Flurry";
    }

    @Override
    public int getAdType() {
        return AdProviderType.AD_PROVIDER_TYPE_FLURRY_NATIVE;
    }

    @Override
    public FlurryAdNative getAdData() {
        return (FlurryAdNative) originData;
    }

    @Override
    public Class getViewProducerClass() {
        return FlurryViewProducer.class;
    }
}
