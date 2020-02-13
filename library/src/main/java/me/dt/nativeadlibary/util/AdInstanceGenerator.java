package me.dt.nativeadlibary.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.Map;

import me.dt.nativeadlibary.ad.loader.INativeAdLoader;
import me.dt.nativeadlibary.config.NativeAdConfig;


/**
 * Created by joybar on 2017/5/3.
 */

public class AdInstanceGenerator extends IGenerator {

    private Map<Integer, INativeAdLoader> adInstanceServiceMap = new LinkedHashMap<>();

    @Override
    public synchronized <T extends INativeAdLoader> T generateAdLoadInstance(Class<T> clazz, NativeAdConfig adInstanceConfiguration) {


        INativeAdLoader adLoadInstance = null;

        if (adInstanceServiceMap.get(adInstanceConfiguration.adType) != null) {
            adLoadInstance = adInstanceServiceMap.get(adInstanceConfiguration.adType);
        } else {
            try {
                Constructor constructor = clazz.getDeclaredConstructor();
                constructor.setAccessible(true);
                adLoadInstance = (INativeAdLoader) constructor.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return (T) adLoadInstance;
    }


    private AdInstanceGenerator() {

    }

    private static class AdLoaderGeneratorHolder {
        private static final AdInstanceGenerator INSTANCE = new AdInstanceGenerator();
    }

    public static AdInstanceGenerator newInstance() {
        return AdLoaderGeneratorHolder.INSTANCE;
    }


    public INativeAdLoader produceAdInstance(NativeAdConfig adInstanceConfiguration) {
        INativeAdLoader adInstanceService = null;
        try {
            adInstanceService = generateAdLoadInstance(adInstanceConfiguration.clazz, adInstanceConfiguration);
            adInstanceServiceMap.put(adInstanceConfiguration.adType, adInstanceService);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adInstanceService;
    }


}
