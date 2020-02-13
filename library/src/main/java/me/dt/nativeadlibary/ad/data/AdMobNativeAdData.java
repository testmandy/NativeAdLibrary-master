package me.dt.nativeadlibary.ad.data;

import com.google.android.gms.ads.formats.UnifiedNativeAd;

import me.dt.nativeadlibary.util.AdProviderType;
import me.dt.nativeadlibary.ad.OfferType;
import me.dt.nativeadlibary.view.producer.AdMobViewProducer;

/**
 * Created by obo on 2017/4/19.
 */

public class AdMobNativeAdData extends BaseNativeAdData {

    public AdMobNativeAdData(UnifiedNativeAd admobNativeAd) {
        super(admobNativeAd);
    }

    @Override
    public void doPack() {
        UnifiedNativeAd data = getAdData();
        title = data.getHeadline();
        content = data.getBody();
        logoUrl = "";//data.getIcon().getUri().toString();
        bigImgUrl = data.getImages().get(0).getUri().toString();
        callToAction = data.getCallToAction();
        //packageName = data.getMediationAdapterClassName();
        offerType = OfferType.getOfferType(data.getCallToAction());
        adProviderType = AdProviderType.AD_PROVIDER_TYPE_ADMOB_NATIVE;
    }

    @Override
    public int getAdType() {
        return AdProviderType.AD_PROVIDER_TYPE_ADMOB_NATIVE;
    }

    @Override
    public UnifiedNativeAd getAdData() {
        return (UnifiedNativeAd) originData;
    }

    @Override
    public Class getViewProducerClass() {
        return AdMobViewProducer.class;
    }

    @Override
    public String getAdName() {

        String name;
        if (originData != null && (name = getAdData().getHeadline()) != null) {
            return name;
        }
        return "AdMob";
    }
}
