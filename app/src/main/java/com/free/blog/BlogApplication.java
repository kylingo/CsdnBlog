package com.free.blog;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.free.blog.library.config.CacheManager;
import com.free.blog.library.util.CrashHandler;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.bugly.crashreport.CrashReport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 应用Application类
 *
 * @author tangqi
 * @since 2015年7月8日下午11:47:10
 */

public class BlogApplication extends Application {

    private static BlogApplication mInstance;
    private List<Activity> mActivities = new ArrayList<Activity>();

    public static BlogApplication getInstance() {
        return mInstance;
    }

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
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

//        initCrashReport();
    }

    @SuppressWarnings("unused")
    private void initCrashHandler() {
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);
        Thread.currentThread().setUncaughtExceptionHandler(crashHandler);
    }

    /**
     * 初始化崩溃上传(腾讯BUGLY)
     */
    private void initCrashReport() {
        CrashReport.initCrashReport(this, "900007710", false);
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

    public void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        for (Activity activity : mActivities) {
            activity.finish();
        }

        System.exit(0);
    }
}
