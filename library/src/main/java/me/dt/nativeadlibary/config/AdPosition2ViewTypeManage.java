package me.dt.nativeadlibary.config;

import java.util.LinkedHashMap;
import java.util.Map;

import me.dt.nativeadlibary.util.L;
import me.dt.nativeadlibary.view.AdViewType;

public class AdPosition2ViewTypeManage {

    private Map<Integer, Integer> adPosition2ViewTypeMap = new LinkedHashMap<>();
    private static final AdPosition2ViewTypeManage ourInstance = new AdPosition2ViewTypeManage();

    public static AdPosition2ViewTypeManage getInstance() {
        return ourInstance;
    }

    private AdPosition2ViewTypeManage() {
        initData();
    }

    private void initData() {
        L.d("AdPosition2ViewTypeManage", "AdPosition2ViewTypeManage  initData");
        adPosition2ViewTypeMap.put(0, AdViewType.BANNER);
        adPosition2ViewTypeMap.put(1, AdViewType.LOADING);
        adPosition2ViewTypeMap.put(2, AdViewType.INTERSTITIAL);
        adPosition2ViewTypeMap.put(3, AdViewType.INS_SDK);
        adPosition2ViewTypeMap.put(4, AdViewType.SPLASH);
        adPosition2ViewTypeMap.put(5, AdViewType.LUCKY_BOX);
        adPosition2ViewTypeMap.put(6, AdViewType.VIDEO_OFFER);
        adPosition2ViewTypeMap.put(7, AdViewType.SPECIAL_OFFER);
    }

    public int getAdViewType(int adPosition) {
        if (adPosition2ViewTypeMap.get(adPosition) != null) {
            return adPosition2ViewTypeMap.get(adPosition);
        } else {
            throw new RuntimeException("this adPosition not config please add in AdPosition2ViewTypeMap AdPosition = " + adPosition);
        }
    }
}
