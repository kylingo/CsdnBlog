package com.free.csdn.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.free.csdn.R;
import com.free.csdn.util.ACache;
import com.free.csdn.util.VersionUtil;

public class SplashActivity extends BaseActivity {
	TextView tvVersion, tvLogoName;
	RelativeLayout rvSplash;
	ACache mCache;
	ImageView ivLogo;

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
		ivLogo = (ImageView) findViewById(R.id.iv_logo);
		tvLogoName = (TextView) findViewById(R.id.tv_logo_name);
		tvVersion = (TextView) findViewById(R.id.tv_version);
		rvSplash = (RelativeLayout) findViewById(R.id.rl_splash);
		mCache = ACache.get(this);
		String name = mCache.getAsString("name");
		try {
			ivLogo.setImageResource(R.drawable.ic_launcher);
			if (!name.equals("")) {
				tvLogoName.setText(name + ",欢迎回来");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		String str = VersionUtil.getVersionName(this);
		tvVersion.setText("当前版本：" + str);
	}

	/**
	 * 渐变展示启动屏
	 */
	private void startAnimation() {

		Animation aa = new Animation() {
		};
		aa.setDuration(3000);
		rvSplash.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {
				Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
				startActivity(intent);
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
