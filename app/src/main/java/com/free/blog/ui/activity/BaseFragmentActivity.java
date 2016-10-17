package com.free.blog.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.free.blog.ui.BaseApplication;
import com.free.blog.ui.view.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

/**
 * FragmentActivity-基类
 * 
 * @author tangqi
 * @data 2015年8月10日上午12:07:57
 */

public class BaseFragmentActivity extends FragmentActivity {
	/**
	 * 系统状态栏管理
	 */
	protected SystemBarTintManager mSystemBarTintManager;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		BaseApplication.getInstance().addActivity(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
}
