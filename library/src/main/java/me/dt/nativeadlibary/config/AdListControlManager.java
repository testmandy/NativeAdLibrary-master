package me.dt.nativeadlibary.config;


import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.dt.nativeadlibary.util.L;
import me.dt.nativeadlibary.util.RootUtil;
import me.dt.nativeadlibary.util.SharedPreferencesUtil;
import me.dt.nativeadlibary.util.ToolsForAd;


public class AdListControlManager {

    private final String TAG = "AdListControlManager";
    private volatile static AdListControlManager instance;
    private Map<Integer, Integer> adCountLimit = new HashMap<>();
    private int randomRatio = (int) (Math.random() * 100 + 0.5);

    public static AdListControlManager getInstance() {
        if (instance == null) {
            synchronized (AdListControlManager.class) {
                instance = new AdListControlManager();
            }
        }
        return instance;
    }


    private boolean needReOrder(int adType) {
        int adLimit = getAdCountLimitByAdType(adType);
        //adLimit == -1 代表取到默认值，不做次数限制
        return adLimit != -1 && getShowAdTimesOneDay(adType) >= adLimit;
    }

    public void addShowedAdCount(int adType) {
        L.d(TAG, "显示native广告商 adType = " + adType);
        SharedPreferencesUtil.setLastShowAdTime(adType, System.currentTimeMillis());
        SharedPreferencesUtil.setShowAdTimesToday(adType, SharedPreferencesUtil.getShowAdTimesToday(adType) + 1);
    }

    private void initNativeAdCountLimit() {
        try {

            if (NativeAdLibManager.getInstance().getNativeAdLibConfig() == null) {
                return;
            }

            String nativeAdCountLimit = NativeAdLibManager.getInstance().getNativeAdLibConfig().getAdFlowControl().getNativeAdCountLimit();
            L.d(TAG, " 初始化次数限制配置 nativeAdCountLimit = " + nativeAdCountLimit);
            if (!TextUtils.isEmpty(nativeAdCountLimit)) {
                adCountLimit.clear();
                if (nativeAdCountLimit.contains(",")) {
                    String[] videoCountLimitPairs = nativeAdCountLimit.split(",");
                    for (String videoCountLimitPair : videoCountLimitPairs) {
                        String[] onePair = videoCountLimitPair.split("-");
                        if (onePair.length == 2) {
                            adCountLimit.put(Integer.parseInt(onePair[0]), Integer.parseInt(onePair[1]));
                        }
                    }
                } else {
                    String[] onePair = nativeAdCountLimit.split("-");
                    adCountLimit.clear();
                    if (onePair.length == 2) {
                        adCountLimit.put(Integer.parseInt(onePair[0]), Integer.parseInt(onePair[1]));
                    }
                }
            }
        } catch (Exception e) {
            L.d(TAG, "initNativeAdCountLimit Exception");
        }
    }


    private int getAdCountLimitByAdType(int adproviderType) {
        if (adCountLimit.size() == 0) {
            initNativeAdCountLimit();
            return -1;
        }
        Integer count = adCountLimit.get(adproviderType);
        if (count != null) {
            return count;
        } else {
            return -1;
        }
    }


    private int getShowAdTimesOneDay(int adType) {
        long lastShowAdTime = SharedPreferencesUtil.getLastShowAdTime(adType);
        long curTime = System.currentTimeMillis();

        if (lastShowAdTime == 0) {
            SharedPreferencesUtil.setShowAdTimesToday(adType, 0);
            return 0;

        } else {

            boolean isSameDay = ToolsForAd.getDifferedDayFromDay(lastShowAdTime, curTime);

            if (isSameDay) {
                L.d(TAG, "该广告商今天显示了： " + SharedPreferencesUtil.getShowAdTimesToday(adType) + " adType = " + adType);
                return SharedPreferencesUtil.getShowAdTimesToday(adType);
            } else {
                L.d(TAG, "该广告商第二天次数置0 adType = " + adType);
                SharedPreferencesUtil.setShowAdTimesToday(adType, 0);
                return 0;
            }
        }
    }

