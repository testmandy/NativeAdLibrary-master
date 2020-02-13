package me.dt.nativeadlibary.ad;

import me.dt.nativeadlibary.ad.data.BaseNativeAdData;

/**
 * Created by Joy on 2019-10-17
 */
public interface AdCallbackListener {

  void onLoadFailed(ErrorMsg errorMsg);

  void onLoadNoCacheFailed(ErrorMsg errorMsg);

  void onLoadSuccess(BaseNativeAdData nativeAdData);

  void onClick(int adType);

  void onImpression(int adType);
}
