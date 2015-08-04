package com.free.csdn.activity;

import java.util.Random;

import com.free.csdn.R;
import com.free.csdn.util.ACache;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
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
	Random random;
	ImageView iv_main_left_head;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		random = new Random();
		int avatarColor = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
		iv_main_left_head = (ImageView) findViewById(R.id.iv_main_left_head);

		logo_name = (TextView) findViewById(R.id.logo_name);
		mCache = ACache.get(this);

		splash_id = (RelativeLayout) findViewById(R.id.splash_id);
		// splash_id.setBackgroundColor(avatarColor);
		String avatar = mCache.getAsString("avatar");
		String name = mCache.getAsString("name");
		try {
			logo_name.setText("CSDN博客");
			iv_main_left_head.setImageResource(R.drawable.ic_launcher);

			if (!name.equals("")) {
				logo_name.setText(name + ",欢迎回来");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		logo_text = (TextView) findViewById(R.id.logo_text);
		try {
			String str = getVersionName();
			logo_text.setText("当前版本号：" + str);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 渐变展示启动屏
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

	private String getVersionName() throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
		String version = packInfo.versionName;
		return version;
	}

}
