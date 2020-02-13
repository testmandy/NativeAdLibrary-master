package me.dt.nativead;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.dt.nativeadlibary.ad.AdCallbackListener;
import me.dt.nativeadlibary.ad.ErrorMsg;
import me.dt.nativeadlibary.ad.data.BaseNativeAdData;
import me.dt.nativeadlibary.config.NativeAdLibManager;
import me.dt.nativeadlibary.manager.AdCenterManager;
import me.dt.nativeadlibary.view.NativeAdBannerView;

public class TestActivity extends Activity implements AdCallbackListener {
    EditText editText;
    NativeAdBannerView nativeAdBannerView;
    RelativeLayout adCantiner;
    AdCenterManager adCenterManager;
    String testAdConfig = "{\"bannerRefreshTime\":10000,\"nativeAdList\":[{\"adPostition\":0,\"adList\":[34,22,112,39]},{\"adPostition\":1,\"adList\":[34,22,112,39]}],\"adFlowControl\":{\"enable\":1,\"adPlacementEnable\":[],\"nativeAdCountLimit\":\"34-3,22-3,39-3,112-3\",\"resetAdCacheCount\":\"34-2,39-2,22-2,112-2\"},\"singleAdConfig\":[{\"adType\":34,\"key\":\"ca-app-pub-1033413373457510/3017898982\",\"placementId\":\"3967238947\",\"cacheCount\":2,\"requestCount\":2,\"videoOfferEnable\":\"1\",\"videoOfferCountry\":[\"cn\"],\"lowValue\":6,\"highValue\":10,\"retryTime\":2,\"timeOut\":5000}],\"independenceAdEnable\":[{\"adType\":0,\"deviceManuFacturer\":[\"xiaomi\",\"samsung\"],\"enable\":1,\"isVPN\":1,\"isRoot\":1,\"isSimulator\":1,\"ratio\":1}]}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        NativeAdLibManager.Builder builder = new NativeAdLibManager.Builder(this.getApplication());
        builder.setCountryCode("cn")
                .setNativeAdLibConfig(testAdConfig);

        if (adCenterManager == null){
            adCenterManager = new AdCenterManager();
            adCenterManager.preloadAd(this,0);
        }
        editText = findViewById(R.id.edit_ad_list);
        nativeAdBannerView = findViewById(R.id.banner_view);
        adCantiner = findViewById(R.id.ad_container);
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String adListStr = editText.getText().toString();
                String[] split = adListStr.split(",");
                List<Integer> adList = new ArrayList<>();
                for (int i = 0; i < split.length; i++) {
                    adList.add(Integer.parseInt(split[i]));
                }
                NativeAdLibManager.getInstance().addAdListTest(adList);

                Toast.makeText(TestActivity.this,"配置广告链：" + adList,Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.btn_banner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             nativeAdBannerView.setPlacement(0);
             nativeAdBannerView.loadAd();
            }
        });


        findViewById(R.id.btn_longing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adCenterManager.load(TestActivity.this,1,adCantiner,TestActivity.this);
            }
        });

        findViewById(R.id.btn_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                adCenterManager.load(TestActivity.this,2,adCantiner,TestActivity.this);
            }
        });

        findViewById(R.id.btn_ins).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adCenterManager.load(TestActivity.this,3,adCantiner,TestActivity.this);
            }
        });

        findViewById(R.id.btn_lucky_box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adCenterManager.load(TestActivity.this,5,adCantiner,TestActivity.this);
            }
        });

        findViewById(R.id.btn_video_offer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adCenterManager.load(TestActivity.this,6,adCantiner,TestActivity.this);
            }
        });
        findViewById(R.id.btn_splash).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adCenterManager.load(TestActivity.this,4,adCantiner,TestActivity.this);
            }
        });

        findViewById(R.id.btn_special).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adCenterManager.load(TestActivity.this,7,adCantiner,TestActivity.this);
            }
        });
    }

    @Override
    public void onLoadFailed(ErrorMsg errorMsg) {

    }

    @Override
    public void onLoadNoCacheFailed(ErrorMsg errorMsg) {

    }

    @Override
    public void onLoadSuccess(BaseNativeAdData nativeAdData) {

    }

    @Override
    public void onClick(int adType) {

    }

    @Override
    public void onImpression(int adType) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nativeAdBannerView != null){
            nativeAdBannerView.setAlive(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (nativeAdBannerView != null){
            nativeAdBannerView.setAlive(false);
        }
    }


}
