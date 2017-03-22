package com.free.blog.ui.home.mine;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.free.blog.R;
import com.free.blog.data.entity.BlogItem;
import com.free.blog.ui.base.activity.BaseRefreshActivity;
import com.free.blog.ui.base.adapter.BaseViewAdapter;
import com.free.blog.ui.detail.BlogContentActivity;
import com.free.blog.ui.list.BlogListAdapter;

import java.util.List;

/**
 * @author studiotang on 17/3/22
 */
public class BlogCollectActivity extends BaseRefreshActivity<List<BlogItem>> {
    @Override
    protected String getActionBarTitle() {
        return "博客收藏";
    }

    @Override
    protected void beforeInitView() {
        new BlogCollectPresenter(this);
    }

    @Override
    protected BaseViewAdapter onCreateAdapter() {
        return new BlogListAdapter();
    }

    @Override
    protected void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
        BlogItem item = (BlogItem) mAdapter.getItem(position);
        Intent intent = new Intent();
        intent.setClass(this, BlogContentActivity.class);
        intent.putExtra(BlogContentActivity.EXTRA_BLOG_ITEM, item);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_no);
    }
}
