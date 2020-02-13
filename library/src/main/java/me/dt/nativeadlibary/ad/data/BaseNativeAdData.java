package me.dt.nativeadlibary.ad.data;

import me.dt.nativeadlibary.ad.OfferType;

public abstract class BaseNativeAdData implements INativeAdData {

    private static final long EXPIRED_TIME = 30 * 60 * 1000;

    protected Object originData;

    protected String title = "";
    protected String content = "";
    protected String logoUrl = "";
    protected String bigImgUrl = "";
    protected String callToAction = "";
    protected String packageName = "";
    protected String source = "";

    protected int offerType = OfferType.OFFER_TYPE_NONINSTALL;
    protected int adProviderType;

    private long createTime;

    public BaseNativeAdData(Object originData) {
        this.originData = originData;
        this.createTime = System.currentTimeMillis();

        doPack();
    }

    public abstract void doPack();

    @Override
    public abstract String getAdName();

    @Override
    public abstract int getAdType();

    public abstract Object getAdData();

    public abstract Class getViewProducerClass();

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getBigImgUrl() {
        return bigImgUrl;
    }

    public String getCallToAction() {
        return callToAction;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getSource() {
        return source;
    }

    public int getOfferType() { return offerType; }

    public int getAdProviderType() {
        return adProviderType;
    }

    public boolean isDownloadType() {
        return offerType == OfferType.OFFER_TYPE_INSTALL;
    }

    public boolean isAdExpired() {
        return System.currentTimeMillis() - createTime > EXPIRED_TIME;
    }

    @Override
    public String toString() {
        return "Ad Type : " + getAdType()
                + " adProviderType : " + adProviderType
                + " title : " + getTitle()
                + " isDownloadType : " + isDownloadType();
    }
}
