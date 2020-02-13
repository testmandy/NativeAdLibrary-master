package me.dt.nativeadlibary.view.producer;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.AdIconView;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;

import java.util.ArrayList;
import java.util.List;

import me.dt.nativeadlibary.R;
import me.dt.nativeadlibary.ad.data.BaseNativeAdData;
import me.dt.nativeadlibary.ad.data.FacebookNativeAdData;

public class FacebookViewProducer implements IProducer {

    private FacebookViewProducer() {
    }

    @Override
    public View createBannerAdView(Context context, BaseNativeAdData data) {
        return createAdView(context, R.layout.facebook_banner_view, ((FacebookNativeAdData) data).getAdData());
    }

    @Override
    public View createLoadingAdView(Context context, BaseNativeAdData data) {
        return createAdView(context, R.layout.facebook_loading_view, ((FacebookNativeAdData) data).getAdData());
    }

    @Override
    public View createInterstialAdView(Context context, BaseNativeAdData data) {
        return createAdView(context, R.layout.facebook_interstial_view, ((FacebookNativeAdData) data).getAdData());
    }

    @Override
    public View createInsSdkAdView(Context context, BaseNativeAdData data) {
        return createAdView(context, R.layout.facebook_ins_sdk_view, ((FacebookNativeAdData) data).getAdData());
    }

    @Override
    public View createSplashAdView(Context context, BaseNativeAdData data) {
        return null;
    }

    @Override
    public View createLuckyBoxAdView(Context context, BaseNativeAdData data) {
        return createAdView(context, R.layout.facebook_lucky_box_view, ((FacebookNativeAdData) data).getAdData());
    }

    @Override
    public View createVideoOfferAdView(Context context, BaseNativeAdData data) {
        return null;
    }

    @Override
    public View createSpecialOfferAdView(Context context, BaseNativeAdData data) {
        return null;
    }

    private View createAdView(Context context, @LayoutRes int resId, NativeAd nativeAd) {

        View rootView = LayoutInflater.from(context).inflate(resId, null);
        AdIconView adIconView = rootView.findViewById(R.id.logo);
        TextView tvTitle = rootView.findViewById(R.id.title);
        TextView tvContent = rootView.findViewById(R.id.content);
        Button btnInstall = rootView.findViewById(R.id.btn_install);
        RelativeLayout llAdChoice = rootView.findViewById(R.id.layout_ad_choice);
        TextView tvSocial = rootView.findViewById(R.id.tv_social);
        MediaView mvMedia = rootView.findViewById(R.id.mv_media);
        TextView adLabel = rootView.findViewById(R.id.ad_tag);
        TextView sponsoredLabel = rootView.findViewById(R.id.native_ad_sponsored_label);
        tvTitle.setText(nativeAd.getAdvertiserName());
        tvContent.setText(nativeAd.getAdBodyText());
        btnInstall.setText(nativeAd.getAdCallToAction());

        TextView tvReward = rootView.findViewById(R.id.tv_jiang_li);

        if (tvReward != null) {
            tvReward.setText("6" + context.getString(R.string.credits));
        }

        if (adLabel != null) {
            adLabel.setText(nativeAd.getAdTranslation());
        }
        if (sponsoredLabel != null) {
            sponsoredLabel.setText(nativeAd.getSponsoredTranslation());
        }


        NativeAdLayout nativeAdLayout = rootView.findViewById(R.id.native_ad_container);
        if (llAdChoice != null && nativeAdLayout != null) {
            AdOptionsView adOptionsView = new AdOptionsView(context, nativeAd, nativeAdLayout);
            llAdChoice.removeAllViews();
            llAdChoice.addView(adOptionsView, 0);
        }

        if (tvSocial != null) {
            tvSocial.setText(nativeAd.getAdSocialContext());
        }
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(tvTitle);
        clickableViews.add(btnInstall);
        clickableViews.add(tvContent);

        if (adIconView != null) {
            clickableViews.add(adIconView);
        }
        nativeAd.registerViewForInteraction(rootView, mvMedia, adIconView, clickableViews);

        return rootView;
    }
}
