package com.free.csdn.activity;

import java.io.File;

import android.app.Application;

import com.free.csdn.db.CacheManager;
import com.free.csdn.util.CrashHandler;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.L;

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
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(5).memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory().writeDebugLogs()
				.memoryCacheSize(1024 * 1024 * 8)
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.build();

		ImageLoader.getInstance().init(config);
		L.writeLogs(false);
	}

	/**
	 * 初始化CrashHandler
	 */
	private void initCrashHandler() {
		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(this);
		Thread.currentThread().setUncaughtExceptionHandler(crashHandler);
	}

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
