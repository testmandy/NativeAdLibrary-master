package me.dt.nativeadlibary.util;


import me.dt.nativeadlibary.ad.loader.INativeAdLoader;
import me.dt.nativeadlibary.config.NativeAdConfig;


/**
 * Created by joybar on 2017/5/3.
 */

public abstract class IGenerator {
    public abstract <T extends INativeAdLoader> T generateAdLoadInstance(Class<T> clazz, NativeAdConfig adInstanceConfiguration);
}
