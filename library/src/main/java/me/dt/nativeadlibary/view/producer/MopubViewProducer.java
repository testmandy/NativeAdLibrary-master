package me.dt.nativeadlibary.view.producer;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mopub.nativeads.BaseNativeAd;
import com.mopub.nativeads.MoPubAdRenderer;
import com.mopub.nativeads.NativeImageHelper;
import com.mopub.nativeads.NativeRendererHelper;
import com.mopub.nativeads.StaticNativeAd;

import me.dt.nativeadlibary.R;
import me.dt.nativeadlibary.ad.data.BaseNativeAdData;
import me.dt.nativeadlibary.ad.data.MopubNativeAdData;
import me.dt.nativeadlibary.util.ViewUtil;

public class MopubViewProducer implements IProducer {

    private MopubViewProducer() {
    }

    @Override
    public View createBannerAdView(Context context, BaseNativeAdData data) {
        NativeViewHolder holder = createViewHolder(context, R.layout.mopub_banner_view_bit_vpn, data);
        resetBannerView(holder);
        return holder.mainView;
    }

    @Override
    public View createLoadingAdView(Context context, BaseNativeAdData data) {
        NativeViewHolder holder = createViewHolder(context, R.layout.mopub_loading_view_bit_vpn, data);
        resetLoadingView(holder);
        return holder.mainView;
    }

    @Override
    public View createInterstialAdView(Context context, BaseNativeAdData data) {
        NativeViewHolder holder = createViewHolder(context, R.layout.mopub_interstial_view_bit_vpn, data);
        resetInterstialView(holder);
        return holder.mainView;
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
        return null;
    }

    @Override
    public View createVideoOfferAdView(Context context, BaseNativeAdData data) {
        return null;
    }

    @Override
    public View createSpecialOfferAdView(Context context, BaseNativeAdData data) {
        NativeViewHolder holder = createViewHolder(context, R.layout.mopub_special_offer_view_bit_vpn, data);
        resetSpecialOfferView(holder);
        return holder.mainView;
    }

    private NativeViewHolder createViewHolder(Context context, @LayoutRes int resId, BaseNativeAdData data) {
        View rootView = LayoutInflater.from(context).inflate(resId, null);
        NativeViewHolder nativeViewHolder = new NativeViewHolder();
        nativeViewHolder.mainView = rootView;
        nativeViewHolder.titleView = rootView.findViewById(R.id.title);
        nativeViewHolder.textView = rootView.findViewById(R.id.content);
        nativeViewHolder.callToActionView = rootView.findViewById(R.id.call_to_action);
        nativeViewHolder.mainImageView = rootView.findViewById(R.id.native_main_image);
        nativeViewHolder.privacyInformationIconImageView = rootView.findViewById(R.id.native_privacy_information_icon_image);
        nativeViewHolder.iconImageView = rootView.findViewById(R.id.logo);

        MopubNativeAdData mopubNativeAdData = (MopubNativeAdData) data;
        MPNativeAdRenderer mpNativeAdRenderer = (MPNativeAdRenderer) mopubNativeAdData.getStreamAdPlacer().getAdRendererForViewType(1);

        mpNativeAdRenderer.setStaticNativeViewHolder(nativeViewHolder);
        mopubNativeAdData.getStreamAdPlacer().bindAdView(mopubNativeAdData.getAdData(), rootView);

        return nativeViewHolder;
    }

    private void resetBannerView(NativeViewHolder holder) {
        ViewGroup.LayoutParams params = holder.mainImageView.getLayoutParams();
        params.width = ViewUtil.getScreenWidth(holder.mainImageView.getContext()) / 2;
        params.height = (int) (params.width / ViewUtil.AD_MAIN_VIEW_RATIO);
        holder.mainImageView.setLayoutParams(params);

        ViewGroup.LayoutParams params2 = holder.callToActionView.getLayoutParams();
        params2.width = (int) (ViewUtil.getScreenWidth(holder.mainImageView.getContext()) * ViewUtil.DEFAULT_BANNER_CTA_WIDTH_RATIO);
        holder.callToActionView.setLayoutParams(params2);
    }

