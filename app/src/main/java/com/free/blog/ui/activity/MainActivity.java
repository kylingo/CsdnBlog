package com.free.blog.ui.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.free.blog.R;
import com.free.blog.domain.bean.DrawerInfo;
import com.free.blog.domain.util.ToastUtil;
import com.free.blog.ui.adapter.DrawerAdapter;
import com.free.blog.ui.fragment.BloggerFragment;
import com.free.blog.ui.view.CircleImageView;
import com.free.blog.ui.view.drawerlayout.ActionBarDrawerToggle;
import com.free.blog.ui.view.drawerlayout.DrawerArrowDrawable;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.List;
/**
 * 侧滑风格主Activity
 * 
 * @author tangqi
 * @since 2015年8月12日下午10:46:07
 */
public class MainActivity extends BaseFragmentActivity implements OnClickListener {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private RelativeLayout mRl;
	private ActionBarDrawerToggle mDrawerToggle;

	public static FragmentManager mFragmentManager;
	private Boolean isOpen = false;
	private String[] mMenuTitles = { "首页",
			// "频道",
			"收藏", "关于", "设置" };
	private int[] mResId = { R.drawable.me_06,
			// R.drawable.me_03,
			R.drawable.me_02, R.drawable.me_04, R.drawable.me_05 };
	private long mExitTime;
	private final static long TIME_DIFF = 2 * 1000;

	private BloggerFragment mBloggerFragment = null;
	// private ColumnFragment channelFragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		RelativeLayout mRlTop = (RelativeLayout) findViewById(R.id.toprl);
		LinearLayout mllAnimllId = (LinearLayout) findViewById(R.id.animll_id);
		ImageView mTvLogin = (ImageView) findViewById(R.id.login_tv);
		TextView mUserName = (TextView) findViewById(R.id.user_name);
		CircleImageView mIvMainLeftHead = (CircleImageView) findViewById(R.id.iv_main_left_head);
		mRl = (RelativeLayout) findViewById(R.id.rl);

		mRlTop.setOnClickListener(this);
		mTvLogin.setOnClickListener(this);
		mllAnimllId.setOnClickListener(this);
		mUserName.setOnClickListener(this);
		mIvMainLeftHead.setOnClickListener(this);

		initFragment();
		initDrawerLayout();
		initDrawerList();

		initUmengStatistics();
		initUmengUpdate();
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
		/** 静默更新 */
		UmengUpdateAgent.silentUpdate(this);

		/** 自动更新，提醒用户下载 */
		// UmengUpdateAgent.update(this);
	}

	/**
	 * 初始化侧滑栏
	 */
	private void initDrawerLayout() {
		ActionBar ab = getActionBar();
		if (ab != null) {
			ab.setDisplayHomeAsUpEnabled(true);
			ab.setHomeButtonEnabled(true);
		}

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.navdrawer);

		DrawerArrowDrawable mDrawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mDrawerArrow, R.string.drawer_open,
				R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
				isOpen = false;
			}

			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
				isOpen = true;
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerToggle.syncState();
	}

	/**
	 * 初始化侧滑ListView
	 */
	private void initDrawerList() {
		List<DrawerInfo> list = new ArrayList<DrawerInfo>();
		for (int i = 0; i < mMenuTitles.length; i++) {
			DrawerInfo drawerItem = new DrawerInfo();
			drawerItem.setName(mMenuTitles[i]);
			drawerItem.setResId(mResId[i]);
			list.add(drawerItem);
		}
		final DrawerAdapter adapter = new DrawerAdapter(this, list);
		mDrawerList.setAdapter(adapter);
		adapter.setSelectionPosition(0);
		mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@SuppressLint({ "ResourceAsColor", "Recycle" })
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = null;
				switch (position) {
				case 0:
					if (mBloggerFragment == null) {
						mBloggerFragment = new BloggerFragment();
					}
					initFragment(mBloggerFragment);
					setTitle(mMenuTitles[position]);
					adapter.setSelectionPosition(position);
					break;

				// case 1:
				// if (channelFragment == null) {
				// channelFragment = new ColumnFragment();
				// }
				// initFragment(channelFragment);
				// setTitle(mMenuTitles[position]);
				// adapter.setSelectionPosition(position);
				// break;

				case 1:
					intent = new Intent(MainActivity.this, BlogCollectActivity.class);
					break;

				case 2:
					intent = new Intent(MainActivity.this, AboutActivity.class);
					break;

				case 3:
					intent = new Intent(MainActivity.this, SettingsActivity.class);
					break;
				}

				if (intent != null) {
					startActivity(intent);
				} else {
					mDrawerLayout.closeDrawers();
					isOpen = false;
				}
			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (mDrawerLayout.isDrawerOpen(mRl)) {
				mDrawerLayout.closeDrawer(mRl);
				isOpen = false;
			} else {
				mDrawerLayout.openDrawer(mRl);
				isOpen = true;
			}
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public void onClick(View v) {

	}

	private void initFragment() {
		mFragmentManager = getSupportFragmentManager();
		// 只當容器，主要內容已Fragment呈現
		if (mBloggerFragment == null) {
			mBloggerFragment = new BloggerFragment();
		}
		initFragment(mBloggerFragment);
		setTitle(mMenuTitles[0]);
	}

	// 切換Fragment
	@SuppressWarnings("unused")
	public void changeFragment(Fragment f) {
		changeFragment(f, false);
	}

	// 初始化Fragment(FragmentActivity中呼叫)
	public void initFragment(Fragment f) {
		changeFragment(f, true);
	}

	private void changeFragment(Fragment f, boolean init) {
		FragmentTransaction ft = mFragmentManager.beginTransaction();
		// .setCustomAnimations(
		// R.anim.umeng_fb_slide_in_from_left,
		// R.anim.umeng_fb_slide_out_from_left,
		// R.anim.umeng_fb_slide_in_from_right,
		// R.anim.umeng_fb_slide_out_from_right);
		ft.replace(R.id.fragment_layout, f);
		if (!init)
			ft.addToBackStack(null);
		ft.commitAllowingStateLoss();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if (!isOpen) {
				if ((System.currentTimeMillis() - mExitTime) > TIME_DIFF) {
					ToastUtil.show(MainActivity.this, "再按一次退出程序");
					mExitTime = System.currentTimeMillis();
				} else {
					System.exit(0);
				}
				return true;
			} else {
				mDrawerLayout.closeDrawers();
				return true;
			}
		}

		return super.onKeyDown(keyCode, event);
	}
}
