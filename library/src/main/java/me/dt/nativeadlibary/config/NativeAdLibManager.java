package me.dt.nativeadlibary.config;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.dt.client.android.analytics.exception.EventException;
import com.dt.client.android.analytics.net.gson.EGson;
import com.dt.client.android.analytics.net.gson.GsonBuilder;
import com.smaato.soma.internal.utilities.StringUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import me.dt.nativeadlibary.util.AdProviderType;
import me.dt.nativeadlibary.util.L;
import me.dt.nativeadlibary.manager.AdInstanceClassMapManager;


public class NativeAdLibManager {

  private static final String TAG = "NativeConfigManager";
  private NativeAdLibConfig nativeAdLibConfig;
  private Application app;//全局持有app,保证sdk正常运转. app引用与进程同生命周期
  private List<Integer> testAdList = new ArrayList<>();
  private NativeAdLibManager(){}

  private static class Instance{
      private static NativeAdLibManager instance = new NativeAdLibManager();
  }


 public static NativeAdLibManager getInstance(){
      return Instance.instance;
 }


    /**
     * 获取application 上下文
     *
     * @return
     */
    public Context getContext() {
        if (app == null) {
            throw new EventException("请先在application中实例化NativeAdLibManager");
        }
        return app;
    }

    /**
     * 初始化sdk, 要在application中的onCreate() 方法中进行初始化.
     *
     * @param application 全局上下文
     * @param isDebug     是否是debug模式(控制开启log等)
     */
    private void init(Application application, boolean isDebug) {
        if (application == null){
            L.d(TAG, " DTEventManager application==null!");
            return;
        }
        DTConstant.DEVELOP_MODE = isDebug;//是否是开发模式
        app = application;
    }

 private void initNativeAdLibConfig(String config){
     L.e(TAG,"initNativeAdLibConfig config = " + config);
    if (StringUtils.isEmpty(config)){
        return;
    }
     try {
         EGson eGson = new GsonBuilder().disableHtmlEscaping().create();
         nativeAdLibConfig = eGson.fromJson(config, NativeAdLibConfig.class);
     } catch (Exception e) {
         L.e(TAG,"Parsing nativeAdLibConfig error = " + e.getMessage());
     }
 }


    /**
     * 获取总配置中广告商的独立配置
     * @param adType 广告商id
     * @return
     */
 private NativeAdLibConfig.SingleAdConfigBean getSingleAdConfigBean(int adType){
      if (nativeAdLibConfig != null){
          List<NativeAdLibConfig.SingleAdConfigBean> singleAdConfig = nativeAdLibConfig.getSingleAdConfig();
          if (singleAdConfig != null){
              int size = singleAdConfig.size();
              for (int i = 0; i < size; i++) {
                  if (singleAdConfig.get(i).getAdType() == adType){
                      return singleAdConfig.get(i);
                  }
              }
          }
      }
      return null;
 }

    /**
     * 初始化单个广告商的配置
     * @param adType 广告商id
     * @return
     */
 public NativeAdConfig getSingleNativeAdConfig(int adType){
     NativeAdLibConfig.SingleAdConfigBean singleAdConfigBean = getSingleAdConfigBean(adType);
     if (singleAdConfigBean != null){
         NativeAdConfig.Builder builder = new NativeAdConfig.Builder();
         return builder.setActivity(null)
                 .setContext(null)
                 .setAdType(adType)
                 .setKey(singleAdConfigBean.getKey())
                 .setPlacementId(singleAdConfigBean.getPlacementId())
                 .setMaxCachePoolCount(singleAdConfigBean.getCacheCount())
                 .setMaxRequestCount(singleAdConfigBean.getRequestCount())
                 .setVideoOfferEnable(singleAdConfigBean.getVideoOfferEnable())
                 .setVideoOfferCountry(singleAdConfigBean.getVideoOfferCountry())
                 .setLowReward(singleAdConfigBean.getLowValue())
                 .setHighReward(singleAdConfigBean.getHighValue())
                 .setRetryTimes(singleAdConfigBean.getRetryTime())
                 .setTimeOut(singleAdConfigBean.getTimeOut())
                 .setDownloadTypeRequestCount(singleAdConfigBean.getDownloadTypeRequestCount())
                 .setDebug(true)
                 .setClazz(AdInstanceClassMapManager.getAdInstanceClassWithAdProviderType(adType))
                 .build();
     }else {
        return getDefaultNativeAdConfig(adType);
     }
 }


