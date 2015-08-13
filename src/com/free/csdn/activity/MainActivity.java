package com.free.csdn.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.free.csdn.R;
import com.free.csdn.fragment.AboutFragment;
import com.free.csdn.fragment.BloggerFragment;
import com.free.csdn.fragment.ChannelFragment;
import com.free.csdn.fragment.ContentFragment;
import com.free.csdn.view.materialmenu.MaterialMenuDrawable;
import com.free.csdn.view.materialmenu.MaterialMenuDrawable.Stroke;
import com.free.csdn.view.materialmenu.MaterialMenuIcon;

/**
 * 侧滑风格主Activity
 * 
 * @author tangqi
 * @data 2015年8月12日下午10:46:07
 */
public class MainActivity extends FragmentActivity {

	/** DrawerLayout */
	private DrawerLayout mDrawerLayout;
	/** 左边栏菜单 */
	private ListView mMenuListView;
	/** 菜单列表 */
	private String[] mMenuTitles = { "首页", "频道", "收藏", "关于", "设置" };
	/** Material Design风格 */
	private MaterialMenuIcon mMaterialMenuIcon;
	/** 菜单打开/关闭状态 */
	private boolean isDirection_left = false;
	private View showView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mMenuListView = (ListView) findViewById(R.id.left_drawer);
		this.showView = mMenuListView;

		// 初始化菜单列表
		mMenuListView.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item,
				mMenuTitles));
		mMenuListView.setOnItemClickListener(new DrawerItemClickListener());

		// 设置抽屉打开时，主要内容区被自定义阴影覆盖
		mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// 设置ActionBar可见，并且切换菜单和内容视图
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mMaterialMenuIcon = new MaterialMenuIcon(this, Color.WHITE, Stroke.THIN);
		mDrawerLayout.setDrawerListener(new DrawerLayoutStateListener());

		if (savedInstanceState == null) {
			selectItem(0);
		}

	}

	/**
	 * ListView上的Item点击事件
	 * 
	 */
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	/**
	 * DrawerLayout状态变化监听
	 */
	private class DrawerLayoutStateListener extends DrawerLayout.SimpleDrawerListener {
		/**
		 * 当导航菜单滑动的时候被执行
		 */
		@Override
		public void onDrawerSlide(View drawerView, float slideOffset) {
			showView = drawerView;
			if (drawerView == mMenuListView) {// 根据isDirection_left决定执行动画
				mMaterialMenuIcon.setTransformationOffset(
						MaterialMenuDrawable.AnimationState.BURGER_ARROW,
						isDirection_left ? 2 - slideOffset : slideOffset);
			}
		}

		/**
		 * 当导航菜单打开时执行
		 */
		@Override
		public void onDrawerOpened(View drawerView) {
			if (drawerView == mMenuListView) {
				isDirection_left = true;
				// setTitle("博客");
			}
		}

		/**
		 * 当导航菜单关闭时执行
		 */
		@Override
		public void onDrawerClosed(View drawerView) {
			if (drawerView == mMenuListView) {
				isDirection_left = false;
				// setTitle(mMenuTitles[mMenuListView.getCheckedItemPosition()]);
			}
		}
	}

	/**
	 * 切换主视图区域的Fragment
	 * 
	 * @param position
	 */
	private void selectItem(int position) {
		Fragment fragment = new ContentFragment();
		Bundle args = new Bundle();
		switch (position) {
		case 0:
			args.putString("key", mMenuTitles[position]);
			fragment = new BloggerFragment();
			break;

		case 1:
			fragment = new ChannelFragment();
			break;

		case 2:
			args.putString("key", mMenuTitles[position]);
			break;

		case 3:
			args.putString("key", mMenuTitles[position]);
			fragment = new AboutFragment();
			break;

		case 4:
			args.putString("key", mMenuTitles[position]);
			break;

		default:
			break;
		}
		fragment.setArguments(args); // FragmentActivity将点击的菜单列表标题传递给Fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

		// 更新选择后的item和title，然后关闭菜单
		mMenuListView.setItemChecked(position, true);
		setTitle(mMenuTitles[position]);
		mDrawerLayout.closeDrawer(mMenuListView);
	}

	/**
	 * 点击ActionBar上菜单
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
		case android.R.id.home:
			if (showView == mMenuListView) {
				if (!isDirection_left) { // 左边栏菜单关闭时，打开
					mDrawerLayout.openDrawer(mMenuListView);
				} else {// 左边栏菜单打开时，关闭
					mDrawerLayout.closeDrawer(mMenuListView);
				}
			}
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 根据onPostCreate回调的状态，还原对应的icon state
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mMaterialMenuIcon.syncState(savedInstanceState);
	}

	/**
	 * 根据onSaveInstanceState回调的状态，保存当前icon state
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		mMaterialMenuIcon.onSaveInstanceState(outState);
		super.onSaveInstanceState(outState);
	}

}
