package me.dt.nativeadlibary.ad.data;

import android.view.View;

import com.smaato.soma.nativead.NativeAd;

import me.dt.nativeadlibary.util.AdProviderType;
import me.dt.nativeadlibary.view.producer.SmaatoViewProducer;

public class SmaatoNativeData extends BaseNativeAdData {

    private View adView;

    public SmaatoNativeData(NativeAd nativeAd) {
        super(nativeAd);
    }

    @Override
    public void doPack() {
    }

    @Override
    public String getAdName() {
        return "Smaato";
    }

    @Override
    public int getAdType() {
        return AdProviderType.AD_PROVIDER_TYPE_SMAATO_NATIVE;
    }

    @Override
    public NativeAd getAdData() {
        return (NativeAd) originData;
    }

    @Override
    public Class getViewProducerClass() {
        return SmaatoViewProducer.class;
    }

    public void setAdView(View adView) {
        this.adView = adView;
    }

    public View getAdView() {
        return adView;
    }
}
