package me.dt.nativeadlibary.view.producer;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.squareup.picasso.Picasso;

import java.util.List;

import me.dt.nativeadlibary.R;
import me.dt.nativeadlibary.ad.data.AdMobNativeAdData;
import me.dt.nativeadlibary.ad.data.BaseNativeAdData;
import me.dt.nativeadlibary.config.NativeAdLibManager;
import me.dt.nativeadlibary.util.L;
import me.dt.nativeadlibary.util.ViewUtil;

public class AdMobViewProducer implements IProducer {

    private static final String TAG = "AdMobViewProducer";

    private AdMobViewProducer() {
    }

    @Override
    public View createBannerAdView(Context context, BaseNativeAdData data) {
        UnifiedNativeAdView adView = createAdView(context, R.layout.admob_banner_view_bit_vpn, ((AdMobNativeAdData) data).getAdData());
        resetMediaViewSizeForBanner(adView.getMediaView());
        return adView;
    }

    @Override
    public View createLoadingAdView(Context context, BaseNativeAdData data) {
        return createAdView(context, R.layout.admob_loading_view_bit_vpn, ((AdMobNativeAdData) data).getAdData());
    }

    @Override
    public View createInterstialAdView(Context context, BaseNativeAdData data) {
        return createAdView(context, R.layout.admob_interstial_view_bit_vpn, ((AdMobNativeAdData) data).getAdData());
    }

    @Override
    public View createInsSdkAdView(Context context, BaseNativeAdData data) {
        return createAdView(context, R.layout.admob_ins_sdk_view, ((AdMobNativeAdData) data).getAdData());
    }

    @Override
    public View createSplashAdView(Context context, BaseNativeAdData data) {
        return createAdView(context, R.layout.admob_splash_view, ((AdMobNativeAdData) data).getAdData());
    }

    @Override
    public View createLuckyBoxAdView(Context context, BaseNativeAdData data) {
        return createAdView(context, R.layout.admob_lucky_box_view, ((AdMobNativeAdData) data).getAdData());
    }

    @Override
    public View createVideoOfferAdView(Context context, BaseNativeAdData data) {
        return createAdView(context, R.layout.admob_video_offer_view_bit_vpn, ((AdMobNativeAdData) data).getAdData());
    }

    @Override
    public View createSpecialOfferAdView(Context context, BaseNativeAdData data) {

        UnifiedNativeAdView adView = createAdView(context, R.layout.admob_special_offer_view_bit_vpn, ((AdMobNativeAdData) data).getAdData());
        resetMediaViewSizeForBanner(adView.getMediaView());
        return adView;
    }

    private UnifiedNativeAdView createAdView(Context context, @LayoutRes int resId, UnifiedNativeAd nativeContentAd) {

        UnifiedNativeAdView rootView = new UnifiedNativeAdView(context);
        rootView.addView(LayoutInflater.from(context).inflate(resId, null));

        TextView tvTitle = rootView.findViewById(R.id.title);
        TextView tvContent = rootView.findViewById(R.id.content);
        TextView tvSocial = rootView.findViewById(R.id.social);
        ImageView ivIcon = rootView.findViewById(R.id.logo);
        TextView tvCallAction = rootView.findViewById(R.id.call_to_action);
        MediaView mediaView = rootView.findViewById(R.id.media_view);

        rootView.setHeadlineView(tvTitle);
        rootView.setBodyView(tvContent);
        rootView.setCallToActionView(tvCallAction);

        TextView tvReward = rootView.findViewById(R.id.tv_jiang_li);
        if (tvReward != null) {
            tvReward.setText(6 + context.getString(R.string.credits));
        }

        if (tvSocial != null) {
            rootView.setAdvertiserView(tvSocial);
        }

        if (rootView.getHeadlineView() != null) {
            ((TextView) rootView.getHeadlineView()).setText(nativeContentAd.getHeadline());
        }
        if (rootView.getBodyView() != null) {
            ((TextView) rootView.getBodyView()).setText(nativeContentAd.getBody());
        }
        if (rootView.getCallToActionView() != null) {
            ((TextView) rootView.getCallToActionView()).setText(nativeContentAd.getCallToAction());
        }
        if (tvSocial != null) {
            if (rootView.getAdvertiserView() != null) {
                ((TextView) rootView.getAdvertiserView()).setText(nativeContentAd.getAdvertiser());
            }
        }


        if (ivIcon != null) {
            // Some aren't guaranteed, however, and should be checked.
            NativeAd.Image logoImage = nativeContentAd.getIcon();

            String hearUrl = "";
            if (logoImage != null) {
                hearUrl = "" + logoImage.getUri();
            } else {
                List<NativeAd.Image> images = nativeContentAd.getImages();

                if (images != null && images.size() > 0) {
                    hearUrl = "" + images.get(0).getUri();
                }
            }
            Picasso.with(NativeAdLibManager.getInstance().getContext()).load(hearUrl).into(ivIcon);
        }


        L.d(TAG, "banner resetViewWithData hasVideoContent");
        rootView.setMediaView(mediaView);
        rootView.setNativeAd(nativeContentAd);

        return rootView;
    }

    private void resetMediaViewSizeForBanner(MediaView mediaView) {
        ViewGroup.LayoutParams params = mediaView.getLayoutParams();
        params.width = ViewUtil.getScreenWidth(mediaView.getContext()) / 2;
        params.height = (int) (params.width / ViewUtil.AD_MAIN_VIEW_RATIO);
        mediaView.setLayoutParams(params);
    }
}