    /**
     * 获取默认的广告商配置
     * @param adType 广告商id
     * @return
     */
    private NativeAdConfig getDefaultNativeAdConfig(int adType){
        NativeAdConfig.Builder builder = new NativeAdConfig.Builder();

        switch (adType){
            case AdProviderType.AD_PROVIDER_TYPE_ADMOB_NATIVE:
                return builder.setActivity(null)
                        .setContext(null)
                        .setAdType(AdProviderType.AD_PROVIDER_TYPE_ADMOB_NATIVE)
//                        .setKey("ca-app-pub-1033413373457510/3017898982")
                        .setKey("ca-app-pub-3772005976135148/5930676852")
                        .setPlacementId("")
                        .setMaxCachePoolCount(3)
                        .setMaxRequestCount(3)
                        .setVideoOfferEnable(1)
                        .setVideoOfferCountry(null)
                        .setLowReward(6)
                        .setHighReward(10)
                        .setRetryTimes(3)
                        .setTimeOut(5000)
                        .setDownloadTypeRequestCount(2)
                        .setDebug(true)
                        .setClazz(AdInstanceClassMapManager.getAdInstanceClassWithAdProviderType(AdProviderType.AD_PROVIDER_TYPE_ADMOB_NATIVE))
                        .build();
            case AdProviderType.AD_PROVIDER_TYPE_FLURRY_NATIVE:
                return builder.setActivity(null)
                        .setContext(null)
                        .setAdType(AdProviderType.AD_PROVIDER_TYPE_FLURRY_NATIVE)
                        .setKey("3TCX7QSWZTRSBZMM684G")
                        .setPlacementId("BitVPN_StreamAdSpace")
//                        .setKey("ZTBZJ7NMTZCDJ5Y22R4Q")
//                        .setPlacementId("SkyVPN_StreamAdSpace")
                        .setMaxCachePoolCount(3)
                        .setMaxRequestCount(3)
                        .setVideoOfferEnable(0)
                        .setVideoOfferCountry(null)
                        .setLowReward(6)
                        .setHighReward(10)
                        .setRetryTimes(3)
                        .setTimeOut(5000)
                        .setDownloadTypeRequestCount(2)
                        .setDebug(true)
                        .setClazz(AdInstanceClassMapManager.getAdInstanceClassWithAdProviderType(AdProviderType.AD_PROVIDER_TYPE_FLURRY_NATIVE))
                        .build();
            case AdProviderType.AD_PROVIDER_TYPE_FB_NATIVE:
                return builder.setActivity(null)
                        .setContext(null)
                        .setAdType(AdProviderType.AD_PROVIDER_TYPE_FB_NATIVE)
                        .setKey("451320148219183_2599093403441836")
                        .setPlacementId("")
                        .setMaxCachePoolCount(3)
                        .setMaxRequestCount(3)
                        .setVideoOfferEnable(0)
                        .setVideoOfferCountry(null)
                        .setLowReward(6)
                        .setHighReward(10)
                        .setRetryTimes(3)
                        .setTimeOut(5000)
                        .setDownloadTypeRequestCount(2)
                        .setDebug(true)
                        .setClazz(AdInstanceClassMapManager.getAdInstanceClassWithAdProviderType(AdProviderType.AD_PROVIDER_TYPE_FB_NATIVE))
                        .build();
            case AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE:
                return builder.setActivity(null)
                        .setContext(null)
                        .setAdType(AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE)
                        .setKey("11a17b188668469fb0412708c3d16813")
                        .setPlacementId("")
                        .setMaxCachePoolCount(3)
                        .setMaxRequestCount(3)
                        .setVideoOfferEnable(1)
                        .setVideoOfferCountry(null)
                        .setLowReward(6)
                        .setHighReward(10)
                        .setRetryTimes(3)
                        .setTimeOut(5000)
                        .setDownloadTypeRequestCount(2)
                        .setDebug(true)
                        .setClazz(AdInstanceClassMapManager.getAdInstanceClassWithAdProviderType(AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE))
                        .build();
            case AdProviderType.AD_PROVIDER_TYPE_INMOBI_NATIVE:
                return builder.setActivity(null)
                        .setContext(null)
                        .setAdType(AdProviderType.AD_PROVIDER_TYPE_INMOBI_NATIVE)
                        .setKey("32263d00329c4726807fefa86903a0f9")
                        .setPlacementId("1535476473906")
                        .setMaxCachePoolCount(3)
                        .setMaxRequestCount(3)
                        .setVideoOfferEnable(0)
                        .setVideoOfferCountry(null)
                        .setLowReward(6)
                        .setHighReward(10)
                        .setRetryTimes(3)
                        .setTimeOut(5000)
                        .setDownloadTypeRequestCount(2)
                        .setDebug(true)
                        .setClazz(AdInstanceClassMapManager.getAdInstanceClassWithAdProviderType(AdProviderType.AD_PROVIDER_TYPE_INMOBI_NATIVE))
                        .build();
            case AdProviderType.AD_PROVIDER_TYPE_SMAATO_NATIVE:
                return builder.setActivity(null)
                        .setContext(null)
                        .setAdType(AdProviderType.AD_PROVIDER_TYPE_SMAATO_NATIVE)
                        .setKey("1100043889")
                        .setPlacementId("130682423")
                        .setMaxCachePoolCount(3)
                        .setMaxRequestCount(3)
                        .setVideoOfferEnable(0)
                        .setVideoOfferCountry(null)
                        .setLowReward(6)
                        .setHighReward(10)
                        .setRetryTimes(3)
                        .setTimeOut(5000)
                        .setDownloadTypeRequestCount(2)
                        .setDebug(true)
                        .setClazz(AdInstanceClassMapManager.getAdInstanceClassWithAdProviderType(AdProviderType.AD_PROVIDER_TYPE_SMAATO_NATIVE))
                        .build();
        }
        return null;
    }

