package me.dt.nativeadlibary.manager;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mopub.nativeads.NativeAd;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import me.dt.nativeadlibary.util.AdInstanceGenerator;
import me.dt.nativeadlibary.util.AdProviderType;
import me.dt.nativeadlibary.util.EventConstant;
import me.dt.nativeadlibary.util.L;
import me.dt.nativeadlibary.ad.AdCallbackListener;
import me.dt.nativeadlibary.ad.ErrorMsg;
import me.dt.nativeadlibary.ad.data.BaseNativeAdData;
import me.dt.nativeadlibary.ad.loader.INativeAdLoader;
import me.dt.nativeadlibary.ad.loader.SmaatoNativeAdLoader;
import me.dt.nativeadlibary.config.AdListControlManager;
import me.dt.nativeadlibary.config.AdPosition2ViewTypeManage;
import me.dt.nativeadlibary.config.DTConstant;
import me.dt.nativeadlibary.config.NativeAdConfig;
import me.dt.nativeadlibary.config.NativeAdLibManager;
import me.dt.nativeadlibary.view.AdViewType;
import me.dt.nativeadlibary.view.ViewFactory;

public class AdCenterManager {
    private static final String TAG = "AdCenterManager";
    private int mAdPosition = -1;//广告位
    private WeakReference<Activity> mActivityWeakReference;
    private BlockingQueue<INativeAdLoader> mAdLoadServiceQueue = new LinkedBlockingQueue<>();//广告商队列
    private Map<Integer, NativeAdConfig> adInstanceConfigurationsMap = new LinkedHashMap<>();//广告商配置集合
    private AdCallbackListener mAdCallbackListener;//广告请求监听
    private ViewGroup mAdContainer;//广告布局容器
    private boolean isAdLoadSuccess = false;//当次广告请求是否已经显示出来
    private int mAdViewType;//广告样式

    public AdCenterManager() {
    }


    public void preloadAd(Activity activity, int adPosition) {
        this.mActivityWeakReference = new WeakReference<>(activity);
        this.mAdPosition = adPosition;
        produceAdInstances(mActivityWeakReference.get(), initAdConfigurations(NativeAdLibManager.getInstance().getAdListByPosition(adPosition)));
        preloadAd();
    }


    public void preloadAd(Activity activity, int adPosition, AdCallbackListener adCallbackListener) {
        this.mAdCallbackListener = adCallbackListener;
        this.mActivityWeakReference = new WeakReference<>(activity);
        this.mAdPosition = adPosition;
        mAdLoadServiceQueue.clear();
        produceAdInstances(mActivityWeakReference.get(), initAdConfigurations(NativeAdLibManager.getInstance().getAdListByPosition(adPosition)));
        preloadAd();
    }


    public void load(Activity activity, int adPosition, ViewGroup adContainer, AdCallbackListener adCallbackListener) {
        this.mAdCallbackListener = adCallbackListener;
        this.mActivityWeakReference = new WeakReference<>(activity);
        this.mAdContainer = adContainer;
        this.mAdPosition = adPosition;
        mAdLoadServiceQueue.clear();
        isAdLoadSuccess = false;
        mAdViewType = AdPosition2ViewTypeManage.getInstance().getAdViewType(mAdPosition);
        L.d(TAG, "start loadAd adPosition  = " + mAdPosition + " AdViewType = " + mAdViewType);
        produceAdInstances(mActivityWeakReference.get(), initAdConfigurations(NativeAdLibManager.getInstance().produceAdListByPosition(adPosition)));
        loadAd();
    }

    private List<NativeAdConfig> initAdConfigurations(List<Integer> adList) {
        List<NativeAdConfig> adInstanceConfigurations = new ArrayList<>();
        for (Integer adType : adList) {

            NativeAdConfig adInstanceConfiguration;
            if (adInstanceConfigurationsMap.get(adType) != null) {
                adInstanceConfiguration = adInstanceConfigurationsMap.get(adType);
            } else {
                adInstanceConfiguration = NativeAdLibManager.getInstance().getSingleNativeAdConfig(adType);
                adInstanceConfigurationsMap.put(adType, adInstanceConfiguration);
            }

            if (adInstanceConfiguration != null) {
                if (mAdViewType == AdViewType.VIDEO_OFFER) {
                    if (adInstanceConfiguration.videoOfferEnable == 0
                            || adInstanceConfiguration.videoOfferCountry == null
                            || !adInstanceConfiguration.videoOfferCountry.contains(DTConstant.COUNTRY_CODE_ID)) {
                        L.d(TAG, "initAdConfigurations adType " + adInstanceConfiguration.adType + " not support video offer remove from ad list");
                        continue;
                    }
                }
                L.d(TAG, "initAdConfigurations adInstanceConfigurations = " + adInstanceConfiguration.toString());
                adInstanceConfigurations.add(adInstanceConfiguration);
            }
        }
        return adInstanceConfigurations;
    }


