package com.free.blog.ui.base.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.free.blog.BlogApplication;
import com.umeng.analytics.MobclickAgent;

/**
 * Activity-基类
 * @author tangqi
 * @since 2015年7月8日下午9:20:10
 */

public abstract class BaseActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BlogApplication.getInstance().addActivity(this);
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
