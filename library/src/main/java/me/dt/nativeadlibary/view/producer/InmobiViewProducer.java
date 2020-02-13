package me.dt.nativeadlibary.view.producer;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inmobi.ads.InMobiNative;
import com.squareup.picasso.Picasso;

import me.dt.nativeadlibary.R;
import me.dt.nativeadlibary.ad.data.BaseNativeAdData;
import me.dt.nativeadlibary.ad.data.InmobiNativeAdData;
import me.dt.nativeadlibary.config.NativeAdLibManager;

public class InmobiViewProducer implements IProducer {

    private String TAG = "InmobiViewProducer";

    private InmobiViewProducer() {
    }

    @Override
    public View createBannerAdView(Context context, BaseNativeAdData data) {
        return loadAdIntoView(context, R.layout.inmobi_banner_view, ((InmobiNativeAdData) data).getAdData());
    }

    @Override
    public View createLoadingAdView(Context context, BaseNativeAdData data) {
        return loadAdIntoViewForLoading(context, R.layout.inmobi_loading_view, ((InmobiNativeAdData) data).getAdData());
    }

    @Override
    public View createInterstialAdView(Context context, BaseNativeAdData data) {
        return loadAdIntoViewForLoading(context, R.layout.inmobi_interstial_view, ((InmobiNativeAdData) data).getAdData());
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
        return loadAdIntoViewForLoading(context, R.layout.inmobi_loading_view, ((InmobiNativeAdData) data).getAdData());
    }

    @Override
    public View createVideoOfferAdView(Context context, BaseNativeAdData data) {
        return loadAdIntoViewForLoading(context, R.layout.inmobi_video_offer_view, ((InmobiNativeAdData) data).getAdData());
    }

    @Override
    public View createSpecialOfferAdView(Context context, BaseNativeAdData data) {
        return null;
    }

    private View loadAdIntoView(Context context, @LayoutRes int resId, @NonNull final InMobiNative inMobiNative) {

        View rootView = LayoutInflater.from(context).inflate(resId, null);

        ImageView icon = rootView.findViewById(R.id.logo);
        TextView title = rootView.findViewById(R.id.title);
        TextView description = rootView.findViewById(R.id.content);
        Button action = rootView.findViewById(R.id.call_to_action);
        RatingBar ratingBar = rootView.findViewById(R.id.adRating);
        Picasso.with(NativeAdLibManager.getInstance().getContext()).load(inMobiNative.getAdIconUrl()).into(icon);

        title.setText(inMobiNative.getAdTitle());
        description.setText(inMobiNative.getAdDescription());
        action.setText(inMobiNative.getAdCtaText());
        float rating = inMobiNative.getAdRating();

        if (rating != 0) {
            ratingBar.setRating(rating);
        }
        ratingBar.setVisibility(rating != 0 ? View.VISIBLE : View.GONE);
        action.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inMobiNative.reportAdClickAndOpenLandingPage();
            }
        });
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inMobiNative.reportAdClickAndOpenLandingPage();
            }
        });
        title.setClickable(true);
        description.setClickable(true);
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inMobiNative.reportAdClickAndOpenLandingPage();
            }
        });
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inMobiNative.reportAdClickAndOpenLandingPage();
            }
        });
        return rootView;
    }

    private View loadAdIntoViewForLoading(Context context, @LayoutRes int resId, @NonNull final InMobiNative inMobiNative) {

        View rootView = LayoutInflater.from(context).inflate(resId, null);

        ImageView icon = rootView.findViewById(R.id.logo);
        TextView title = rootView.findViewById(R.id.title);
        TextView description = rootView.findViewById(R.id.content);
        Button action = rootView.findViewById(R.id.call_to_action);
        RatingBar ratingBar = rootView.findViewById(R.id.adRating);
        FrameLayout content = rootView.findViewById(R.id.adContent);
        RelativeLayout mContainer = rootView.findViewById(R.id.ad_view);
        Picasso.with(NativeAdLibManager.getInstance().getContext()).load(inMobiNative.getAdIconUrl()).into(icon);

        action.setClickable(false);
        icon.setClickable(false);
        ratingBar.setClickable(false);
        title.setText(inMobiNative.getAdTitle());
        description.setText(inMobiNative.getAdDescription());
        action.setText(inMobiNative.getAdCtaText());

        float rating = inMobiNative.getAdRating();
        if (rating != 0) {
            ratingBar.setRating(rating);
        }
        ratingBar.setVisibility(rating != 0 ? View.VISIBLE : View.GONE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        content.addView(inMobiNative.getPrimaryViewOfWidth(context, content, mContainer, displayMetrics.widthPixels));
        mContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inMobiNative.reportAdClickAndOpenLandingPage();
            }
        });
        return rootView;
    }
}
