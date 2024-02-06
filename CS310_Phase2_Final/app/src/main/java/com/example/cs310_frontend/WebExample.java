package com.example.cs310_frontend;

import android.app.Application;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebExample extends Application {
    private static ExecutorService srv; // Make it static

    @Override
    public void onCreate() {
        super.onCreate();
        srv = Executors.newCachedThreadPool();
    }

    public static ExecutorService getExecutorService() { // Make it static
        return srv;
    }
}