    /**
     * 添加测试广告链
     * @param adList 测试广告链
     */
    public void addAdListTest(List<Integer> adList){
        testAdList = adList;
    }

    /**
     * 广告链重排序
     * @param position 广告位
     * @return
     */
    public List<Integer> produceAdListByPosition(int position){

        if (testAdList.size() > 0){
            return testAdList;
        }

        List<Integer> originAdList = getAdListByPosition(position);
        List<Integer> filterAdList = new ArrayList<>();
        for (int i = 0; i < originAdList.size(); i++) {
            if (AdListControlManager.getInstance().canUseAd(originAdList.get(i))){
                filterAdList.add(originAdList.get(i));
            }
        }
        L.d(TAG,"produceAdListByPosition adPosition  = " + position + " filterAdList = " + Arrays.toString(filterAdList.toArray()));
        AdListControlManager.getInstance().reOrderAdListForAd(position,filterAdList);
        return filterAdList;
    }

    /**
     * 根据广告位获得广告链
     * @param position 广告位
     * @return
     */
    public List<Integer> getAdListByPosition(int position){
     if (nativeAdLibConfig != null){
         List<NativeAdLibConfig.NativeAdListBean> nativeAdList = nativeAdLibConfig.getNativeAdList();
         if (nativeAdList != null){
             int size = nativeAdList.size();
             for (int i = 0; i < size ; i++) {
                if (nativeAdList.get(i).getAdPostition() == position){
                    L.d(TAG,"loadAd getAdListByPosition  = " + nativeAdList.get(i).getAdList().toString());
                    return nativeAdList.get(i).getAdList();
                }
             }
         }
     }
             List<Integer> defaultAdList = new ArrayList<>();
             defaultAdList.add(AdProviderType.AD_PROVIDER_TYPE_ADMOB_NATIVE);
             defaultAdList.add(AdProviderType.AD_PROVIDER_TYPE_FB_NATIVE);
             defaultAdList.add(AdProviderType.AD_PROVIDER_TYPE_FLURRY_NATIVE);
             defaultAdList.add(AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE);
//             defaultAdList.add(AdProviderType.AD_PROVIDER_TYPE_INMOBI_NATIVE);
//             defaultAdList.add(AdProviderType.AD_PROVIDER_TYPE_SMAATO_NATIVE);
             L.d(TAG,"loadAd getAdListByPosition  defaultAdList = " + defaultAdList.toString());
             return defaultAdList;

 }

    /**
     * 获得banner 刷新的时间
     * @return
     */
    public int getBannerRefreshTime(){
     if (nativeAdLibConfig != null){
       return nativeAdLibConfig.getBannerRefreshTime();
     }
     return 10000;
 }

    /**
     * 获取广告插件化整体配置
     * @return
     */
    public NativeAdLibConfig getNativeAdLibConfig() {
        return nativeAdLibConfig;
    }


    /**
     * 内部构建类
     * 优势:可以根据需求,在不改变原有架构API的基础上,灰常灵活的进行构建修改,方便的很~
     */
    public static class Builder {

        private Application application;
        private boolean DEVELOP_MODE = DTConstant.DEVELOP_MODE;

        public Builder(Application application) {
            this.application = application;
        }

        /**
         * 是否是开发者模式
         * @param isDebug
         * @return
         */
        public NativeAdLibManager.Builder setDebug(boolean isDebug) {
            DEVELOP_MODE = isDebug;
            return this;
        }

        /**
         * 设置用户国家码
         * @param countryCode 国家码
         * @return
         */
        public NativeAdLibManager.Builder setCountryCode(String countryCode) {
            DTConstant.COUNTRY_CODE_ID = countryCode;
            return this;
        }

        /**
         * 初始化native 插件配置
         * @param config
         * @return
         */
        public NativeAdLibManager.Builder setNativeAdLibConfig(String config){
            NativeAdLibManager.getInstance().initNativeAdLibConfig(config);
           return this;
        }

        /**
         * 开始构建
         */
        public void start() {
            Log.i(TAG, " NativeAdLibManager.Builder#start() " );
            if (application == null) {
                Log.e(TAG, " NativeAdLibManager.Builder#start() application:" + "不能为空!");
                return;
            }
            NativeAdLibManager.getInstance().init(application, DEVELOP_MODE);
        }
    }
}
