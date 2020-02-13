package me.dt.nativead;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.dt.nativeadlibary.util.AdProviderType;
import me.dt.nativeadlibary.config.NativeAdLibManager;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickAdmob(View view) {

        List<Integer> adList = new ArrayList<>();
        adList.add(AdProviderType.AD_PROVIDER_TYPE_ADMOB_NATIVE);
        NativeAdLibManager.getInstance().addAdListTest(adList);

        AdShowActivity.start(this, AdProviderType.AD_PROVIDER_TYPE_ADMOB_NATIVE);
    }

    public void onClickFacebook(View view) {

        List<Integer> adList = new ArrayList<>();
        adList.add(AdProviderType.AD_PROVIDER_TYPE_FB_NATIVE);
        NativeAdLibManager.getInstance().addAdListTest(adList);

        AdShowActivity.start(this, AdProviderType.AD_PROVIDER_TYPE_FB_NATIVE);
    }

    public void onClickFlurry(View view) {

        List<Integer> adList = new ArrayList<>();
        adList.add(AdProviderType.AD_PROVIDER_TYPE_FLURRY_NATIVE);
        NativeAdLibManager.getInstance().addAdListTest(adList);

        AdShowActivity.start(this, AdProviderType.AD_PROVIDER_TYPE_FLURRY_NATIVE);
    }

    public void onClickInmobi(View view) {

        List<Integer> adList = new ArrayList<>();
        adList.add(AdProviderType.AD_PROVIDER_TYPE_INMOBI_NATIVE);
        NativeAdLibManager.getInstance().addAdListTest(adList);

        AdShowActivity.start(this, AdProviderType.AD_PROVIDER_TYPE_INMOBI_NATIVE);
    }

    public void onClickMopub(View view) {

        List<Integer> adList = new ArrayList<>();
        adList.add(AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE);
        NativeAdLibManager.getInstance().addAdListTest(adList);

        AdShowActivity.start(this, AdProviderType.AD_PROVIDER_TYPE_MOPUB_NATIVE);
    }

    public void onClickSmaato(View view) {

        List<Integer> adList = new ArrayList<>();
        adList.add(AdProviderType.AD_PROVIDER_TYPE_SMAATO_NATIVE);
        NativeAdLibManager.getInstance().addAdListTest(adList);

        AdShowActivity.start(this, AdProviderType.AD_PROVIDER_TYPE_SMAATO_NATIVE);
    }

    public void onClickTest(View view) {
        startActivity(new Intent(this,TestActivity.class));
    }
}
