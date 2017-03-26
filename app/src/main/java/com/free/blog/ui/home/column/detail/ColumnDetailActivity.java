package com.free.blog.ui.home.column.detail;

import com.free.blog.model.entity.BlogColumn;
import com.free.blog.model.entity.BlogItem;
import com.free.blog.ui.base.activity.BaseBlogListActivity;

import java.util.List;

/**
 * @author tangqi on 17-3-13.
 */
public class ColumnDetailActivity extends BaseBlogListActivity {
    public static final String EXTRA_BLOG_COLUMN = "blog_column";

    private BlogColumn mBlogColumn;

    @Override
    protected String getActionBarTitle() {
        return mBlogColumn.getName();
    }

    @Override
    protected void beforeInitView() {
        mBlogColumn = (BlogColumn) getIntent().getSerializableExtra(EXTRA_BLOG_COLUMN);
        new ColumnDetailPresenter(this, mBlogColumn);
    }

    @Override
    protected boolean hasMore(List<BlogItem> blogItems) {
        if (blogItems != null && blogItems.size() > 0) {
            BlogItem blogItem = blogItems.get(blogItems.size() - 1);
            return mPresenter.hasMore(blogItem.getTotalPage());
        }
        return false;
    }
}
