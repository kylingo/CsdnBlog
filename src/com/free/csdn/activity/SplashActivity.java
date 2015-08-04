package com.free.csdn.activity;

import java.net.CacheRequest;

import com.free.csdn.R;
import com.free.csdn.util.ACache;
import com.free.csdn.util.VersionUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SplashActivity extends Activity {
	TextView logo_text, logo_name;
	RelativeLayout splash_id;
	ACache mCache;
	ImageView iv_main_left_head;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		initView();
		startAnimation();
	}

	/**
	 * 初始化
	 */
	private void initView() {
		iv_main_left_head = (ImageView) findViewById(R.id.iv_main_left_head);
		logo_name = (TextView) findViewById(R.id.logo_name);
		logo_text = (TextView) findViewById(R.id.logo_text);
		splash_id = (RelativeLayout) findViewById(R.id.splash_id);
		mCache = ACache.get(this);
		String name = mCache.getAsString("name");
		try {
			iv_main_left_head.setImageResource(R.drawable.ic_launcher);

			if (!name.equals("")) {
				logo_name.setText(name + ",欢迎回来");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		String str = VersionUtil.getVersionName(this);
		logo_text.setText("当前版本号：" + str);
	}

	/**
	 * 渐变展示启动屏
	 */
	private void startAnimation() {

		AlphaAnimation aa = new AlphaAnimation(0.4f, 1.0f);
		aa.setDuration(3000);
		splash_id.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				Intent it = new Intent(SplashActivity.this, HomeActivity.class);
				startActivity(it);
				finish();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationStart(Animation animation) {

			}

		});
	}

}
