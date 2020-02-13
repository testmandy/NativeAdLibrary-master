package me.dt.nativeadlibary.ad.loader;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import me.dt.nativeadlibary.util.L;
import me.dt.nativeadlibary.ad.AdCallbackListener;
import me.dt.nativeadlibary.ad.ErrorMsg;
import me.dt.nativeadlibary.ad.data.BaseNativeAdData;
import me.dt.nativeadlibary.config.NativeAdConfig;

public abstract class BaseNativeAdLoader implements INativeAdLoader {

    protected Context mContext;
    protected boolean mHasInited;
    private volatile Status mStatus = Status.FINISHED;
    private int mDownloadTypeRequestCount;
    private int mCurrentRetryCounts;
    private Handler mWorkerHandler;

    public NativeAdConfig mNativeConfig;
    public List<BaseNativeAdData> mCacheAdList = new ArrayList<>();

    private Runnable mLoadingTimeoutRunnable;

    protected BaseNativeAdLoader() {
    }

    @Override
    public void initialized(Context context, NativeAdConfig nativeConfig) {
        L.i(getLoaderName(), " initialized");
        mContext = context;
        mNativeConfig = nativeConfig;
        HandlerThread workerThread = new HandlerThread(getLoaderName() + "Thread");
        workerThread.setPriority(Process.THREAD_PRIORITY_BACKGROUND);
        workerThread.start();
        mWorkerHandler = new Handler(workerThread.getLooper());

        mLoadingTimeoutRunnable = new Runnable() {
            @Override
            public void run() {
                mStatus = Status.FINISHED;
            }
        };
    }


    protected abstract ErrorMsg getErrorMsg();

    protected abstract String getLoaderName();


    protected abstract boolean shouldLoadInBackground();

    protected void loadDirectly(boolean isDownloadType, AdCallbackListener adCallbackListener) {
        if (isDownloadType) {
            mDownloadTypeRequestCount++;
        } else {
            mCurrentRetryCounts++;
        }
        mStatus = Status.LOADING;
        L.i(getLoaderName(), "loadDirectly isDownloadType : " + isDownloadType);

        mWorkerHandler.postDelayed(mLoadingTimeoutRunnable, mNativeConfig.timeOut);
    }

    protected abstract BaseNativeAdData packData(Object originData);

    @Override
    public void startLoad(AdCallbackListener adCallbackListener) {
        startLoad(false, adCallbackListener);
    }

    @Override
    public void startLoad(boolean isDownloadType, AdCallbackListener adCallbackListener) {
        mDownloadTypeRequestCount = 0;
        mCurrentRetryCounts = 0;
        BaseNativeAdData baseNativeAdData = getCacheAd(isDownloadType);

        if (baseNativeAdData != null) {
            adCallbackListener.onLoadSuccess(baseNativeAdData);
        } else {
            adCallbackListener.onLoadNoCacheFailed(getErrorMsg());
        }

        if (mStatus != Status.LOADING) {
            if (shouldLoadInBackground()) {
                loadInWorkThread(isDownloadType, adCallbackListener);
            } else {
                loadDirectly(isDownloadType, adCallbackListener);
            }
        }
    }

    @Override
    public synchronized void addCacheAd(BaseNativeAdData baseNativeAdData) {

        L.i(getLoaderName(), "addCacheAd : " + baseNativeAdData);
        abandonExpiredAd();

        int cacheSize = mCacheAdList.size();
        if (cacheSize < mNativeConfig.maxCachePoolCount) {
            mCacheAdList.add(baseNativeAdData);

        } else {

            int firstNoDownloadAdIndex = 0;
            for (int i = 0; i < cacheSize; i++) {
                if (!mCacheAdList.get(i).isDownloadType()) {
                    firstNoDownloadAdIndex = i;
                    break;
                }
            }
            mCacheAdList.remove(firstNoDownloadAdIndex);
            mCacheAdList.add(baseNativeAdData);
        }
    }

    @Override
    public synchronized BaseNativeAdData getCacheAd(boolean isDownloadType) {

        abandonExpiredAd();

        int cacheAdSize = mCacheAdList.size();
        if (cacheAdSize == 0) {
            L.i(getLoaderName(), "getCacheAd no cache");
            return null;
        }

        if (isDownloadType) {
            for (int i = 0; i < cacheAdSize; i++) {
                if (mCacheAdList.get(i).isDownloadType()) {
                    L.i(getLoaderName(), "getCacheAd download type");
                    return mCacheAdList.remove(i);
                }
            }
            return null;
        }

        L.i(getLoaderName(), "getCacheAd non-download type");
        return mCacheAdList.remove(0);
    }

    private synchronized void abandonExpiredAd() {

        Iterator<BaseNativeAdData> iterator = mCacheAdList.iterator();

        while (iterator.hasNext()) {
            BaseNativeAdData data = iterator.next();
            if (data.isAdExpired()) {
                L.i(getLoaderName(), "abandonExpiredAd : " + data);
                iterator.remove();
            }
        }
    }


    private void loadInWorkThread(final boolean isDownloadType, final AdCallbackListener adCallbackListener) {

        mWorkerHandler.post(new Runnable() {
            @Override
            public void run() {
                loadDirectly(isDownloadType, adCallbackListener);
            }
        });
    }

    protected void onLoadSuccess(BaseNativeAdData data, boolean isDownloadType, final AdCallbackListener adCallbackListener) {

        L.i(getLoaderName(), "onLoadSuccess : " + data);

        mStatus = Status.FINISHED;
        mWorkerHandler.removeCallbacks(mLoadingTimeoutRunnable);

        adCallbackListener.onLoadSuccess(data);

        if (isDownloadType) {
            if (data.isDownloadType()) {
                mDownloadTypeRequestCount = 0;
            } else {
                retry(true, adCallbackListener);
            }

        } else {
            mCurrentRetryCounts = 0;
        }
    }

    protected void onLoadFailed(boolean isDownloadType, final AdCallbackListener adCallbackListener, ErrorMsg errorMsg) {

        L.i(getLoaderName(), "onLoadFailed : " + errorMsg.getErrorMsg());

        mStatus = Status.FINISHED;
        mWorkerHandler.removeCallbacks(mLoadingTimeoutRunnable);

        if (!retry(isDownloadType, adCallbackListener)) {
            adCallbackListener.onLoadFailed(errorMsg);
        }
    }


    private boolean retry(boolean isDownloadType, final AdCallbackListener adCallbackListener) {

        boolean needRetry = (mStatus != Status.LOADING)
                && ((isDownloadType && mDownloadTypeRequestCount < mNativeConfig.downloadTypeRequestCount)
                || (!isDownloadType && mCurrentRetryCounts < mNativeConfig.retryTimes));

        L.i(getLoaderName(), "mStatus : " + mStatus);
        L.i(getLoaderName(), "isDownloadType : " + isDownloadType);
        L.i(getLoaderName(), "mDownloadTypeRequestCount : " + mDownloadTypeRequestCount);
        L.i(getLoaderName(), "mCurrentRetryCounts : " + mCurrentRetryCounts);
        L.i(getLoaderName(), "needRetry : " + needRetry);

        if (needRetry) {
            L.i(getLoaderName(), " retry load");
            if (shouldLoadInBackground()) {
                loadInWorkThread(isDownloadType, adCallbackListener);
            } else {
                loadDirectly(isDownloadType, adCallbackListener);
            }
        }

        return needRetry;

    }


}
