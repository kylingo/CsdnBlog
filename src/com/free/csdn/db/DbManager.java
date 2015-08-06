package com.free.csdn.db;

import android.content.Context;

import com.free.csdn.util.FileUtil;
import com.free.csdn.util.MD5;
import com.lidroid.xutils.DbUtils;

/**
 * 数据库管理
 * 
 * @author tangqi
 * @data 2015年8月7日上午3:04:21
 */

public class DbManager {

	public static DbUtils getBlogContentDb(Context context, String url) {
		String urlMD5 = "url-md5";
		try {
			urlMD5 = MD5.getMD5(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DbUtils db = DbUtils.create(context,
				FileUtil.getExternalCacheDir(context) + "/BlogDetail", urlMD5);
		return db;

	}
}
