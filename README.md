                                          Native 组件化接入文档
                                          


1.接入方式：

	1.1 集成 api 'me.dt.native.ad.library:1.0.0',

  在项目总的build.gradle -》 allprojects 的 repositories中添加：

  maven { url "http://10.88.0.34:8081/repository/maven-public/" }

   1.2 在 Application的中onCreate中初始化 sdk

        NativeAdLibManager.Builder builder = new NativeAdLibManager.Builder(this); 
        builder.setCountryCode("cn") //国家码
                .setNativeAdLibConfig("") //native广告商配置
                .setDebug(true) //是否是测试模式
                .start(); 

   注意获取到sever的配置以后要再次调用以下代码更新配置  
     
    new NativeAdLibManager.Builder(this).setNativeAdLibConfig("") //native广告商配置; 
       
            
                

2.对外开放的接口

  2.1  预加载广告资源，可以提前缓存资源

    /**
     * 预加载广告资源
     * @param activity 上下文
     * @param adPosition 广告位
     */
    AdCenterManager#preloadAd(Activity activity, int adPosition)

  2.2  预加载广告资源，可以提前缓存资源，并对外提供缓存是否加载完成的回调

  	/**
     * 预加载广告资源
     * @param activity 上下文
     * @param adPosition 广告位
     * @param adCallbackListener 预加载广告资源回调
     */
    AdCenterManager#preloadAd(Activity activity, int adPosition, AdCallbackListener adCallbackListener)


  2.3 直接请求广告资源


    /**
     * 直接请求广告资源
     * @param activity 上下文
     * @param adPosition  广告位
     * @param adContainer 显示广告的布局容器
     * @param adCallbackListener 请求广告资源回调
     */
  
   AdCenterManager#load(Activity activity, int adPosition, ViewGroup adContainer, AdCallbackListener adCallbackListener)  
   
   
   
 
 
 
 
 ===================================================================================================================
 
 
 
 
 
   
                                                Native 组件化新广告商接入文档
                                                
                                                
                                                
                                                
                                                

对接一个新的广告商到插件需要完成以下步骤：

1.在该类中添加新接入广告商的广告商ID me.dt.nativeadlibary.util.AdProviderType

 例如：public static final int AD_PROVIDER_TYPE_SMAATO_NATIVE = 1243;


2.继承新接入广告商取资源的loader接口 me.dt.nativeadlibary.ad.loader.BaseNativeAdLoader

 实现以下方法：

 2.1 public void initialized(Context context, NativeAdConfig nativeConfig) //初始化 sdk 和 广告商配置

 2.2 protected String getLoaderName() //获得loader的名称

 2.3 protected ErrorMsg getErrorMsg() //获得加载资源返回的错误信息

 2.4 protected boolean shouldLoadInBackground() //是否使用子线程请求广告资源

 2.5 protected void loadDirectly(final boolean isDownloadType, final AdCallbackListener adCallbackListener) //广告商请求资源的具体实现

 2.6 protected AdMobNativeAdData packData(Object originData) //封装广告商返回的资源


3.继承新接入广告商数据封装接口 me.dt.nativeadlibary.ad.data.BaseNativeAdData

 实现以下方法：

 3.1 public void doPack()；//封装广告商返回的资源

 3.2 public int getAdType() //返回广告商类型

 3.3 public UnifiedNativeAd getAdData() //返回请求到的广告商资源

 3.4  public Class getViewProducerClass() //返回广告商的布局工厂类

 3.5 public String getAdName() //返回广告商的名称


4.实现广告布局工厂类IProducer

 4.1 View createBannerAdView(Context context, BaseNativeAdData data); // 生成banner布局

 4.2  View createLoadingAdView(Context context, BaseNativeAdData data); // 生成loading布局

 4.3  View createInterstialAdView(Context context, BaseNativeAdData data);// 生成Interstitial布局

 4.4  View createInsSdkAdView(Context context, BaseNativeAdData data);// 生成ins布局

 4.5  View createSplashAdView(Context context, BaseNativeAdData data);// 生成splash布局

 4.6  View createLuckyBoxAdView(Context context, BaseNativeAdData data);// 生成luckybox布局

 4.7  View createVideoOfferAdView(Context context, BaseNativeAdData data);// 生成video offer布局

 4.8 View createSpecialOfferAdView(Context context, BaseNativeAdData data);// 生成special offer布局

5.将新接入的广告商类型和取资源的loader实现按照key，value的形式加入到 me.dt.nativeadlibary.manager.AdInstanceClassMapManager#sAdClassMap 中用于反射

 例如：sAdClassMap.put(AdProviderType.AD_PROVIDER_TYPE_ADMOB_NATIVE, AdMobNativeAdLoader.class);

6.在这个方法me.dt.nativeadlibary.config.NativeAdLibManager#getDefaultNativeAdConfig中添加新接入广告商的默认配置

 例如：

 case AdProviderType.AD_PROVIDER_TYPE_FLURRY_NATIVE:
                return builder.setActivity(null)
                        .setContext(null)
                        .setAdType(AdProviderType.AD_PROVIDER_TYPE_FLURRY_NATIVE)
                        .setKey("3TCX7QSWZTRSBZMM684G")
                        .setPlacementId("BitVPN_StreamAdSpace")
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

  经过以上步骤就完成集成一个新广告商需要做的工作了；         