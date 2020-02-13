package me.dt.nativeadlibary.ad;

import me.dt.nativeadlibary.util.ToolsForAd;

public class OfferType {

    public static final int OFFER_TYPE_NONINSTALL = 0;

    public static final int OFFER_TYPE_INSTALL = 1;


    public static int getOfferType(String callToAction) {
        if (ToolsForAd.containInstallWords(callToAction)){
            return OFFER_TYPE_INSTALL;
        }
        return OFFER_TYPE_NONINSTALL;
    }
}
