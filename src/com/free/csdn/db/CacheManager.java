package com.free.csdn.db;

import java.io.File;

import android.content.Context;

import com.free.csdn.util.FileUtil;

/**
 * App缓存管理
 * 
 * @author tangqi
 * @data 2015年8月8日上午11:35:33
 */

public class CacheManager {

	/**
	 * 获取博客列表数据目录
	 * 
	 * @param context
	 * @return
	 */
	public static String getBlogListDbPath(Context context) {
		return FileUtil.getExternalCacheDir(context) + File.separator
				+ "BlogList";
	}

	/**
	 * 获取博客内容数据库目录
	 * 
	 * @param context
	 * @return
	 */
	public static String getBlogContentDbPath(Context context) {
		return FileUtil.getExternalCacheDir(context) + File.separator
				+ "BlogContent";
	}

	/**
	 * 获取评论数据库目录
	 * 
	 * @param context
	 * @return
	 */
	public static String getCommentDbPath(Context context) {
		return FileUtil.getExternalCacheDir(context) + File.separator
				+ "CommentList";
	}

	/**
	 * 获取WebView缓存目录
	 * 
	 * @return
	 */
	public static String getAppCachePath(Context context) {
		return FileUtil.getExternalCacheDir(context) + File.separator + "App"
				+ File.separator + "Cache";
	}

	/**
	 * 获取WebView数据库目录
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppDatabasePath(Context context) {
		return FileUtil.getExternalCacheDir(context) + File.separator + "App"
				+ File.separator + "DataBase";
	}
}
