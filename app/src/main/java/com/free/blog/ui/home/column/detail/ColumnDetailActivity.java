package com.free.blog.ui.home.column.detail;

import com.free.blog.model.entity.BlogColumn;
import com.free.blog.ui.base.activity.BaseBlogListActivity;

/**
 * @author tangqi on 17-3-13.
 */
public class ColumnDetailActivity extends BaseBlogListActivity {
    public static final String EXTRA_COLUMN = "column";

    private BlogColumn mBlogColumn;

    @Override
    protected String getActionBarTitle() {
        return mBlogColumn.getName();
    }

    @Override
    protected void beforeInitView() {
        mBlogColumn = (BlogColumn) getIntent().getSerializableExtra(EXTRA_COLUMN);
        new ColumnDetailPresenter(this, mBlogColumn);
    }
}
