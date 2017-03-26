package com.free.blog.ui.home.find.hot;

import com.free.blog.ui.home.find.last.NewBlogActivity;

/**
 * @author studiotang on 17/3/25
 */
public class HotBlogActivity extends NewBlogActivity {

    @Override
    protected void beforeInitView() {
        new HotBlogPresenter(this);
    }
}
