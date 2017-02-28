package com.free.blog.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.free.blog.MyApplication;
import com.umeng.analytics.MobclickAgent;

/**
 * FragmentActivity-基类
 * 
 * @author tangqi
 * @since 2015年8月10日上午12:07:57
 */

public class BaseFragmentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		MyApplication.getInstance().addActivity(this);
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
