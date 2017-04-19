package com.free.blog.ui.base.activity;

import android.app.Activity;
import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;

/**
 * Activity-基类
 * @author tangqi
 * @since 2015年7月8日下午9:20:10
 */

public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
