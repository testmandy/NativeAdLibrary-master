package me.dt.nativeadlibary.view.producer;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import me.dt.nativeadlibary.R;
import me.dt.nativeadlibary.ad.data.BaseNativeAdData;
import me.dt.nativeadlibary.ad.data.SmaatoNativeData;

public class SmaatoViewProducer implements IProducer {

    private SmaatoViewProducer() {
    }

    @Override
    public View createBannerAdView(Context context, BaseNativeAdData data) {

        View adView = ((SmaatoNativeData) data).getAdView();
        return adView != null ? adView : createAdView(context, R.layout.smaato_banner_view, (SmaatoNativeData) data);
    }

    @Override
    public View createLoadingAdView(Context context, BaseNativeAdData data) {
        View adView = ((SmaatoNativeData) data).getAdView();
        return adView != null ? adView : createAdView(context, R.layout.smaato_loading_view, (SmaatoNativeData) data);
    }

    @Override
    public View createInterstialAdView(Context context, BaseNativeAdData data) {
        View adView = ((SmaatoNativeData) data).getAdView();
        return adView != null ? adView : createAdView(context, R.layout.smaato_interstial_view, (SmaatoNativeData) data);
    }

    @Override
    public View createInsSdkAdView(Context context, BaseNativeAdData data) {
        return null;
    }

    @Override
    public View createSplashAdView(Context context, BaseNativeAdData data) {
        return null;
    }

    @Override
    public View createLuckyBoxAdView(Context context, BaseNativeAdData data) {
        View adView = ((SmaatoNativeData) data).getAdView();
        return adView != null ? adView : createAdView(context, R.layout.smaato_lucky_box_view, (SmaatoNativeData) data);
    }

    @Override
    public View createVideoOfferAdView(Context context, BaseNativeAdData data) {
        return null;
    }

    @Override
    public View createSpecialOfferAdView(Context context, BaseNativeAdData data) {
        return null;
    }

    private View createAdView(Context context, @LayoutRes int resId, SmaatoNativeData smaatoNativeData) {

        View rootView = LayoutInflater.from(context).inflate(resId, null);

        Button ctaButton = rootView.findViewById(R.id.call_to_action);
        ImageView iconImage = rootView.findViewById(R.id.logo);
        ImageView mainImage = rootView.findViewById(R.id.mainImage);
        TextView nativeText = rootView.findViewById(R.id.content);
        TextView nativeTitle = rootView.findViewById(R.id.title);
        RelativeLayout nativeRelativeLayout = rootView.findViewById(R.id.nativeRelativeLayout);
        RatingBar ratingBar = rootView.findViewById(R.id.adRating);


        smaatoNativeData.getAdData().setClickToActionButton(ctaButton)
                .setTextView(nativeText)
                .setTitleView(nativeTitle)
                .setMainLayout(nativeRelativeLayout).setRatingBar(ratingBar);

        if (iconImage != null) {
            smaatoNativeData.getAdData().setIconImageView(iconImage);
        }
        if (mainImage != null) {
            smaatoNativeData.getAdData().setMainImageView(mainImage);
        }

        return rootView;
    }
}
