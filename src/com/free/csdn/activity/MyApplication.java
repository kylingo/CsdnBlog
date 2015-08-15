package com.free.csdn.activity;

import java.io.File;

import android.app.Application;

import com.free.csdn.config.CacheManager;
import com.free.csdn.util.CrashHandler;

/**
 * 应用Application类
 * 
 * @author tangqi
 * @data 2015年7月8日下午11:47:10
 */

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		initImageLoader();
		initCrashHandler();
	}

	/**
	 * 初始化ImageLoader
	 */
	private void initImageLoader() {

	}

	/**
	 * 初始化CrashHandler
	 */
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
		// TODO Auto-generated method stub
		return new File(CacheManager.getAppCachePath(this));
	}

	@Override
	public File getDatabasePath(String name) {
		// TODO Auto-generated method stub
		return new File(CacheManager.getAppDatabasePath(this));
	}

}
