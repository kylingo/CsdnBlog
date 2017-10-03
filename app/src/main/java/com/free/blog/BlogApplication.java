package com.free.blog;

import android.app.Application;
import android.content.Context;

import com.free.blog.library.config.CacheManager;
import com.free.blog.library.util.CrashHandler;
import com.squareup.leakcanary.AndroidExcludedRefs;
import com.squareup.leakcanary.ExcludedRefs;
import com.squareup.leakcanary.LeakCanary;

import java.io.File;

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

    protected void setupLeakCanary() {
        if (BuildConfig.DEBUG) {
            ExcludedRefs excludedRefs = AndroidExcludedRefs.createAppDefaults()
                    .staticField("android.view.inputmethod.InputMethodManager", "sInstance")
                    .build();
            LeakCanary.refWatcher(this)
                    .excludedRefs(excludedRefs)
                    .buildAndInstall();
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
}