    private void produceAdInstances(Context context, List<NativeAdConfig> adInstanceConfigurations) {
        if (adInstanceConfigurations != null) {
            int size = adInstanceConfigurations.size();
            for (int i = 0; i < size; i++) {
                NativeAdConfig adLoaderConfiguration = adInstanceConfigurations.get(i);
                produceAdInstanceByConfiguration(context, adLoaderConfiguration);
            }
        }
    }


    private void produceAdInstanceByConfiguration(Context context, NativeAdConfig adLoaderConfiguration) {
        INativeAdLoader iNativeAdLoader = AdInstanceGenerator.newInstance().produceAdInstance(adLoaderConfiguration);
        if (iNativeAdLoader != null) {
            iNativeAdLoader.initialized(context, adLoaderConfiguration);
            mAdLoadServiceQueue.add(iNativeAdLoader);
            L.d(TAG, "produceAdInstanceByConfiguration adType = " + adLoaderConfiguration.adType);
        }
    }


    private void loadAd() {

        if (mActivityWeakReference.get() == null) {
            L.e(TAG, "context is null so stop load ad ！");
            return;
        }

        final boolean isNeedLoadDownloadAd = (mAdViewType == AdViewType.VIDEO_OFFER);
        final INativeAdLoader iNativeAdLoader = mAdLoadServiceQueue.poll();
        if (iNativeAdLoader instanceof SmaatoNativeAdLoader) {
            ((SmaatoNativeAdLoader) iNativeAdLoader).setAdViewType(mAdViewType);
        }
        if (iNativeAdLoader != null) {
            iNativeAdLoader.startLoad(isNeedLoadDownloadAd, new AdCallbackListener() {
                @Override
                public void onLoadFailed(ErrorMsg errorMsg) {
                    if (mAdCallbackListener != null) {
                        mAdCallbackListener.onLoadFailed(errorMsg);
                    }
                }

                @Override
                public void onLoadNoCacheFailed(ErrorMsg errorMsg) {
                    L.d(TAG, "loadAd onLoadNoCacheFailed errorMsg = " + errorMsg.getErrorMsg());
                    loadAd();
                }

                @Override
                public void onLoadSuccess(BaseNativeAdData nativeAdData) {

                    if (!isAdLoadSuccess) {
                        L.d(TAG, "loadAd onLoadSuccess type = " + nativeAdData.getAdType());
                        if (isNeedLoadDownloadAd && !nativeAdData.isDownloadType()) {
                            iNativeAdLoader.addCacheAd(nativeAdData);
                            loadAd();
                            return;
                        }

                        View adView = ViewFactory.produce(mActivityWeakReference.get(), nativeAdData, mAdViewType);

                        if (adView != null) {
                            mAdContainer.removeAllViews();
                            mAdContainer.addView(adView);
                            isAdLoadSuccess = true;
                        }
                        if (mAdCallbackListener != null) {
                            mAdCallbackListener.onLoadSuccess(nativeAdData);
                        }
                        Toast.makeText(mActivityWeakReference.get(), "显示广告商 " + nativeAdData.getAdType(), Toast.LENGTH_SHORT).show();
                        AdListControlManager.getInstance().addShowedAdCount(nativeAdData.getAdType());
                        adCallBackListenerDistribute(nativeAdData);

                    } else {
                        L.d(TAG, "loadAd onLoadSuccess  showed ad add cache type = " + nativeAdData.getAdType() + "");
                        iNativeAdLoader.addCacheAd(nativeAdData);
                    }
                }

                @Override
                public void onClick(int adType) {
                    L.d(TAG, "onClick adType = " + adType);
                    if (mAdCallbackListener != null) {
                        mAdCallbackListener.onClick(adType);
                    }
                    EventConstant.event(EventConstant.CATEGORY_NATIVE, EventConstant.ACTION_CLICK, EventConstant.makeLabel(adType, mAdPosition + ""));
                }

                @Override
                public void onImpression(int adType) {
                    L.d(TAG, "onImpression adType = " + adType);
                    if (mAdCallbackListener != null) {
                        mAdCallbackListener.onImpression(adType);
                    }
                    EventConstant.event(EventConstant.CATEGORY_NATIVE, EventConstant.ACTION_IMPRESSION, EventConstant.makeLabel(adType, mAdPosition + ""));
                }
            });
        } else {
            if (mAdCallbackListener != null) {
                mAdCallbackListener.onLoadNoCacheFailed(new ErrorMsg());
            }
        }
    }

