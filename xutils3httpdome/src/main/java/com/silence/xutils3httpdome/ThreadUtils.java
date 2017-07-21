package com.silence.xutils3httpdome;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadUtils {
    private static Handler sHandler = new Handler(Looper.getMainLooper());
    private static Executor sExecutor = Executors.newSingleThreadExecutor();
    /**
     * 在子线程中运行一段任务
     * @param runnable
     */
    public static void runOnSubThread(Runnable runnable){
        sExecutor.execute(runnable);
    }

    /**
     * 在主线程中运行一段任务
     * @param runnable
     */
    public static void runOnMainThread(Runnable runnable){
        sHandler.post(runnable);
    }

    public static void runOnDelayedMainThread(Runnable runnable, long delayMillis){
        sHandler.postDelayed(runnable, delayMillis);
    }
}
