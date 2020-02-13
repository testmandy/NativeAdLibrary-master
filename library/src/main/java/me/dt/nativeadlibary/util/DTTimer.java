package me.dt.nativeadlibary.util;


import android.os.Handler;
import android.util.Log;


public class DTTimer implements Runnable {

    public static String tag = "DTTimer";

    public interface DTTimerListener {
        public void onTimer(DTTimer timer);
    }

    public enum TimerState {
        STOP, START
    }

    private Handler mHandler;

    private DTTimerListener listener;

    public DTTimerListener getListener() {
        return listener;
    }

    private TimerState timerState;

    /**
     * timer interval in seconds
     */
    private long timeInterval;

    /**
     * 定时器开启时的时间
     */
    private long startTime;


    /**
     * 定时器停止时距离定时器开始时逝去的时间
     */
    private long elapsedTimeSecondsWhenStop;

    /**
     * Tell if the timer is a one-shot timer or not.
     */
    private boolean repeated;

    /**
     * Calulate the ellapsed time since the timer start until now
     */
    private DTTickCount mTickCount;

    /**
     * @param timeInterval The interval of the timer. in milliseconds.
     * @param repeated     If repeated is true the timer will fired every specific seconds.
     * @param listener     DTTimerListener
     */
    public DTTimer(long timeInterval, boolean repeated, DTTimerListener listener) {
        this.timeInterval = timeInterval;
        this.repeated = repeated;
        this.listener = listener;
        timerState = TimerState.STOP;
        mHandler = new Handler();
        mTickCount = new DTTickCount();
    }

    public void startTimer() {
        if (timerState == TimerState.STOP) {
            mHandler.postDelayed(this, timeInterval);
            timerState = TimerState.START;
            mTickCount.reset();
            startTime = System.currentTimeMillis();
        } else {
            Log.d(tag, "Call start timer when state is not STOP");
        }
    }

    public void stopTimer() {
        if (timerState == TimerState.START) {
            mHandler.removeCallbacks(this);
            timerState = TimerState.STOP;
            elapsedTimeSecondsWhenStop = System.currentTimeMillis() - startTime;
        }

    }

    public long getTimeInterval() {
        return timeInterval;
    }

    public long getElapsedTimeSeconds() {
        return mTickCount.GetEllapsedSeconds();
    }

    /**
     * 定时器停止时距离定时器开始时逝去的时间,时间值类型为毫秒
     *
     * @return the time
     */
    public long getElapsedTimeSecondsWhenStop() {
        return elapsedTimeSecondsWhenStop;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        if (timerState == TimerState.START) {
            listener.onTimer(this);

            if (repeated) {
                mHandler.postDelayed(this, timeInterval);
            } else {
                this.stopTimer();
            }
        }
    }

}

