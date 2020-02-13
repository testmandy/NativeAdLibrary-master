package me.dt.nativeadlibary.config;

import java.util.ArrayList;
import java.util.List;

public class NativeAdLibConfig {


    /**
     * bannerRefreshTime : 5000
     * nativeAdList : [{"adPostition":30,"adList":"34,22,112,39"},{"adPostition":14,"adList":"34,22,112,39"}]
     * adFlowControl : {"enable":1,"adPlacementEnable":[1],"nativeAdCountLimit":"34-3,22-3,39-3,112-3","resetAdCacheCount":"34-2,39-2,22-2,112-2"}
     * singleAdConfig : [{"adType":34,"key":"hddjshaksdka","placementId":"3967238947","cacheCount":2,"requestCount":2,"videoOfferEnable":"1","videoOfferCountry":["cn"],"lowValue":6,"highValue":10}]
     * independenceAdEnable : [{"adType":1,"deviceManuFacturer":["xiaomi","samsung"],"enable":1,"isVPN":1,"isRoot":1,"isSimulator":1,"ratio":1},{"adType":129,"appVersion":["4.11.0.540"],"osTypeRange":["6.0","8.0"],"deviceManuFacturer":["xiaomi","samsung"],"enable":1,"isVPN":1,"isRoot":1,"isSimulator":1,"ratio":1}]
     */

    private int bannerRefreshTime = 10000;
    private AdFlowControlBean adFlowControl;
    private List<NativeAdListBean> nativeAdList;
    private List<SingleAdConfigBean> singleAdConfig;
    private List<IndependenceAdEnableBean> independenceAdEnable;

    public int getBannerRefreshTime() {
        return bannerRefreshTime;
    }

    public void setBannerRefreshTime(int bannerRefreshTime) {
        this.bannerRefreshTime = bannerRefreshTime;
    }

    public AdFlowControlBean getAdFlowControl() {
        return adFlowControl;
    }

    public void setAdFlowControl(AdFlowControlBean adFlowControl) {
        this.adFlowControl = adFlowControl;
    }

    public List<NativeAdListBean> getNativeAdList() {
        return nativeAdList;
    }

    public void setNativeAdList(List<NativeAdListBean> nativeAdList) {
        this.nativeAdList = nativeAdList;
    }

    public List<SingleAdConfigBean> getSingleAdConfig() {
        return singleAdConfig;
    }

    public void setSingleAdConfig(List<SingleAdConfigBean> singleAdConfig) {
        this.singleAdConfig = singleAdConfig;
    }

    public List<IndependenceAdEnableBean> getIndependenceAdEnable() {
        return independenceAdEnable;
    }

    public void setIndependenceAdEnable(List<IndependenceAdEnableBean> independenceAdEnable) {
        this.independenceAdEnable = independenceAdEnable;
    }

    public static class AdFlowControlBean {
        /**
         * enable : 1
         * adPlacementEnable : [1]
         * nativeAdCountLimit : 34-3,22-3,39-3,112-3
         * resetAdCacheCount : 34-2,39-2,22-2,112-2
         */

        private int enable;
        private String nativeAdCountLimit;
        private String resetAdCacheCount;
        private List<Integer> adPlacementEnable;

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public String getNativeAdCountLimit() {
            return nativeAdCountLimit;
        }

        public void setNativeAdCountLimit(String nativeAdCountLimit) {
            this.nativeAdCountLimit = nativeAdCountLimit;
        }

        public String getResetAdCacheCount() {
            return resetAdCacheCount;
        }

        public void setResetAdCacheCount(String resetAdCacheCount) {
            this.resetAdCacheCount = resetAdCacheCount;
        }

        public List<Integer> getAdPlacementEnable() {
            return adPlacementEnable;
        }

        public void setAdPlacementEnable(List<Integer> adPlacementEnable) {
            this.adPlacementEnable = adPlacementEnable;
        }
    }

    public static class NativeAdListBean {
        /**
         * adPostition : 30
         * adList : [34,22,112,39]
         */

        private int adPostition;
        private List<Integer> adList;

        public int getAdPostition() {
            return adPostition;
        }

        public void setAdPostition(int adPostition) {
            this.adPostition = adPostition;
        }

        public List<Integer> getAdList() {
            return adList;
        }

        public void setAdList(List<Integer> adList) {
            this.adList = adList;
        }
    }

    public static class SingleAdConfigBean {
        /**
         * adType : 34
         * key : hddjshaksdka
         * placementId : 3967238947
         * cacheCount : 2
         * requestCount : 2
         * videoOfferEnable : 1
         * videoOfferCountry : ["cn"]
         * lowValue : 6
         * highValue : 10
         * retryTime ： 2
         * timeOut ：5000
         */

        private int adType;
        private String key;
        private String placementId;
        private int cacheCount;
        private int requestCount;
        private int videoOfferEnable;
        private int lowValue;
        private int highValue;
        private List<String> videoOfferCountry;
        private int retryTime;
        private long timeOut;
        private int downloadTypeRequestCount;

