package com.kab.chatclient;

/**
 * Created by Kraskovskiy on 29.10.2016.
 */

import android.content.Context;
import android.util.Log;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Kraskovskiy on 21.09.16.
 */

public class SenderMessageScheduler {
    private static final int UPDATE_TIME_SEC = 60;
    private static Boolean sIsCanceled = false;
    private static Boolean sIsRunning = false;
    private ScheduledExecutorService mScheduler = Executors.newScheduledThreadPool(1);

    public void readRssFeedScheduler(final Context context) {
        if (!sIsRunning) {
            sIsRunning = true;
            mScheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    if (!sIsCanceled) {

                        Log.e("SenderMessageScheduler", "run: " + "readRssFeedScheduler");
                    } else {
                        mScheduler.shutdownNow();
                        sIsCanceled = false;
                        sIsRunning = false;
                    }
                }
            }, 0, UPDATE_TIME_SEC, SECONDS);
        }
    }

    public void stopScheduler() {
        sIsCanceled = true;
        sIsRunning = false;
    }
}