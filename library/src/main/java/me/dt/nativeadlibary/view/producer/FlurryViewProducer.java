package me.dt.nativeadlibary.view.producer;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import me.dt.nativeadlibary.R;
import me.dt.nativeadlibary.ad.data.BaseNativeAdData;
import me.dt.nativeadlibary.ad.data.FlurryNativeAdData;
import me.dt.nativeadlibary.config.NativeAdLibManager;
import me.dt.nativeadlibary.util.ViewUtil;

public class FlurryViewProducer implements IProducer {

    private FlurryViewProducer() {
    }

    @Override
    public View createBannerAdView(Context context, BaseNativeAdData data) {
        View rootView = createAdView(context, R.layout.flurry_banner_view_for_bit_vpn, (FlurryNativeAdData) data);
        resetBannerSize(rootView);
        return rootView;
    }

    @Override
    public View createLoadingAdView(Context context, BaseNativeAdData data) {
        View rootView = createAdView(context, R.layout.flurry_loading_view_for_bit_vpn, (FlurryNativeAdData) data);
        resetLoadingView(rootView);
        return rootView;
    }

    @Override
    public View createInterstialAdView(Context context, BaseNativeAdData data) {
        View rootView = createAdView(context, R.layout.flurry_interstial_view_for_bit_vpn, (FlurryNativeAdData) data);
        resetInterstialView(rootView);
        return rootView;
    }

    @Override
    public View createInsSdkAdView(Context context, BaseNativeAdData data) {
        return createAdView(context, R.layout.flurry_ins_sdk_view, (FlurryNativeAdData) data);
    }

    @Override
    public View createSplashAdView(Context context, BaseNativeAdData data) {
        return createAdView(context, R.layout.flurry_splash_view, (FlurryNativeAdData) data);
    }

    @Override
    public View createLuckyBoxAdView(Context context, BaseNativeAdData data) {
        return createAdView(context, R.layout.flurry_lucky_box_view, (FlurryNativeAdData) data);
    }

    @Override
    public View createVideoOfferAdView(Context context, BaseNativeAdData data) {
        View rootView = createAdView(context, R.layout.flurry_video_offer_view_for_bit_vpn, (FlurryNativeAdData) data);
        resetInterstialView(rootView);
        return rootView;
    }

    @Override
    public View createSpecialOfferAdView(Context context, BaseNativeAdData data) {
        View rootView = createAdView(context, R.layout.flurry_special_offer_view_for_bit_vpn, (FlurryNativeAdData) data);
        resetBannerSize(rootView);
        return rootView;
    }

    public View createAdView(Context context, @LayoutRes int resId, FlurryNativeAdData flurryAdNative) {

        View rootView = LayoutInflater.from(context).inflate(resId, null);

        ImageView logo = rootView.findViewById(R.id.logo);
        TextView title = rootView.findViewById(R.id.title);
        TextView content = rootView.findViewById(R.id.content);
        LinearLayout starburst = rootView.findViewById(R.id.ad_starburst);
        //Button collapse = rootView.findViewById(R.id.bt_collapse);
        RelativeLayout rlTrackingView = rootView.findViewById(R.id.rl_tracking_view);
        ImageView contentImg = rootView.findViewById(R.id.native_main_image);

        TextView tvCallToAction = rootView.findViewById(R.id.call_to_action);

        TextView tvReward = rootView.findViewById(R.id.tv_jiang_li);

        if (title != null) {
            title.setText(flurryAdNative.getTitle());
        }
        if (content != null) {
            content.setText(flurryAdNative.getContent());
        }

        if (starburst != null) {
            starburst.setVisibility(View.VISIBLE);
        }
        if (logo != null) {
            Picasso.with(NativeAdLibManager.getInstance().getContext()).load(flurryAdNative.getLogoUrl()).into(logo);
        }

        if (contentImg != null) {
            Picasso.with(NativeAdLibManager.getInstance().getContext()).load(flurryAdNative.getBigImgUrl()).into(contentImg);
        }

        flurryAdNative.getAdData().setCollapsableTrackingView(rlTrackingView, null);

        if (tvReward != null) {
            tvReward.setText("6" + context.getString(R.string.credits));
        }

        if (tvCallToAction != null) {
            tvCallToAction.setText(flurryAdNative.getCallToAction());
        }

        return rootView;
    }

    private void resetBannerSize(View rootView) {
        View tvCallToAction = rootView.findViewById(R.id.call_to_action);
        ViewGroup.LayoutParams params = tvCallToAction.getLayoutParams();
        params.width = (int) (ViewUtil.getScreenWidth(rootView.getContext()) * ViewUtil.DEFAULT_BANNER_CTA_WIDTH_RATIO);
        tvCallToAction.setLayoutParams(params);
    }

    private void resetLoadingView(View rootView) {
        View contentImg = rootView.findViewById(R.id.native_main_image);
        ViewGroup.LayoutParams params = contentImg.getLayoutParams();
        params.width = (int) (ViewUtil.getScreenWidth(contentImg.getContext())
                - 2 * 8 * ViewUtil.getDensity(contentImg.getContext()));
        params.height = (int) (params.width / ViewUtil.AD_MAIN_VIEW_RATIO);
        contentImg.setLayoutParams(params);

        View tvCallToAction = rootView.findViewById(R.id.call_to_action);
        ViewGroup.LayoutParams params2 = tvCallToAction.getLayoutParams();
        params2.width = (int) (ViewUtil.getScreenWidth(tvCallToAction.getContext()) * ViewUtil.DEFAULT_BANNER_CTA_WIDTH_RATIO);
        tvCallToAction.setLayoutParams(params2);
    }

    private void resetInterstialView(View rootView) {
        View contentImg = rootView.findViewById(R.id.native_main_image);
        ViewGroup.LayoutParams params = contentImg.getLayoutParams();
        params.width = (int) (ViewUtil.getScreenWidth(contentImg.getContext())
                - 2 * 8 * ViewUtil.getDensity(contentImg.getContext()));
        params.height = (int) (params.width / ViewUtil.AD_MAIN_VIEW_RATIO);
        contentImg.setLayoutParams(params);

        View tvCallToAction = rootView.findViewById(R.id.call_to_action);
        ViewGroup.LayoutParams params2 = tvCallToAction.getLayoutParams();
        params2.width = (int) (ViewUtil.getScreenWidth(tvCallToAction.getContext()) * ViewUtil.DEFAULT_VIDEO_OFFER_CTA_WIDTH_RATIO);
        tvCallToAction.setLayoutParams(params2);
    }
}
