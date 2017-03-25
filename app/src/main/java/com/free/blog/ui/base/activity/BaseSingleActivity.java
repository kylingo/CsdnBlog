package com.free.blog.ui.base.activity;

import android.os.Bundle;

/**
 * @author studiotang on 17/3/25
 */
public abstract class BaseSingleActivity extends BaseActivity {

    protected abstract int getContentView();
    protected abstract void beforeInitView();
    protected abstract void initView();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());

        beforeInitView();
        initView();
    }
}
