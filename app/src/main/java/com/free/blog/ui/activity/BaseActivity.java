package com.free.blog.ui.activity;

import android.app.Activity;
import android.os.Bundle;

import com.free.blog.ui.BaseApplication;
import com.free.blog.ui.view.SystemBarTintManager;
import com.umeng.analytics.MobclickAgent;

/**
 * Activity-基类
 * @author tangqi
 * @data 2015年7月8日下午9:20:10
 */

public class BaseActivity extends Activity {

	/**
	 * 系统状态栏管理
	 */
	protected SystemBarTintManager mSystemBarTintManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