    private void preloadAd() {

        if (mActivityWeakReference.get() == null) {
            L.e(TAG, "context is null so stop load ad ！");
            return;
        }

        final boolean isNeedLoadDownloadAd = (mAdViewType == AdViewType.VIDEO_OFFER);
        final INativeAdLoader iNativeAdLoader = mAdLoadServiceQueue.poll();
        if (iNativeAdLoader instanceof SmaatoNativeAdLoader) {
            ((SmaatoNativeAdLoader) iNativeAdLoader).setAdViewType(mAdViewType);
        }
        if (iNativeAdLoader != null) {
            iNativeAdLoader.startLoad(isNeedLoadDownloadAd, new AdCallbackListener() {
                @Override
                public void onLoadFailed(ErrorMsg errorMsg) {
                    if (mAdCallbackListener != null) {
                        mAdCallbackListener.onLoadFailed(errorMsg);
                    }
                }

                @Override
                public void onLoadNoCacheFailed(ErrorMsg errorMsg) {
                    L.d(TAG, "loadAd onLoadNoCacheFailed errorMsg = " + errorMsg.getErrorMsg());
                    preloadAd();
                }

                @Override
                public void onLoadSuccess(BaseNativeAdData nativeAdData) {
                    L.d(TAG, "loadAd onLoadSuccess type = " + nativeAdData.getAdType());
                    if (isNeedLoadDownloadAd && !nativeAdData.isDownloadType()) {
                        iNativeAdLoader.addCacheAd(nativeAdData);
                        preloadAd();
                        return;
                    }
                    iNativeAdLoader.addCacheAd(nativeAdData);
                    if (mAdCallbackListener != null) {
                        mAdCallbackListener.onLoadSuccess(nativeAdData);
                    }
                    Toast.makeText(mActivityWeakReference.get(), "缓存成功 广告商 " + nativeAdData.getAdType(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onClick(int adType) {
                }

                @Override
                public void onImpression(int adType) {
                }
            });
        } else {
            if (mAdCallbackListener != null) {
                mAdCallbackListener.onLoadNoCacheFailed(new ErrorMsg());
            }
        }
    }


    private void adCallBackListenerDistribute(BaseNativeAdData nativeAdData) {
        if (nativeAdData.getAdType() == AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE) {
            Object adObject = nativeAdData.getAdData();

            if (adObject != null) {
                ((NativeAd) adObject).setMoPubNativeEventListener(new NativeAd.MoPubNativeEventListener() {
                    @Override
                    public void onImpression(View view) {
                        if (mAdCallbackListener != null) {
                            mAdCallbackListener.onImpression(AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE);
                        }
                        L.d(TAG, "onImpression adType = " + AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE);
                        EventConstant.event(EventConstant.CATEGORY_NATIVE, EventConstant.ACTION_IMPRESSION, EventConstant.makeLabel(AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE, mAdPosition + ""));
                    }

                    @Override
                    public void onClick(View view) {
                        if (mAdCallbackListener != null) {
                            mAdCallbackListener.onClick(AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE);
                        }
                        L.d(TAG, "onClick adType = " + AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE);
                        EventConstant.event(EventConstant.CATEGORY_NATIVE, EventConstant.ACTION_CLICK, EventConstant.makeLabel(AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE, mAdPosition + ""));
                    }
                });
            }
        } else if (nativeAdData.getAdType() == AdProviderType.AD_PROVIDER_TYPE_SMAATO_NATIVE) {
            L.d(TAG, "onImpression adType = " + AdProviderType.AD_PROVIDER_TYPE_SMAATO_NATIVE);
            if (mAdCallbackListener != null) {
                mAdCallbackListener.onImpression(AdProviderType.AD_PROVIDER_TYPE_SMAATO_NATIVE);
            }
            EventConstant.event(EventConstant.CATEGORY_NATIVE, EventConstant.ACTION_IMPRESSION, EventConstant.makeLabel(AdProviderType.AD_PROVIDER_TYPE_SMAATO_NATIVE, mAdPosition + ""));
        }
    }


}
