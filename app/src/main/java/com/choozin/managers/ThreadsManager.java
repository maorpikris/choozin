package com.choozin.managers;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import com.choozin.infra.base.BaseManager;

import java.util.HashMap;
import java.util.Map;

public class ThreadsManager extends BaseManager {

    public static final String Secondary = "secondary";
    public static final String MainThread = "main";
    private static final Map<String, Looper> loopers = new HashMap<>();
    private static final Map<String, Handler> defaultHandlers = new HashMap<>();
    private static ThreadsManager instance = null;

    public static ThreadsManager getInstance() {
        if (instance == null) {
            instance = new ThreadsManager();
            loopers.put(MainThread, Looper.getMainLooper());
        }
        return instance;
    }

    public final Handler getNewHandler(String threadName) {
        return new Handler(getLooper(threadName));
    }

    public final Handler getDefaultHandler(String threadName) {
        Handler handler = defaultHandlers.get(threadName);
        if (handler == null || !handler.getLooper().getThread().isAlive()) {
            handler = getNewHandler(threadName);
            defaultHandlers.put(threadName, handler);
        }
        return handler;
    }

    private Looper getLooper(String threadName) {
        Looper looper = loopers.get(threadName);
        if (looper == null || !looper.getThread().isAlive()) {
            HandlerThread thread = new HandlerThread(threadName);
            thread.start();
            looper = thread.getLooper();
            loopers.put(threadName, looper);
        }
        return looper;
    }

    public Handler getDefaultHandler(String threadName, final int threadPriority) {
        Handler defaultHandler = getDefaultHandler(threadName);
        defaultHandler.post(() -> android.os.Process.setThreadPriority(threadPriority));
        return defaultHandler;
    }

}
