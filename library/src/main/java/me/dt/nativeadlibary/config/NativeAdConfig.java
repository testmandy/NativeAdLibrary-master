package me.dt.nativeadlibary.config;

import android.app.Activity;
import android.content.Context;

import java.util.List;


/**
 * Created by Bill on 2019-10-09
 */
public class NativeAdConfig {
    public final Context context;
    public final Activity activity;
    public final String key;//广告商key
    public final String placementId;//广告商placementId
    public final Class clazz;
    public final int adType; //广告商代号
    public final int maxCachePoolCount; //缓存池最大的缓存个数
    public final int maxRequestCount; //同时发出的广告请求的最大次数
    public final int videoOfferEnable;//是否支持推荐offer
    public final List<String> videoOfferCountry;//推荐offer支持的国家列表
    public final float lowReward;//低价值国家给予的奖励
    public final float highReward;//高价值国家给予的奖励
    public final int retryTimes; //加载失败重试的次数
    public final long timeOut;//单次广告请求超时时间
    public boolean debug;//是否是测试模式
    public int downloadTypeRequestCount;//下载类请求重试次数

    public NativeAdConfig(Builder builder) {
        this.context = builder.context;
        this.activity = builder.activity;
        this.key = builder.key;
        this.placementId = builder.placementId;
        this.clazz = builder.clazz;
        this.adType = builder.adType;
        this.maxCachePoolCount = builder.maxCachePoolCount;
        this.maxRequestCount = builder.maxRequestCount;
        this.videoOfferEnable = builder.videoOfferEnable;
        this.videoOfferCountry = builder.videoOfferCountry;
        this.lowReward = builder.lowReward;
        this.highReward = builder.highReward;
        this.retryTimes = builder.retryTimes;
        this.timeOut = builder.timeOut;
        this.debug = builder.debug;
        this.downloadTypeRequestCount = builder.downloadTypeRequestCount;
    }


    public static class Builder {

        private Context context;
        private Activity activity;
        private String key;//广告商key
        private String placementId;//广告商placementId
        private Class clazz;
        private int adType; //广告商代号
        private int maxCachePoolCount; //缓存池最大的缓存个数
        private int maxRequestCount; //同时发出的广告请求的最大次数
        private int videoOfferEnable;//是否支持推荐offer
        private List<String> videoOfferCountry;//推荐offer支持的国家列表
        private float lowReward;//低价值国家给予的奖励
        private float highReward;//高价值国家给予的奖励
        private int retryTimes; //加载失败重试的次数
        private long timeOut;//单次广告请求超时时间
        private boolean debug;//是否是测试模式
        private int downloadTypeRequestCount;//下载类请求重试次数

        public Builder() {

        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setActivity(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder setKey(String key) {
            this.key = key;
            return this;
        }

        public Builder setPlacementId(String placementId) {
            this.placementId = placementId;
            return this;
        }

        public Builder setClazz(Class clazz) {
            this.clazz = clazz;
            return this;
        }

        public Builder setAdType(int adType) {
            this.adType = adType;
            return this;
        }

        public Builder setMaxCachePoolCount(int maxCachePoolCount) {
            this.maxCachePoolCount = maxCachePoolCount;
            return this;
        }

        public Builder setMaxRequestCount(int maxRequestCount) {
            this.maxRequestCount = maxRequestCount;
            return this;
        }

        public Builder setVideoOfferEnable(int videoOfferEnable) {
            this.videoOfferEnable = videoOfferEnable;
            return this;
        }

        public Builder setVideoOfferCountry(List<String> videoOfferCountry) {
            this.videoOfferCountry = videoOfferCountry;
            return this;
        }

        public Builder setLowReward(float lowReward) {
            this.lowReward = lowReward;
            return this;
        }

        public Builder setHighReward(float highReward) {
            this.highReward = highReward;
            return this;
        }

        public Builder setRetryTimes(int retryTimes) {
            this.retryTimes = retryTimes;
            return this;
        }

        public Builder setTimeOut(long timeOut) {
            this.timeOut = timeOut;
            return this;
        }

        public Builder setDebug(boolean debug) {
            this.debug = debug;
            return this;
        }

        public Builder setDownloadTypeRequestCount(int downloadTypeRequestCount) {
            this.downloadTypeRequestCount = downloadTypeRequestCount;
            return this;
        }

        public NativeAdConfig build() {
            return new NativeAdConfig(this);
        }

    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(" adType = ").append(adType);
        sb.append(" key = ").append(key);
        sb.append(" placementId = ").append(placementId);
        sb.append(" maxCachePoolCount = ").append(maxCachePoolCount);
        sb.append(" maxRequestCount = ").append(maxRequestCount);
        sb.append(" videoOfferEnable = ").append(videoOfferEnable);
        sb.append(" videoOfferCountry = ").append(videoOfferCountry);
        sb.append(" retryTimes = ").append(retryTimes);
        sb.append(" timeOut = ").append(timeOut);
        sb.append(" downloadTypeRequestCount = ").append(downloadTypeRequestCount);
        sb.append(" debug = ").append(debug);
        return sb.toString();
    }
}
