package com.free.blog;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.free.blog.library.config.CacheManager;
import com.free.blog.library.util.CrashHandler;

import java.io.File;
import java.lang.reflect.Method;

/**
 * 应用Application类
 *
 * @author tangqi
 * @since 2015年7月8日下午11:47:10
 */

public class BlogApplication extends Application {

    private static BlogApplication mInstance;

    public static Context getContext() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
    }

    private void init() {
        setupLeakCanary();
    }

    @SuppressWarnings("unchecked")
    protected void setupLeakCanary() {
        if (BuildConfig.DEBUG) {
            try {
                Class androidExcludedRefs = Class.forName("com.squareup.leakcanary.AndroidExcludedRefs");
                Method createAppDefaults = androidExcludedRefs.getMethod("createAppDefaults");

                Object builderWithParams = createAppDefaults.invoke(androidExcludedRefs);
                Method clazz = builderWithParams.getClass().getMethod("clazz", String.class);
                Method build = builderWithParams.getClass().getMethod("build");
                clazz.invoke(builderWithParams, "android.view.inputmethod.InputMethodManager");
                Object excludedRefs = build.invoke(builderWithParams);

                Class leakCanary = Class.forName("com.squareup.leakcanary.LeakCanary");
                Method refWatcher = leakCanary.getMethod("refWatcher", Context.class);
                Object androidRefWatcherBuilder = refWatcher.invoke(leakCanary, this);
                Method excludedRefs_ = androidRefWatcherBuilder.getClass().getMethod("excludedRefs", excludedRefs.getClass());
                Method buildAndInstall = androidRefWatcherBuilder.getClass().getMethod("buildAndInstall");
                excludedRefs_.invoke(androidRefWatcherBuilder, excludedRefs);
                buildAndInstall.invoke(androidRefWatcherBuilder);
            } catch (Exception e) {
                e.printStackTrace();
            }

//            ExcludedRefs excludedRefs = AndroidExcludedRefs.createAppDefaults()
//                    .staticField("android.view.inputmethod.InputMethodManager", "sInstance")
//                    .clazz("android.view.inputmethod.InputMethodManager")
//                    .build();
//            LeakCanary.refWatcher(this)
//                    .excludedRefs(excludedRefs)
//                    .buildAndInstall();
        }
    }

    @SuppressWarnings("unused")
    private void initCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        Thread.currentThread().setUncaughtExceptionHandler(crashHandler);
    }

    /**
     * 重载系统获取缓存目录
     */
    @Override
    public File getCacheDir() {
        return new File(CacheManager.getAppCachePath(this));
    }

    @Override
    public File getDatabasePath(String name) {
        return new File(CacheManager.getAppDatabasePath(this));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