    public void reOrderAdListForAd(int adPosition, List<Integer> adList) {
        try {
            reOrderAdList(adPosition, adList);
        } catch (Exception e) {
            e.printStackTrace();
            L.d(TAG, "reOrderAdListForAd Exception = " + e.getMessage());
        }
    }

    private void reOrderAdList(int adPosition, List<Integer> adList) {

        if (true) {
            return;
        }

        if (adList != null && adList.size() <= 1) {
            return;
        }

        if (NativeAdLibManager.getInstance().getNativeAdLibConfig() == null) {
            return;
        }
        NativeAdLibConfig.AdFlowControlBean adFlowControl = NativeAdLibManager.getInstance().getNativeAdLibConfig().getAdFlowControl();
        L.d(TAG, "排序前的广告链 adList " + adList.toString() + "   adPosition = " + adPosition);

        if (adFlowControl.getEnable() == 0) {
            L.d(TAG, "广告商导流功能关闭");
            return;
        }

        if (adFlowControl.getAdPlacementEnable().contains(adPosition)) {
            L.d(TAG, "该广告位不支持导流功能 adPosition = " + adPosition);
            return;
        }

        if (adCountLimit.size() == 0) {
            initNativeAdCountLimit();
        }

        ArrayList tempList = new ArrayList<Integer>();
        Iterator<Integer> tempListIterator = adList.iterator();
        while (tempListIterator.hasNext()) {
            int adType = tempListIterator.next();
            if (adCountLimit.size() > 0) {
                if (!adCountLimit.containsKey(adType)) {
                    tempList.add(adType);
                    tempListIterator.remove();
                }
            }
        }

        L.d(TAG, "不支持排序的广告商列表 " + tempList.toString());

        ArrayList sortList = new ArrayList<Integer>();

        Iterator<Integer> iterator = adList.iterator();
        while (iterator.hasNext()) {
            int adType = iterator.next();
            if (needReOrder(adType)) {
                L.d(TAG, "该广告商到达显示次数需要重新排序 adType = " + adType);
                iterator.remove();
                sortList.add(adType);
            }
        }
        L.d(TAG, "支持排序的广告商并且次数到达限制 进行排序以后的结果 " + sortList.toString());
        adList.addAll(sortList);
        adList.addAll(tempList);
        L.d(TAG, "最终返回的排序后的广告链 " + adList.toString() + "   adPosition = " + adPosition);
    }


    public boolean canUseAd(int adType) {
        try {
            return canUseAdCheckByAdType(adType);
        } catch (Exception e) {
            Log.i(TAG, "canUseAd error: " + e.getMessage());
            return true;
        }
    }

