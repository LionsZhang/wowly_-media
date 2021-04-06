package com.wowly.common;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.wowly.common.app.BaseApplication;



public class CommonApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();


    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
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
