package com.free.blog.ui.home;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.free.blog.R;
import com.free.blog.library.rx.RxHelper;
import com.free.blog.library.rx.RxSubscriber;
import com.free.blog.library.util.LogUtils;
import com.free.blog.model.remote.NetEngine;
import com.free.blog.ui.base.activity.BaseActivity;
import com.free.blog.ui.home.blogger.BloggerFragment;
import com.free.blog.ui.home.column.ColumnFragment;
import com.free.blog.ui.home.find.FindFragment;
import com.free.blog.ui.home.mine.MineFragment;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

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

		initUmengStatistics();
		initUmengUpdate();

		NetEngine.getInstance().getSearchBlog("Android")
				.compose(RxHelper.<String>getErrAndIOSchedulerTransformer())
				.subscribe(new RxSubscriber<String>() {
					@Override
					public void onError(Throwable e) {
						LogUtils.log(e.toString());
					}

					@Override
					public void onNext(String s) {
						LogUtils.log(s);
					}
				});
	}

	@Override
	protected void initActionBar() {
		// TODO nothing
	}

	/**
	 * 友盟数据统计
	 */
	private void initUmengStatistics() {
		MobclickAgent.setDebugMode(true);
		// SDK在统计Fragment时，需要关闭Activity自带的页面统计，
		// 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
		MobclickAgent.openActivityDurationTrack(false);
		// MobclickAgent.setAutoLocation(true);
		// MobclickAgent.setSessionContinueMillis(1000);

		MobclickAgent.updateOnlineConfig(this);
	}

	/**
	 * 友盟自动更新
	 */
	private void initUmengUpdate() {
		UmengUpdateAgent.update(this);
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
				System.exit(0);
			}
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

}
