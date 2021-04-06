package com.wowly.home;

import androidx.multidex.MultiDex;

import com.wowly.common.app.BaseApplication;


public class WowlyApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        MultiDex.install(this);

    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
