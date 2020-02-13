package me.dt.nativeadlibary.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import me.dt.nativeadlibary.util.DTTimer;
import me.dt.nativeadlibary.util.L;
import me.dt.nativeadlibary.ad.AdCallbackListener;
import me.dt.nativeadlibary.ad.ErrorMsg;
import me.dt.nativeadlibary.ad.data.BaseNativeAdData;
import me.dt.nativeadlibary.config.NativeAdLibManager;
import me.dt.nativeadlibary.manager.AdCenterManager;

public class NativeAdBannerView extends RelativeLayout {

    private static final String TAG = "NativeAdBannerView";
    private boolean isAlive = true;
    private Activity mActivity;
    private DTTimer mAdBannerRefreshTimer;
    private AdCenterManager adCenterManager;
    private int mPlacement = -1;

    public NativeAdBannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mActivity = (Activity) context;
    }


    private void startTimer() {
        if (mAdBannerRefreshTimer == null) {
            int bannerRefreshTime = NativeAdLibManager.getInstance().getBannerRefreshTime();
            mAdBannerRefreshTimer = new DTTimer(bannerRefreshTime, false, new DTTimer.DTTimerListener() {
                @Override
                public void onTimer(DTTimer timer) {
                    if (!isAlive) {
                        return;
                    }
                    L.e(TAG, "refresh ad by timer");
                    loadAd();
                }
            });
        }
        mAdBannerRefreshTimer.startTimer();
    }


    private void stopTimer() {
        if (mAdBannerRefreshTimer != null) {
            mAdBannerRefreshTimer.stopTimer();
            mAdBannerRefreshTimer = null;
        }
    }

    public void loadAd() {
        if (mPlacement == -1) {
            L.e(TAG, "请调用setPlacement()初始化广告位");
            return;
        }
        loadNextTypeAd(mPlacement);
        refreshAdBanner();
    }

    private void loadNextTypeAd(final int adPosition) {
        if (adCenterManager == null) {
            adCenterManager = new AdCenterManager();
        }
        adCenterManager.load(mActivity, adPosition, this, new AdCallbackListener() {
            @Override
            public void onLoadFailed(ErrorMsg errorMsg) {

            }

            @Override
            public void onLoadNoCacheFailed(ErrorMsg errorMsg) {

            }

            @Override
            public void onLoadSuccess(BaseNativeAdData nativeAdData) {

            }

            @Override
            public void onClick(int adType) {
                loadAd();
            }

            @Override
            public void onImpression(int adType) {

            }
        });
    }

    private void refreshAdBanner() {
        if (!isAlive) {
            return;
        }
        stopTimer();
        startTimer();
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void setPlacement(int placement) {
        this.mPlacement = placement;
    }

    public void destory() {
        stopTimer();
        adCenterManager = null;
    }
}
