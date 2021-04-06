package com.wowly.common.app;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;

public class BaseApplication extends MultiDexApplication {
    private static Context mContext;
    private static BaseApplication mApplication;
//    public static ApplicationComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mApplication = this;
//        appComponent = DaggerApplicationComponent.create();

        if(BuildConfig.DEBUG) {
            ARouter.openLog();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getContext() {
        return mContext;
    }
    public static BaseApplication getApplication() {
        return mApplication;
    }
}
