package me.dt.nativeadlibary.util;

public class DTTickCount {
    private long mLastTickTime;


    public DTTickCount() {
        mLastTickTime = System.nanoTime();

    }

    public void reset() {
        mLastTickTime = System.nanoTime();
    }

    public long getEllapsedMilliSeconds() {
        return (System.nanoTime() - mLastTickTime) / 1000000;
    }

    public long GetEllapsedSeconds() {
        return (System.nanoTime() - mLastTickTime) / 1000000000;
    }
}