    /**
     * 广告精细化控制，根据线上配置，决定广告是否可以使用
     */
    private boolean canUseAdCheckByAdType(int adType) {
        NativeAdLibConfig.IndependenceAdEnableBean independenceAdEnableBean = getIndependenceAdEnableBeenByAdType(adType);
        if (independenceAdEnableBean == null) {
            L.d(TAG, "canUseAd true independenceAdEnableBean == null ");
            return true;
        }

        if (independenceAdEnableBean.getEnable() == 0) {
            L.d(TAG, "canUseAd true getEnable == 0 ");
            return true;
        }
        L.d(TAG, "canUseAd  getIsoCountryCode == " + DTConstant.COUNTRY_CODE_ID);
        if (independenceAdEnableBean.getIsoCountryCode().size() != 0 && independenceAdEnableBean.getIsoCountryCode().contains(DTConstant.COUNTRY_CODE_ID)) {
            L.d(TAG, "canUseAd false getIsoCountryCode contains " + DTConstant.COUNTRY_CODE_ID);
            return false;
        }
        L.d(TAG, "canUseAd getAppVersion ==  " + ToolsForAd.getVersionName() + "." + ToolsForAd.getVersionCode());
        if (independenceAdEnableBean.getAppVersion().size() != 0 && independenceAdEnableBean.getAppVersion().contains(ToolsForAd.getVersionName() + "." + ToolsForAd.getVersionCode())) {
            L.d(TAG, "canUseAd false getAppVersion contains " + ToolsForAd.getVersionName() + "." + ToolsForAd.getVersionCode());
            return false;
        }
        L.d(TAG, "canUseAd Build.VERSION.SDK ==  " + Build.VERSION.RELEASE);
        if (independenceAdEnableBean.getOsType().size() != 0 && independenceAdEnableBean.getOsType().contains(Build.VERSION.RELEASE)) {
            L.d(TAG, "canUseAd false getOsType contains " + Build.VERSION.RELEASE);
            return false;
        }
        if (independenceAdEnableBean.getOsTypeRange().size() != 0 && Build.VERSION.RELEASE.length() >= 3 && independenceAdEnableBean.getOsTypeRange().contains(Build.VERSION.RELEASE.substring(0, 3))) {
            L.d(TAG, "canUseAd false getOsTypeRange contains " + Build.VERSION.RELEASE);
            return false;
        }
        L.d(TAG, "canUseAd Build.MODEL ==  " + Build.MODEL);
        if (independenceAdEnableBean.getPhoneType().size() != 0 && independenceAdEnableBean.getPhoneType().contains(Build.MODEL)) {
            L.d(TAG, "canUseAd false getPhoneType contains " + Build.MODEL);
            return false;
        }

        L.d(TAG, "canUseAd Build.MANUFACTURER ==  " + Build.MANUFACTURER);
        if (independenceAdEnableBean.getDeviceManuFacturer().size() != 0 && independenceAdEnableBean.getDeviceManuFacturer().contains(Build.MANUFACTURER)) {
            L.d(TAG, "canUseAd false getDeviceManuFacturer contains " + Build.MANUFACTURER);
            return false;
        }

        L.d(TAG, "canUseAd isDeviceRooted ==  " + RootUtil.getInstance().isDeviceRooted());
        if (independenceAdEnableBean.getIsRoot() == 1 && RootUtil.getInstance().isDeviceRooted()) {
            L.d(TAG, "canUseAd false getIsRoot == 1");
            return false;
        }

        if (independenceAdEnableBean.getIsSimulator() == 1 && RootUtil.isRunningOnEmulator()) {
            L.d(TAG, "canUseAd false getIsSimulator == 1");
            return false;
        }

        L.d(TAG, "canUseAd randomRatio ==  " + randomRatio);
        if (independenceAdEnableBean.getRatio() != 0 && independenceAdEnableBean.getRatio() * 100 < randomRatio) {
            L.d(TAG, "canUseAd false radio < randomRatio  radio = " + independenceAdEnableBean.getRatio() * 100 + " randomRatio = " + randomRatio);
            return false;
        }

        return true;
    }

    private NativeAdLibConfig.IndependenceAdEnableBean getIndependenceAdEnableBeenByAdType(int adType) {
        if (NativeAdLibManager.getInstance().getNativeAdLibConfig() == null) {
            return null;
        }
        List<NativeAdLibConfig.IndependenceAdEnableBean> mIndependenceAdEnable = NativeAdLibManager.getInstance().getNativeAdLibConfig().getIndependenceAdEnable();
        if (mIndependenceAdEnable != null) {
            for (int i = 0; i < mIndependenceAdEnable.size(); i++) {
                if (mIndependenceAdEnable.get(i).getAdType() == adType) {
                    Log.i(TAG, "independenceAdEnableBeans = " + mIndependenceAdEnable.get(i).toString());
                    return mIndependenceAdEnable.get(i);
                }
            }
        }
        return null;
    }
}
