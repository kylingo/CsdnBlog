package com.free.blog.ui.home.mine;

import com.free.blog.ui.base.activity.BaseBlogListActivity;

/**
 * @author tangqi on 17-3-23.
 */
public class BlogHistoryActivity extends BaseBlogListActivity {

    @Override
    protected String getActionBarTitle() {
        return "博客历史";
    }

    @Override
    protected void beforeInitView() {
        new BlogHistoryPresenter(this);
    }
}
