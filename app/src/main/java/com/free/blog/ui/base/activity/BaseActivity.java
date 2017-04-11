package com.free.blog.ui.base.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.free.blog.BlogApplication;
import com.umeng.analytics.MobclickAgent;

/**
 * Activity-基类
 *
 * @author tangqi
 * @since 2015年7月8日下午9:20:10
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActionBar();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;

            default:
                break;
        }
        return true;
    }

    protected void initActionBar() {
        mActionBar = getSupportActionBar();
        if (mActionBar != null) {
//            mActionBar.setCustomView(R.layout.include_head_layout);
//            View view = mActionBar.getCustomView();
//            mTvTitle = (TextView) view.findViewById(R.id.tv_title);
//            ImageView mBackBtn = (ImageView) view.findViewById(R.id.btn_back);
//            ImageView mMenuBtn = (ImageView) view.findViewById(R.id.btn_menu);
//            mBackBtn.setOnClickListener(this);
//            mMenuBtn.setOnClickListener(this);
//            mMenuBtn.setVisibility(isShowMenu() ? View.VISIBLE : View.GONE);
//            mMenuBtn.setImageResource(R.drawable.ic_menu);

//            setActionBarTitle(getActionBarTitle());
            mActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void setActionBarTitle(String title) {
        if (mActionBar != null) {
            mActionBar.setTitle(title);
        }
    }
}
