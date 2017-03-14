package com.free.blog;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.free.blog.domain.config.CacheManager;
import com.free.blog.domain.util.CrashHandler;
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

public class MyApplication extends Application {

	private static MyApplication mInstance;
	private List<Activity> mActivities = new ArrayList<Activity>();

	// 单例模式中获取唯一的ExitApplication 实例
	public static MyApplication getInstance() {
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
		initImageLoader();

		initCrashReport();
	}

	/**
	 * 初始化ImageLoader
	 */
	private void initImageLoader() {

	}

	/**
	 * 初始化CrashHandler(保存在本地)
	 */
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

	/**
	 * 把Activity加入历史堆栈
	 */
	public void addActivity(Activity activity) {
		mActivities.add(activity);
	}

	/**
	 * 结束
	 */
	@Override
	public void onTerminate() {
		super.onTerminate();

		for (Activity activity : mActivities) {
			activity.finish();
		}

		System.exit(0);
	}
}