        public int getAdType() {
            return adType;
        }

        public void setAdType(int adType) {
            this.adType = adType;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getPlacementId() {
            return placementId;
        }

        public void setPlacementId(String placementId) {
            this.placementId = placementId;
        }

        public int getCacheCount() {
            return cacheCount;
        }

        public void setCacheCount(int cacheCount) {
            this.cacheCount = cacheCount;
        }

        public int getRequestCount() {
            return requestCount;
        }

        public void setRequestCount(int requestCount) {
            this.requestCount = requestCount;
        }

        public int getVideoOfferEnable() {
            return videoOfferEnable;
        }

        public void setVideoOfferEnable(int videoOfferEnable) {
            this.videoOfferEnable = videoOfferEnable;
        }

        public int getLowValue() {
            return lowValue;
        }

        public void setLowValue(int lowValue) {
            this.lowValue = lowValue;
        }

        public int getHighValue() {
            return highValue;
        }

        public void setHighValue(int highValue) {
            this.highValue = highValue;
        }

        public List<String> getVideoOfferCountry() {
            return videoOfferCountry;
        }

        public void setVideoOfferCountry(List<String> videoOfferCountry) {
            this.videoOfferCountry = videoOfferCountry;
        }

        public int getRetryTime() {
            return retryTime;
        }

        public void setRetryTime(int retryTime) {
            this.retryTime = retryTime;
        }

        public long getTimeOut() {
            return timeOut;
        }

        public void setTimeOut(long timeOut) {
            this.timeOut = timeOut;
        }

        public int getDownloadTypeRequestCount() {
            return downloadTypeRequestCount;
        }

        public void setDownloadTypeRequestCount(int downloadTypeRequestCount) {
            this.downloadTypeRequestCount = downloadTypeRequestCount;
        }
    }

    public static class IndependenceAdEnableBean {
        private int adType;
        private int enable = 0;
        private double ratio;
        private int isVPN;
        private int isRoot;
        private int isSimulator;
        private List<String> appVersion = new ArrayList<>();
        private List<String> osType = new ArrayList<>();
        private List<String> phoneType = new ArrayList<>();
        private List<String> isoCountryCode = new ArrayList<>();
        private List<String> osTypeRange = new ArrayList<>();
        private List<String> deviceManuFacturer = new ArrayList<>();

        public int getAdType() {
            return adType;
        }

        public void setAdType(int adType) {
            this.adType = adType;
        }

        public int getEnable() {
            return enable;
        }

        public void setEnable(int enable) {
            this.enable = enable;
        }

        public double getRatio() {
            return ratio;
        }

        public void setRatio(double ratio) {
            this.ratio = ratio;
        }

        public int getIsVPN() {
            return isVPN;
        }

        public void setIsVPN(int isVPN) {
            this.isVPN = isVPN;
        }

        public int getIsRoot() {
            return isRoot;
        }

        public void setIsRoot(int isRoot) {
            this.isRoot = isRoot;
        }

        public int getIsSimulator() {
            return isSimulator;
        }

        public void setIsSimulator(int isSimulator) {
            this.isSimulator = isSimulator;
        }

        public List<String> getAppVersion() {
            return appVersion;
        }

        public void setAppVersion(List<String> appVersion) {
            this.appVersion = appVersion;
        }

        public List<String> getOsType() {
            return osType;
        }

        public void setOsType(List<String> osType) {
            this.osType = osType;
        }

        public List<String> getPhoneType() {
            return phoneType;
        }

        public void setPhoneType(List<String> phoneType) {
            this.phoneType = phoneType;
        }

        public List<String> getIsoCountryCode() {
            return isoCountryCode;
        }

        public void setIsoCountryCode(List<String> isoCountryCode) {
            this.isoCountryCode = isoCountryCode;
        }

        public List<String> getOsTypeRange() {
            return osTypeRange;
        }

        public void setOsTypeRange(List<String> osTypeRange) {
            this.osTypeRange = osTypeRange;
        }

        public List<String> getDeviceManuFacturer() {
            return deviceManuFacturer;
        }

        public void setDeviceManuFacturer(List<String> deviceManuFacturer) {
            this.deviceManuFacturer = deviceManuFacturer;
        }

        @Override
        public String toString() {
            StringBuffer sb = new StringBuffer();
            sb.append(" adType = ").append(adType);
            sb.append(" enable = ").append(enable);
            sb.append(" ratio = ").append(ratio);
            sb.append(" isVPN = ").append(isVPN);
            sb.append(" isRoot = ").append(isRoot);
            sb.append(" isSimulator = ").append(isSimulator);
            sb.append(" appVersion = ").append(appVersion.toString());
            sb.append(" osType = ").append(osType.toString());
            sb.append(" phoneType = ").append(phoneType.toString());
            sb.append(" isoCountryCode = ").append(isoCountryCode.toString());
            sb.append(" osTypeRange = ").append(osTypeRange.toString());
            sb.append(" deviceManuFacturer = ").append(deviceManuFacturer.toString());
            return sb.toString();
        }
    }
}
