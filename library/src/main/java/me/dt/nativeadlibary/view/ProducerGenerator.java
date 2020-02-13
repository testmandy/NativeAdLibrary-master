package me.dt.nativeadlibary.view;

import android.util.SparseArray;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import me.dt.nativeadlibary.util.L;
import me.dt.nativeadlibary.view.producer.IProducer;

class ProducerGenerator {

    private static final String TAG = "ProducerGenerator";

    private static SparseArray<IProducer> sProducerArray = new SparseArray<>();

    static IProducer generate(int adType, Class clazz) {

        IProducer producer = sProducerArray.get(adType);

        if (producer != null) {
            return producer;
        }

        try {
            Constructor constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            producer = (IProducer) constructor.newInstance();
        } catch (NoSuchMethodException e) {
            L.e(TAG, "generate err1 : " + e.getLocalizedMessage());
        } catch (IllegalAccessException e) {
            L.e(TAG, "generate err2 : " + e.getLocalizedMessage());
        } catch (InstantiationException e) {
            L.e(TAG, "generate err3 : " + e.getLocalizedMessage());
        } catch (InvocationTargetException e) {
            L.e(TAG, "generate err4 : " + e.getLocalizedMessage());
        }

        if (producer != null) {
            sProducerArray.put(adType, producer);
        }

        return producer;
    }
}
