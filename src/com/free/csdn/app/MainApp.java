package com.free.csdn.app;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.L;

import android.app.Application;

/**
 * @author tangqi
 * @data 2015年7月8日下午11:47:10
 */

public class MainApp extends Application {
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		initImageLoader();
	}

	private void initImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this).threadPriority(Thread.NORM_PRIORITY - 2)
				.threadPoolSize(5).memoryCache(new WeakMemoryCache())
				.denyCacheImageMultipleSizesInMemory().writeDebugLogs()
				.memoryCacheSize(1024 * 1024 * 4)
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.build();

		ImageLoader.getInstance().init(config);
		L.writeLogs(true);
	}
}
