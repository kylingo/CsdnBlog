package com.free.blog.ui.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.free.blog.BuildConfig;
import com.free.blog.R;
import com.free.blog.ui.base.activity.BaseActivity;
import com.free.blog.ui.home.blogger.BloggerFragment;
import com.free.blog.ui.home.column.ColumnFragment;
import com.free.blog.ui.home.find.FindFragment;
import com.free.blog.ui.home.mine.MineFragment;
import com.tencent.bugly.Bugly;

/**
 * 主页
 *
 * @author tangqi
 * @since 2015年7月8日下午9:20:20
 */

/**
 * 首页
 * 
 * @author smile
 * 
 */
public class HomeActivity extends BaseActivity implements OnCheckedChangeListener {

	private BloggerFragment mFirstFragment;
	private ColumnFragment mSecondFragment;
	private FindFragment mThirdFragment;
	private MineFragment mFourthFragment;

	private String mFormerTag;
	private final static String FIRST_TAG = "FirstFragment";
	private final static String SECOND_TAG = "SecondFragment";
	private final static String THIRD_TAG = "ThirdFragment";
	private final static String FOURTH_TAG = "FourthFragment";

	private long exitTime;
	private final static long TIME_DIFF = 2 * 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		RadioGroup mGroup = (RadioGroup) findViewById(R.id.main_radio);
		mFirstFragment = new BloggerFragment();
		mSecondFragment = new ColumnFragment();
		mThirdFragment = new FindFragment();
		mFourthFragment = new MineFragment();
		mFormerTag = FIRST_TAG;
		getFragmentManager().beginTransaction()
				.add(R.id.main_content, mFirstFragment, FIRST_TAG).commit();
		mGroup.setOnCheckedChangeListener(this);

		checkUpdate();
	}

	/**
	 * 检测升级
	 */
	private void checkUpdate() {
		Bugly.init(getApplicationContext(), "900007710", BuildConfig.DEBUG);
	}

	/**
	 * 首页导航切换
	 */
	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkedId) {
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction mTransaction = fragmentManager.beginTransaction();
		mTransaction.hide(fragmentManager.findFragmentByTag(mFormerTag));

		switch (checkedId) {

		// 博客
		case R.id.radiobutton_blogger:
			mFormerTag = FIRST_TAG;
			if (mFirstFragment.isAdded()) {
				mTransaction.show(mFirstFragment).commit();
			} else {
				mTransaction.add(R.id.main_content, mFirstFragment, mFormerTag).commit();
			}
			break;

		// 频道
		case R.id.radiobutton_channel:
			mFormerTag = SECOND_TAG;
			if (mSecondFragment.isAdded()) {
				mTransaction.show(mSecondFragment).commit();
			} else {
				mTransaction.add(R.id.main_content, mSecondFragment, mFormerTag).commit();
			}
			break;

		// 发现
		case R.id.radiobutton_find:
			mFormerTag = THIRD_TAG;
			if (mThirdFragment.isAdded()) {
				mTransaction.show(mThirdFragment).commit();
			} else {
				mTransaction.add(R.id.main_content, mThirdFragment, mFormerTag).commit();
			}
			break;

		// 个人
		case R.id.radiobutton_me:
			mFormerTag = FOURTH_TAG;
			if (mFourthFragment.isAdded()) {
				mTransaction.show(mFourthFragment).commit();
			} else {
				mTransaction.add(R.id.main_content, mFourthFragment, mFormerTag).commit();
			}
			break;

		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if ((System.currentTimeMillis() - exitTime) > TIME_DIFF) {
				Toast.makeText(HomeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