    private void resetLoadingView(NativeViewHolder holder) {
        ViewGroup.LayoutParams params = holder.mainImageView.getLayoutParams();
        params.width = (int) (ViewUtil.getScreenWidth(holder.mainImageView.getContext())
                - 2 * 8 * ViewUtil.getDensity(holder.mainImageView.getContext()));
        params.height = (int) (params.width / ViewUtil.AD_MAIN_VIEW_RATIO);
        holder.mainImageView.setLayoutParams(params);

        ViewGroup.LayoutParams params2 = holder.callToActionView.getLayoutParams();
        params2.width = (int) (ViewUtil.getScreenWidth(holder.mainImageView.getContext()) * ViewUtil.DEFAULT_BANNER_CTA_WIDTH_RATIO);
        holder.callToActionView.setLayoutParams(params2);
    }

    private void resetInterstialView(NativeViewHolder holder) {
        ViewGroup.LayoutParams params = holder.mainImageView.getLayoutParams();
        params.width = (int) (ViewUtil.getScreenWidth(holder.mainImageView.getContext())
                - 2 * 8 * ViewUtil.getDensity(holder.mainImageView.getContext()));
        params.height = (int) (params.width / ViewUtil.AD_MAIN_VIEW_RATIO);
        holder.mainImageView.setLayoutParams(params);

        ViewGroup.LayoutParams params2 = holder.callToActionView.getLayoutParams();
        params2.width = (int) (ViewUtil.getScreenWidth(holder.mainImageView.getContext()) * ViewUtil.DEFAULT_VIDEO_OFFER_CTA_WIDTH_RATIO);
        holder.callToActionView.setLayoutParams(params2);
    }

    private void resetSpecialOfferView(NativeViewHolder holder) {

        ViewGroup.LayoutParams params = holder.iconImageView.getLayoutParams();
        params.width = (int) (ViewUtil.getScreenWidth(holder.iconImageView.getContext()) * ViewUtil.DEFAULT_BANNER_CTA_WIDTH_RATIO);
        params.height = params.width;
        holder.iconImageView.setLayoutParams(params);

        ViewGroup.LayoutParams params2 = holder.callToActionView.getLayoutParams();
        params2.width = (int) (ViewUtil.getScreenWidth(holder.callToActionView.getContext()) * ViewUtil.DEFAULT_BANNER_CTA_WIDTH_RATIO);
        holder.callToActionView.setLayoutParams(params2);
    }

    public class NativeViewHolder {
        public View mainView;   // The main ad view
        public TextView titleView;  // The title
        public TextView textView;  // The description
        public TextView callToActionView;  // The call to action view
        public ImageView mainImageView;  // Main image
        public ImageView iconImageView; // Icon image
        public ImageView privacyInformationIconImageView;
    }

    public static class MPNativeAdRenderer implements MoPubAdRenderer<StaticNativeAd> {

        private NativeViewHolder viewHolder;

        public void setStaticNativeViewHolder(NativeViewHolder viewHolder) {
            this.viewHolder = viewHolder;
        }

        @Override
        public View createAdView(Context context, ViewGroup parent) {
            return null;
        }

        @Override
        public void renderAdView(View view, StaticNativeAd ad) {

            if (viewHolder.titleView != null) {
                NativeRendererHelper.addTextView(viewHolder.titleView, ad.getTitle());
            }
            if (viewHolder.textView != null) {
                NativeRendererHelper.addTextView(viewHolder.textView, ad.getText());
            }

            if (viewHolder.callToActionView != null) {
                NativeRendererHelper.addTextView(viewHolder.callToActionView, ad.getCallToAction());
            }

            if (viewHolder.mainImageView != null) {
                NativeImageHelper.loadImageView(ad.getMainImageUrl(), viewHolder.mainImageView);
            }

            if (viewHolder.iconImageView != null) {
                NativeImageHelper.loadImageView(ad.getIconImageUrl(), viewHolder.iconImageView);
            }

            if (viewHolder.privacyInformationIconImageView != null) {
                NativeRendererHelper.addPrivacyInformationIcon(viewHolder.privacyInformationIconImageView,
                        ad.getPrivacyInformationIconImageUrl(),
                        ad.getPrivacyInformationIconClickThroughUrl());
            }
        }

        @Override
        public boolean supports(BaseNativeAd nativeAd) {
            return nativeAd instanceof StaticNativeAd;
        }
    }
}
