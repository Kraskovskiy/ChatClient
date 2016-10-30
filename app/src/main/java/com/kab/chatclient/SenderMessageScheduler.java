package com.kab.chatclient;

/**
 * Created by Kraskovskiy on 29.10.2016.
 */

import android.content.Context;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Kraskovskiy on 21.09.16.
 */

public class SenderMessageScheduler {
    private static final int UPDATE_TIME_SEED = 6;
    private static int sUpdateTime = 6;
    private static Boolean sIsCanceled = false;
    private ScheduledExecutorService mScheduler = Executors.newScheduledThreadPool(1);

    public void sendMessageSchedule(final Context context) {
        mScheduler.schedule(new Runnable() {
                                @Override
                                public void run() {

                                    Utility.saveMessageInDb(context, Utility.messageGenerator(context));
                                    changeUpdateTime(UPDATE_TIME_SEED);

                                    if (!sIsCanceled) {
                                        sendMessageSchedule(context);
                                    }

                                }
                            }

                , sUpdateTime, SECONDS);
    }

    public void changeUpdateTime(int timeSeed) {
        Random rnd = new Random();
        sUpdateTime = rnd.nextInt(timeSeed)+1;
    }

    public void startScheduler() {
        sIsCanceled = false;
    }

    public void stopScheduler() {
        sIsCanceled = true;
        mScheduler.shutdownNow();
    }
}